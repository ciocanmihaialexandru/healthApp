package healthapp

import org.springframework.security.access.annotation.Secured

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class RegisterController {

    def index() {

        def newUser = new User(username: params.email, password: params.password, notificationsEmail: params.notificationsEmail, name: params.name)

        if (newUser.validate()) {

            newUser.save(flush: true)
            UserRole.create(newUser, Role.findByAuthority("ROLE_USER"))

            flash.successfulRegistration = "User registered! Please login."
            return redirect(controller: "login", action: "auth")
        } else {
            flash.message = "Validation errors! Please retry."
            return
        }

    }
}
