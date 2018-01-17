package healthapp

import grails.transaction.Transactional
import org.springframework.messaging.simp.SimpMessagingTemplate
import wslite.http.auth.HTTPBasicAuthorization
import wslite.json.JSONObject
import wslite.rest.ContentType
import wslite.rest.RESTClient

@Transactional
class FitbitService {

    SimpMessagingTemplate brokerMessagingTemplate

    //------------------------------------ Methods used in Authorization flow ----------------------------------------//

    /**
     *
     * @param accessToken
     * @return JSONObject with informations about current user, subscription id, subscriber id
     */
    def addUsertoSubscriptionStream(accessToken) {

        def client = new RESTClient("https://api.fitbit.com/1/")

        def response = client.post(path: "/user/-/activities/apiSubscriptions/320.json",
                accept: ContentType.JSON,
                headers: ["Authorization": "Bearer " + accessToken.toString(), "X-Fitbit-Subscriber-Id" : "healthAppSubscriber"])

        switch (response.statusCode) {
            case 201:
                println "A new subscription created for current user"
                break
            case 200:
                println "Current user is already subscribed to the stream"
                break
            default:
                println "Other status code"
                break
        }

        return new JSONObject(new String(response.data))

    }

    /**
     *
     * @param accessToken
     * @return JSONObject with profile informations using a valid accessToken
     */
    def getProfileInformationsWithToken(accessToken) {
        def client = new RESTClient("https://api.fitbit.com/1/")

        def response = client.get(path: '/user/-/profile.json',
                accept: ContentType.JSON,
                headers: ["Authorization": "Bearer " + accessToken.toString()])

        return new JSONObject(new String(response.data))
    }

    //------------------------------------- Methods used in Home controller ------------------------------------------//

    /**
     *
     * @param accessTokenEntry
     * @return JSONObject with user profile informations if token is not expired
     *         If token is expired, a request with refresh token is made to get a new token
     */
    def checkAccessTokenValidity(AccessToken accessTokenEntry) {
        def client = new RESTClient("https://api.fitbit.com/1/")
        def userData

        // improvement to not make calls in vain ----- see throthling policy from fitbit
        if (hoursBetween(accessTokenEntry.lastModified, new Date()) < 8) {
            return true
        }

        try {
            userData = client.get(path: '/user/-/profile.json',
                    accept: ContentType.JSON,
                    headers: ["Authorization": "Bearer " + accessTokenEntry.accessToken.toString()])

            return new JSONObject(new String(userData?.response?.data))
        } catch (Exception e) {

            // token has expired, so we need to make a request for a new token with existing refresh_token
            if (e.response.statusCode == 401 && new JSONObject(new String(e.response.data)).errors[0].errorType == "expired_token") {
                userData = requestNewToken(accessTokenEntry.refreshToken.toString())
                if (userData) {
                    return userData
                } else {
                    return null
                }

            }
        }
    }

    /**
     *
     * @param refreshToken
     * @return JSONObject with new token and refresh token based on old refresh token
     */
    def requestNewToken(refreshToken) {

        def client = new RESTClient("https://api.fitbit.com")

        // As fitbit documentation says, create basic authorization with client_id and client_secret
        client.httpClient.authorization = new HTTPBasicAuthorization("228HWD", "cf5753b1b88d8ae0d045b0c7efa00880")

        try {
            def response = client.post(path: "/oauth2/token") {
                type ContentType.URLENC
                urlenc grant_type: "refresh_token", refresh_token: refreshToken
            }

            return new JSONObject(new String(response.data))
        } catch (Exception e) {
            log.error(new String(e.response.data))
        }

    }

    static def hoursBetween(def startDate, def endDate) {
        use(groovy.time.TimeCategory) {
            def duration = endDate - startDate
            if (duration.days > 0) {
                return 8 // return a value bigger than the limit (expiration hours)
            } else {
                return duration.hours
            }
        }
    }

    //------------------------------------ Methods used to get informations ------------------------------------------//

    /**
     *
     * @param accessToken
     * @return User data such as heart rate or other informations
     */
    private JSONObject getUserData(accessToken) {
        def client = new RESTClient("https://api.fitbit.com/1/")
        def userData = client.get(path: '/user/-/activities/heart/date/today/1d.json',
                accept: ContentType.JSON,
                headers: ["Authorization": "Bearer " + accessToken.toString()])


        return new JSONObject(new String(userData.response.data))

    }

    /**
     * method for websockets live notification
     */
    void sendNotification(user) {
        brokerMessagingTemplate.convertAndSend "/topic/notifications", "${user}"
    }

}
