<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/">Health App</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <sec:ifLoggedIn>&nbsp;Welcome, <sec:loggedInUserInfo
                        field="username"/>!</sec:ifLoggedIn> <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">

                <li><a href="/logoff"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="/"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                </li>
                <li>
                    <a href="/fitbitAuthorization/index"><i class="fa fa-sign-in fa-fw"></i> Fitbit authorization</a>
                </li>
                <li>
                    <a href="/fitbitAuthorization/heartRate"><i class="fa fa-heart fa-fw"></i> Show heart rate</a>
                </li>
                <li>
                    <a href="/fitbitAuthorization/fullHeartRateInformations"><i class="fa fa-info-circle fa-fw"></i> Complete health informations</a>
                </li>
                <li>
                    <a href="/mailNotifications/index"><i class="fa fa-envelope fa-fw"></i> Mail notifications</a>
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>