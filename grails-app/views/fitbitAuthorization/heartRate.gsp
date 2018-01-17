<!doctype html>
<html>
<head>
    <meta name="layout" content="mainLayout"/>
    <title>Health App</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <link id="themecss" rel="stylesheet" type="text/css" href="/assets/all.min.css" />
    <script type="text/javascript" src="/assets/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="/assets/shieldui-all.min.js"></script>
</head>
<body class="theme-light">
    <div id="wrapper">
        <g:render template="/templates/nav"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Heart Rate Page</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div id="chart"></div>
                        <script type="text/javascript">
                            $(function () {
                                var heartRates = new Array(${heartRates.size()});
                                var hours = new Array(${hours.size()});
                                <g:each in="${heartRates}" var="rate" status="i">
                                    heartRates[${i}] = ${rate}
                                </g:each>

                                <g:each in="${hours}" var="hour" status="i">
                                    hours[${i}] = "${hour}"
                                </g:each>

                                $("#chart").shieldChart({
                                    theme: "light",
                                    primaryHeader: {
                                        text: 'Heart Rate Measurement'
                                    },
                                    secondaryHeader: {
                                        text: 'Plot band indicates the normal range of heart rate at rest'
                                    },
                                    chartLegend: {
                                        enabled: false
                                    },
                                    exportOptions: false,
                                    axisX: {
                                        categoricalValues: hours
                                    },
                                    axisY: {
                                        min: 40,
                                        max: 140,
                                        title: {
                                            text: 'Beats per minute (bpm)'
                                        },
                                        plotLines: [
                                            { start: 60, drawWidth: 2, drawColor: 'green', zIndex: 2 },
                                            { start: 100, drawWidth: 2, drawColor: 'red', zIndex: 2 }
                                        ],
                                        plotBands: [
                                            { start: 60, end: 100, drawColor: 'rgba(214,235,248,0.8)' }
                                        ]
                                    },
                                    dataSeries: [{
                                        seriesType: 'line',
                                        collectionAlias: 'HR (bpm)',
                                        data: heartRates
                                    }]
                                });
                            });
                        </script>
                        <script language = "javascript">
                            $(document).ready(function () {
                                var elems = document.getElementsByTagName('tspan');
                                for(var i=0; i< elems.length; i++) {
                                    if (elems[i].innerHTML == "Demo Version" || elems[i].innerHTML == "Demo") {
                                        elems[i].setAttribute("display", "none");
                                    }
                                }
                            });
                        </script>
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
