package healthapp

class AccessToken {

    String userId
    String accessToken
    String refreshToken
    String username
    Date lastModified

    static constraints = {
        userId nullable: false
        accessToken nullable: false
        refreshToken nullable: false
        username nullable: false
        lastModified nullable: false, date: true
    }

    static mapping = {
        accessToken type: 'text'
        refreshToken type: 'text'
    }
}
