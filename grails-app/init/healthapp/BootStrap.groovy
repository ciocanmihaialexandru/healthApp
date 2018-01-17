package healthapp

class BootStrap {

    def init = { servletContext ->
        def userRole = new Role(authority: 'ROLE_USER').save()
    }
    def destroy = {
    }
}
