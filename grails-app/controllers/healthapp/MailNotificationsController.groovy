package healthapp

import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_USER'])
class MailNotificationsController {

    def springSecurityService

    def index() {
        def listOfMailNotifications = MailNotifications.findAllByUsername(springSecurityService.currentUser.username.toString())

        [listOfMailNotifications : listOfMailNotifications]
    }
}
