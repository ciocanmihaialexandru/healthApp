package healthapp

class FitbitProfile {

    String username // username from healthApp
    String firstName
    String lastName
    String gender
    String memberSince
    String height
    String weight
    String dateOfBirth
    String averageDailySteps
    String age

    static constraints = {
        username nullable: false
        firstName nullable: false
        lastName nullable: false
        gender nullable: false
        memberSince nullable: false
        height nullable: false
        weight nullable: false
        dateOfBirth nullable: false
        averageDailySteps nullable: false
        age nullable: false
    }
}
