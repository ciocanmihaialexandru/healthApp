<!doctype html>
<html>
<head>
    <meta name="layout" content="mainLayout"/>
    <title>Health App</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body class="theme-light">
    <div id="wrapper">
        <g:render template="/templates/nav"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Complete heart rate informations</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>Time</th>
                                        <th>Heart Rate (BPM)</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <g:each in="${hours}" var="hour" status="i">
                                            <tr>
                                                <td>${hour}</td>
                                                <td>${heartRates[i]}</td>
                                            </tr>
                                        </g:each>
                                    </tbody>
                                </table>
                                <!-- /.table-responsive -->
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
    <script>
        $(document).ready(function() {
            $('#dataTables-example').DataTable({
                responsive: true,
                "order": [[ 0, "desc" ]]
            });
        });
    </script>
</body>
</html>
