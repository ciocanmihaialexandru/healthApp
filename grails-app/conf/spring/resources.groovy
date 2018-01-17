// Place your Spring DSL code here
beans = {
    mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
        host = "email-smtp.us-west-2.amazonaws.com"
        port = 465
        username = "edit"
        password = "edit"
        javaMailProperties = ["mail.smtp.auth":"true",
                              "mail.smtp.port":"465",
                              "mail.transport.protocol":"smtps",
                              "mail.smtp.ssl.enable":"true",
                              "mail.smtp.from":"ciocanmihaialexandru@gmail.com"]
    }
}
