package healthapp

import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_USER'])
class HomeController {

    def springSecurityService
    def fitbitService

    def index() {

        boolean isHavingAccessToken = false
        def userInformations = FitbitProfile.findByUsername(springSecurityService.currentUser.username.toString())
        def accessTokenValidityInformations

        AccessToken accessTokenEntry = AccessToken.findByUsername(springSecurityService.currentUser.username.toString())
        if (accessTokenEntry) {
            isHavingAccessToken = true
            accessTokenValidityInformations = fitbitService.checkAccessTokenValidity(accessTokenEntry)

            // if something went wrong, send user to authorization page
            if (accessTokenValidityInformations == null) {
                return redirect(controller: "fitbitAuthorization", action: "index")
            }

        }

        // if user has not authorized fitbit yet
        if (!isHavingAccessToken) {
            return redirect(controller: "fitbitAuthorization", action: "index")
        }

        // if token has not expired
        if (accessTokenValidityInformations == true) {
            render view: 'index', model: [userInformations: userInformations]
        } else {

            if (accessTokenValidityInformations.user) {

                // current token is valid
                render view: 'index', model: [userInformations: userInformations]
            } else {

                // refresh token was used to get a new token, so we need to update access and refresh token for current user
                def newAccessToken = accessTokenValidityInformations.access_token
                def newRefreshToken = accessTokenValidityInformations.refresh_token
                accessTokenEntry.accessToken = newAccessToken
                accessTokenEntry.refreshToken = newRefreshToken
                accessTokenEntry.lastModified = new Date()
                accessTokenEntry.save(flush: true)

                render view: 'index', model: [userInformations: userInformations]

            }
        }

    }

}
