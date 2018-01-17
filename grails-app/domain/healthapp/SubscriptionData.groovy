package healthapp

class SubscriptionData {

    String ownerId;
    String collectionType;
    String subscriptionId;

    static constraints = {
        ownerId nullable: false
        collectionType nullable: false
        subscriptionId nullable: false
    }
}
