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
                        <h1 class="page-header">Dashboard</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-user fa-fw"></i> FitBit Profile
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="#" class="list-group-item">
                                        First Name
                                        <span class="pull-right text-muted small"><em>${userInformations.firstName}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Last Name
                                        <span class="pull-right text-muted small"><em>${userInformations.lastName}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Gender
                                        <span class="pull-right text-muted small"><em>${userInformations.gender}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Member since
                                        <span class="pull-right text-muted small"><em>${userInformations.memberSince}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Height
                                        <span class="pull-right text-muted small"><em>${userInformations.height} cm</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Weight
                                        <span class="pull-right text-muted small"><em>${userInformations.weight} kg</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Date of birth
                                        <span class="pull-right text-muted small"><em>${userInformations.dateOfBirth}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Age
                                        <span class="pull-right text-muted small"><em>${userInformations.age}</em>
                                        </span>
                                    </a>
                                    <a href="#" class="list-group-item">
                                        Average daily steps
                                        <span class="pull-right text-muted small"><em>${userInformations.averageDailySteps}</em>
                                        </span>
                                    </a>
                                </div>
                                <!-- /.list-group -->
                            </div>
                            <!-- /.panel-body -->
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
