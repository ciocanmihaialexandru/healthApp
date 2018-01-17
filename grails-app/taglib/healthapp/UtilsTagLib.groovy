package healthapp

class UtilsTagLib {

    static defaultEncodeAs = [taglib:'text']
    
    static namespace = "utils"

    def springSecurityService

    def username = { attrs ->
        if (springSecurityService.isLoggedIn()) {
            out << springSecurityService.authentication.name
        }
    }
}
