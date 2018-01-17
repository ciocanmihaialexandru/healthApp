package healthapp

class NotificationData {

    String ownerId
    String ownerType
    String date
    String collectionType
    String subscriptionId

    static constraints = {
        date nullable: false
        ownerType nullable: false
        collectionType nullable: false
        ownerId nullable: false
        subscriptionId nullable: false
    }
}
