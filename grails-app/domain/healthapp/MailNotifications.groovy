package healthapp

class MailNotifications {

    String username
    Date dateCreated
    String subject
    String body

    static constraints = {
        username nullable: false
        dateCreated nullable: false, date: true
        subject nullable: false
        body nullable: false
    }
}
