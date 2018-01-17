package healthapp

import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_USER'])
class FitbitAuthorizationController {

    def springSecurityService
    def fitbitService

    def index() { }

    def heartRate() {

        AccessToken accessTokenEntry = AccessToken.findByUsername(springSecurityService.currentUser.username.toString())
        def userData = fitbitService.getUserData(accessTokenEntry.accessToken)

        def userDataValues = userData."activities-heart-intraday".dataset
        def userDataSize = userData."activities-heart-intraday".dataset.size()

        def heartRates = []
        def hours = []

        try {

            // get last 1.5 hours of date from 10 minutes to 10 minutes
            if (userDataSize > 0) {
                for (int i = userDataSize - 91; i <= userDataSize - 1; i += 10) {
                    heartRates.add(userDataValues[i].value)
                    hours.add(new String(userDataValues[i].time.substring(0, 5)))
                }
            }
        } catch (Exception e) {
            if (userDataSize > 0 && userDataSize < 10) {

                //get all we can extract
                for (int i = 0; i <= userDataSize - 1; i++) {
                    heartRates.add(userDataValues[i].value)
                    hours.add(new String(userDataValues[i].time.substring(0, 5)))
                }
            } else {

                // get last 10 minutes
                for (int i = userDataSize - 10; i <= userDataSize - 1; i++) {
                    heartRates.add(userDataValues[i].value)
                    hours.add(new String(userDataValues[i].time.substring(0, 5)))
                }
            }
        }


        [heartRates: heartRates, hours: hours]
    }

    def fullHeartRateInformations() {

        AccessToken accessTokenEntry = AccessToken.findByUsername(springSecurityService.currentUser.username.toString())
        def userData = fitbitService.getUserData(accessTokenEntry.accessToken)

        def userDataValues = userData."activities-heart-intraday".dataset
        def userDataSize = userData."activities-heart-intraday".dataset.size()

        def heartRates = []
        def hours = []

        if (userDataSize > 0) {
            for (int i = 0; i < userDataSize; i++) {
                heartRates.add(userDataValues[i].value)
                hours.add(new String(userDataValues[i].time.substring(0, 5)))
            }
        }

        [heartRates: heartRates, hours: hours]
    }
}
