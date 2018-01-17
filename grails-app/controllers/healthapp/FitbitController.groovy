package healthapp

import org.springframework.security.access.annotation.Secured

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class FitbitController {

    def notifications() {

        // verification code given by fitbit after configuring endpoint subscriber url
        /*if (params.verify == "c058f77faba2dcedba7c4d45e0c2c5b14c3bd4f07475a3771a4d698a867dde88") {
            response.setStatus(204)
            return render (status: 204)
        } else {
            response.setStatus(404)
            return render (status: 404)
        }*/
        // verification code given by fitbit after configuring endpoint subscriber url

        def notificationData = request.JSON[0]

        println(notificationData)

        if (notificationData) {
            new NotificationData(ownerId: notificationData.ownerId, ownerType: notificationData.ownerType,
                    date: notificationData.date, collectionType: notificationData.collectionType,
                    subscriptionId: notificationData.subscriptionId).save(flush: true)
        }

        // send back HTTP.NO_CONTENT status as Fitbit mentions in documentation
        response.setStatus(204)
        return render (status: 204)
    }

}
