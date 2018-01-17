<!doctype html>
<html>
<head>
    <meta name="layout" content="mainLayout"/>
    <title>Health App</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <div id="wrapper">
        <g:render template="/templates/nav"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">FitBit Authorization Page</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                FitBit Authorization
                            </div>
                            <div class="panel-body">
                                <g:link class="btn btn-primary btn-lg btn-block" url="https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=228HWD&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FdisplayData%2Fshow&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight">
                                    Authorize FitBit
                                </g:link>
                                <em>If you haven't authorized FitBit yet, press this button</em>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
</body>
</html>
