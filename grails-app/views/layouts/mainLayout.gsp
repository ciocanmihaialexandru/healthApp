<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    <asset:javascript src="application.js"/>


    <script type="text/javascript">
        var username = "${utils.username()}";
        username = username.replace("&#64;", "@");

        $(function() {
            var socket = new SockJS("${createLink(uri: '/stomp')}");
            var client = Stomp.over(socket);

            client.connect({}, function() {
                client.subscribe("/topic/notifications", function(message) {
                    var usernameReceived = JSON.parse(message.body)
                    if (username == usernameReceived.values[0]) {
                        alertify.alert('You have a new notification! Please check mail notifications page.');
                    }
                });
            });

        });
    </script>

    <g:layoutBody/>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
</body>
</html>
