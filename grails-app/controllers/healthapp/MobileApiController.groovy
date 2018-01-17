package healthapp

import grails.converters.JSON
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.access.annotation.Secured

class MobileApiController {

    def mailSender;

    @Secured(["ROLE_USER"])
    def showMailNotifications(Integer max) {

        // display last 10 notifications for given user
        def username = request.JSON.username;
        params.max = Math.min(max ?: 10, 100)
        params.sort = "id"
        params.order = "desc"
        render MailNotifications.findAllByUsername(username.toString(), params) as JSON
    }

    @Secured(["ROLE_USER"])
    def showProfileInfo() {
        def username = request.JSON.username;
        def profile = FitbitProfile.findByUsername(username.toString());

        render (["lastName" : profile.lastName, "firstName" : profile.firstName, "height": profile.height,
                 "weight": profile.weight, "gender": profile.gender, "memberSince": profile.memberSince,
                 "dateOfBirth": profile.dateOfBirth] as JSON)
    }

    @Secured(["permitAll"])
    def signup() {
        def newUser = new User(username: request.JSON.username, password: request.JSON.password, notificationsEmail: request.JSON.notificationsEmail, name: request.JSON.name)

        if (newUser.validate()) {

            newUser.save(flush: true)
            UserRole.create(newUser, Role.findByAuthority("ROLE_USER"))

            render (["registration" : "true"] as JSON)
        } else {
            render (["registration" : "false"] as JSON)
        }
    }

    @Secured(["permitAll"])
    def backgroundLogs() {
        println ("Position: " + request.JSON.latitude.toString() + "," + request.JSON.longitude.toString());
        render (["logs" : "ok"] as JSON)
    }

    @Secured(["permitAll"])
    def mobileNotification() {

        def user = request.JSON.user

        if (user) {

            println ("Notification alert. Location changed.")

            def sendMail = sendMailMessage(User.findByUsername(user.toString()).notificationsEmail)

            if (sendMail == true) {
                new MailNotifications(username: user.toString(), dateCreated: new Date(),
                        subject: "Location alert", body: "Location alert. Please check this out.").save(flush: true)
            }
        }

        render (["notification" : "ok"] as JSON)
    }

    /**
     *
     * @param to
     * sends a mail message
     */
    private sendMailMessage(to) {

        try {
            SimpleMailMessage message = new SimpleMailMessage()
            message.subject = "Location alert"
            message.to = to
            message.from = "ciocanmihaialexandru@gmail.com"
            message.text = "Location alert. Please check this out."

            mailSender.send(message)

            return true
        } catch (Exception e) {
            println("Exception in sending mail")
        }

    }
}
