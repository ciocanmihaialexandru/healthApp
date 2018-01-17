package healthapp

import org.springframework.security.access.annotation.Secured
import wslite.http.auth.HTTPBasicAuthorization
import wslite.json.JSONObject
import wslite.rest.ContentType
import wslite.rest.RESTClient

@Secured(['ROLE_USER'])
class DisplayDataController {

    def springSecurityService
    def fitbitService

    def show() {

        def authorizationCode
        def subscriptionData

        // get code from params to make calls for access token and refresh token
        if (params.code) {

            authorizationCode = params.code

            def client = new RESTClient("https://api.fitbit.com")

            // As fitbit documentation says, create basic authorization with client_id and client_secret
            client.httpClient.authorization = new HTTPBasicAuthorization("228HWD", "cf5753b1b88d8ae0d045b0c7efa00880")
            def response = client.post(path: "/oauth2/token") {
                type ContentType.URLENC
                urlenc client_id: "228HWD", code: authorizationCode, grant_type: "authorization_code",
                        redirect_uri: "http://localhost:8080/displayData/show"
            }

            if (response.data) {
                def receivedParams = new JSONObject(new String(response.data))
                def userId = receivedParams.user_id
                def accessToken = receivedParams.access_token
                def refreshToken = receivedParams.refresh_token

                if (!AccessToken.findByUserId(new String(userId))) {
                    new AccessToken(userId: userId, accessToken: accessToken, refreshToken: refreshToken,
                            username: springSecurityService.currentUser.username, lastModified: new Date()).save(flush: true)
                    new FitbitProfile(username: springSecurityService.currentUser.username,
                                      firstName: fitbitService.getProfileInformationsWithToken(accessToken).user.firstName,
                                      lastName: fitbitService.getProfileInformationsWithToken(accessToken).user.lastName,
                                      height: fitbitService.getProfileInformationsWithToken(accessToken).user.height,
                                      weight: fitbitService.getProfileInformationsWithToken(accessToken).user.weight,
                                      gender: fitbitService.getProfileInformationsWithToken(accessToken).user.gender,
                                      memberSince: fitbitService.getProfileInformationsWithToken(accessToken).user.memberSince,
                                      averageDailySteps: fitbitService.getProfileInformationsWithToken(accessToken).user.averageDailySteps,
                                      age: fitbitService.getProfileInformationsWithToken(accessToken).user.age,
                                      dateOfBirth: fitbitService.getProfileInformationsWithToken(accessToken).user.dateOfBirth).save(flush: true)

                    subscriptionData = fitbitService.addUsertoSubscriptionStream(accessToken)

                    // save into database if entry is not already there
                    if (!SubscriptionData.findByOwnerIdAndCollectionTypeAndSubscriptionId(subscriptionData.ownerId.toString(),
                            subscriptionData.collectionType.toString(), subscriptionData.subscriptionId.toString())) {
                        new SubscriptionData(ownerId: subscriptionData.ownerId, collectionType: subscriptionData.collectionType,
                                subscriptionId: subscriptionData.subscriptionId).save(flush: true)
                    }

                } else {
                    def existingTokenEntry = AccessToken.findByUserId(new String(userId));
                    existingTokenEntry.accessToken = accessToken
                    existingTokenEntry.refreshToken = refreshToken
                    existingTokenEntry.save(flush: true)

                    subscriptionData = fitbitService.addUsertoSubscriptionStream(accessToken)

                    // save into database if entry is not already there
                    if (!SubscriptionData.findByOwnerIdAndCollectionTypeAndSubscriptionId(subscriptionData.ownerId.toString(),
                            subscriptionData.collectionType.toString(), subscriptionData.subscriptionId.toString())) {
                        new SubscriptionData(ownerId: subscriptionData.ownerId, collectionType: subscriptionData.collectionType,
                        subscriptionId: subscriptionData.subscriptionId).save(flush: true)
                    }

                }
            }

        }

        render (view: 'show')

    }

}
