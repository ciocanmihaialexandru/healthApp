package healthapp

import org.springframework.mail.SimpleMailMessage

class NotificationJob {

    def mailSender
    def fitbitService

    static int CUTOFF_HEART_RATE_FOR_EMAIL = 70
    static String EMAIL_SUBJECT = "Health App Notification"
    static String EMAIL_BODY = "Unusual heart rate detected. Please check the doctor"

    static triggers = {
      simple repeatInterval: 10000l // execute job once in 10 seconds
    }

    def execute() {

        // execute job
        def notificationDataList = NotificationData.list()
        def accessTokenValidityInformations

        for (NotificationData notificationData : notificationDataList) {

            // get the fitbit id of user that need to be notified
            def ownerId = notificationData.ownerId

            // get access token of user
            AccessToken accessTokenEntry = AccessToken.findByUserId(ownerId)

            // check if token is valid or need to request a new token
            accessTokenValidityInformations = fitbitService.checkAccessTokenValidity(accessTokenEntry)

            if (accessTokenValidityInformations == true) {

                // token is valid, then request informations and verify if notification is needed
                checkSendingNotification(accessTokenEntry)

            } else {

                // refresh token was used to get a new token, so we need to update access and refresh token for current user
                def newAccessToken = accessTokenValidityInformations.access_token
                def newRefreshToken = accessTokenValidityInformations.refresh_token
                accessTokenEntry.accessToken = newAccessToken
                accessTokenEntry.refreshToken = newRefreshToken
                accessTokenEntry.lastModified = new Date()
                accessTokenEntry.save(flush: true)

                // token is refreshed, so request informations and verify if notification is needed
                checkSendingNotification(accessTokenEntry)
            }

            // finally, delete notification, since we don't need it any more
            notificationData.delete(flush: true)
        }
    }

    /**
     *
     * @param accessTokenEntry
     * sends mail if conditions are verified
     */
    private checkSendingNotification(accessTokenEntry) {

        def userData = fitbitService.getUserData(accessTokenEntry.accessToken)

        def userDataValues = userData."activities-heart-intraday".dataset
        def userDataSize = userData."activities-heart-intraday".dataset.size()


        // check the last 10 minutes, if user has unusual heart rate
        if (userDataSize > 0) {
            for (int i = userDataSize - 1; i >= userDataSize - 10; i--) {
                if (userDataValues[i].value > CUTOFF_HEART_RATE_FOR_EMAIL) {

                    println ("Email condition satisfied for user " + accessTokenEntry.username.toString());

                    def sendMail = sendMailMessage(User.findByUsername(accessTokenEntry.username.toString()).notificationsEmail)

                    // if message was sent with success (since no exception was thrown), we save into db the notification
                    if (sendMail == true) {

                        println ("Creating mail notification for user " + accessTokenEntry.username.toString())
                        new MailNotifications(username: accessTokenEntry.username.toString(), dateCreated: new Date(),
                                              subject: EMAIL_SUBJECT, body: EMAIL_BODY).save(flush: true)

                        // async notification
                        fitbitService.sendNotification(accessTokenEntry.username.toString())

                    }
                    break;
                }
            }
        }
    }

    /**
     *
     * @param to
     * sends a mail message
     */
    private sendMailMessage(to) {

        try {
            SimpleMailMessage message = new SimpleMailMessage()
            message.subject = EMAIL_SUBJECT
            message.to = to
            message.from = "ciocanmihaialexandru@gmail.com"
            message.text = EMAIL_BODY

            mailSender.send(message)

            return true
        } catch (Exception e) {
            println("Exception in sending mail")
        }

    }
}
