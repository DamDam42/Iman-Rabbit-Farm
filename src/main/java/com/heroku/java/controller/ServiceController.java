<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Ansonika">
    <title>D Chalet Ombak Biru</title>
    <!-- Favicons-->
    <link rel="shortcut icon" th:href="@{stylesheets/img/favicon.png}" type="image/x-icon">
    <link rel="apple-touch-icon" type="image/x-icon"
        th:href="@{stylesheets/img/apple-touch-icon-57x57-precomposed.png}">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="72x72"
        th:href="@{stylesheets/img/apple-touch-icon-72x72-precomposed.png}">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="114x114"
        th:href="@{stylesheets/img/apple-touch-icon-114x114-precomposed.png}">
    <link th:rel="apple-touch-icon" type="image/x-icon" sizes="144x144"
        th:href="@{stylesheets/img/apple-touch-icon-144x144-precomposed.png}">

    <!-- GOOGLE WEB FONT-->
    <link
        href="https://fonts.googleapis.com/css2?family=Caveat:wght@400;500&family=Montserrat:wght@300;400;500;600;700&display=swap"
        rel="stylesheet">

    <!-- BASE CSS -->
    <link th:href="@{stylesheets/css/bootstrap.min.css}" rel="stylesheet">
    <link th:href="@{stylesheets/css/style.css}" rel="stylesheet">
    <link th:href="@{stylesheets/css/vendors.min.css}" rel="stylesheet">

    <!-- SEPCIFIC CSS -->
    <link th:href="@{stylesheets/css/flex_slider.css}" rel="stylesheet">

    <!-- YOUR CUSTOM CSS -->
    <link th:href="@{stylesheets/css/custom.css}" rel="stylesheet">
</head>

<body class="datepicker_mobile_full">
    <div class="layer"></div>
    <header class="reveal_header" style="background-color: #24262d;">
        <div class="container-fluid">
          <div class="row align-items-center">
            <div class="col-6">
              <a th:href="@{/index}" class="logo_normal"><img th:src="@{stylesheets/img/logo.png}" width="135" height="45" alt=""></a>
              <a th:href="@{/index}" class="logo_sticky"><img th:src="@{stylesheets/img/logo_sticky.png}" width="135" height="45" alt=""></a>
            </div>
            <div class="col-6">
              <nav>
                <ul>
                  <li><a class="btn_1 btn_scrollto">Manager</a></li>
                  <li>
                    <div class="hamburger_2 open_close_nav_panel">
                      <div class="hamburger__box">
                        <div class="hamburger__inner"></div>
                      </div>
                    </div>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div><!-- /container -->
      </header><!-- /Header -->

    <!-- -------------------- sidebar panel ------------------------- -->
    <div class="nav_panel">
        <a th:href="@{#}" class="closebt open_close_nav_panel"><i class="bi bi-x"></i></a>
        <div class="logo_panel"><img th:src="@{stylesheets/img/logo_sticky.png}" width="135" height="45" alt=""></div>
        <div class="sidebar-navigation">
          <nav>
            <ul class="level-1">
              <li class=""><a th:href="@{/managerHome}"><i class="bi bi-house" style="font-size: 25px;"></i>  &nbsp;Home</a></li>
              <li class=""><a th:href="@{/managerUpdateStatus}"><i class="bi bi-clipboard-check" style="font-size: 25px;"></i>&nbsp; Room Reservation</a></li>
              <li><a th:href="@{/managerStaffList}"><i class="bi bi-person-badge" style="font-size: 25px;"></i>&nbsp; Manage Staff</a></li>
              <li><a th:href="@{/managerRoomList}"><i class="bi bi-pencil-square" style="font-size: 25px;"></i>&nbsp; Manage Room</a></li>
              <li><a th:href="@{/managerServiceList}"><i class="bi bi-nut" style="font-size: 25px;"></i>&nbsp; Manage Room Service</a></li>
              <li><a th:href="@{/managerGuestList}"><i class="bi bi-people" style="font-size: 25px;"></i>&nbsp; Manage Guest</a></li>
              <li><a th:href="@{/managerGenerateReport}"><i class="bi bi-file-earmark-bar-graph" style="font-size: 25px;"></i>&nbsp; Generate Report</a></li>
              <li class="mt-5"><a th:href="@{/guestLogin}" class="btn btn-danger pt-3" style="color: white; border-radius: 10px 10px 10px 10px; height: 50px;">Logout</a></li>
            </ul>
            <div class="panel_footer">
              <div class="copy">
                <div class="container py-3">
                  © Ombak Biru Chalet Malaysia
                </div>
              </div>
            </div>
            <!-- /panel_footer -->
          </nav>
        </div>
        <!-- /sidebar-navigation -->
    </div>
    <!-- /nav_panel -->

    <main>
        <div class="container mt-5">
            &nbsp;
        </div>

        <div class="container mt-5 mb-5 align-items-center">
            <div class="row">
                <div class="col">

                </div>

                <div class="col-md card card-body py-5">
                    <!-- 
                        <div class="col-md-12 text-center">
                            <div class="">
                                <h1>Update Room Service</h1>
                            </div>
                        </div>
                    </div> -->
                    <div class="row m-5">
                        <div class="col-2 text-center">
                            <a th:href="@{/managerServiceList}">
                                <div class="bi bi-arrow-left-circle" style="font-size: 50px;"></div>
                            </a>
                        </div>
                        <div class="col-md-8 text-center pt-3">
                            <div class="">

                                <h1>Room Information</h1>
                            </div>
                        </div>
                        <div class="col-2">

                        </div>
                    </div>
                    <div class="row m-3">
                        <div class="col-1"></div>
                        <div class="col pt-3">
                            <span>
                                <h6>Service ID</h6>
                            </span>
                        </div>
                        <div class="col-8">
                            <input type="text" name="serviceID" id="" class="form-control" th:value ="${service.serviceID}" readonly>
                        </div>
                    </div>

                    <div class="row m-3">
                        <div class="col-1"></div>
                        <div class="col pt-3">
                            <span>
                                <h6>Service Name</h6>
                            </span>
                        </div>
                        <div class="col-8">
                            <input type="text" name="serviceName" id="" class="form-control" th:value ="${service.serviceName}" readonly>
                        </div>
                    </div>

                    <div class="row m-3">
                        <div class="col-1"></div>
                        <div class="col pt-3">
                            <span>
                                <h6>Service Type</h6>
                            </span>
                        </div>
                        <div class="col-8">
                            <input type="text" name="serviceType" id="" class="form-control" th:value ="${service.serviceType}" readonly>
                        </div>
                    </div>

                    <!-- <th:block th:if="${service.serviceType == 'Room Service'}">
                        <div class="row m-3">
                            <div class="col-1"></div>
                            <div class="col pt-3">
                                <span>
                                    <h6>Balance</h6>
                                </span>
                            </div>
                            <div class="col-8">
                                <input type="text" name="balance" id="" class="form-control" th:value ="${service.balance}" readonly>
                            </div>
                        </div>
                    </th:block>

                    <th:block th:if="${service.serviceType == 'Event Service'}">
                        <div class="row m-3">
                            <div class="col-1"></div>
                            <div class="col pt-3">
                                <span>
                                    <h6>Capacity</h6>
                                </span>
                            </div>
                            <div class="col-8">
                                <input type="text" name="eventCapacity" id="" class="form-control" th:value ="${service.eventCapacity}" readonly>
                            </div>
                        </div>
                    </th:block> -->

                    <div class="row m-3">
                        <div class="col-1"></div>
                        <div class="col pt-3">
                            <span>
                                <h6>Service Price</h6>
                            </span>
                        </div>
                        <div class="col-8">
                            <input type="text" name="servicePrice" id="" class="form-control" th:value ="${service.servicePrice}" readonly>
                        </div>
                    </div>

                    <div class="row m-5">
                        <div class="col text-center">
                            <a th:href="@{/managerUpdateService?serviceID=}+${service.serviceID}" th:value="${service.serviceID}" class="btn btn-dark btn-lg"
                            style="border-radius: 3px 3px 3px 3px; height: auto; width:150px">Edit</a>
                            &nbsp;
                            <a th:href="@{/managerServiceList}" class="btn btn-danger btn-lg" style="border-radius: 3px 3px 3px 3px; height: auto; width:150px">Cancel</a>
                        </div>
                    </div>

                </div>
                <div class="col">
                </div>
            </div>

            <div class="container mt-5">
                &nbsp;
            </div>
        </div>
    </main>

    <footer class="revealed">
        <div class="footer_bg">
            <div class="gradient_over"></div>
    
        </div>
        <div class="container">
            <div class="row move_content">
                <div class="col-lg-4 col-md-12">
                    <h5>Contacts</h5>
                    <ul>
                        <li>Jalan Telok Gong / Pengkalan Balak, Kampung Sungai Tuang<br>78300 Masjid Tanah,
                            Melaka<br><br></li>
                        <li><strong><a th:href="@{#0}">dchaletombakbiru@gmail.com</a></strong></li>
                        <li><strong><a th:href="@{#0}">016-2115359/012-2431337</a></strong></li>
                    </ul>
                    <div class="social">
                        <ul>
                            <li><a th:href="@{#0}"><i class="bi bi-instagram"></i></a></li>
                            <li><a th:href="@{#0}"><i class="bi bi-whatsapp"></i></a></li>
                            <li><a th:href="@{#0}"><i class="bi bi-facebook"></i></a></li>
                            <li><a th:href="@{#0}"><i class="bi bi-twitter"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!--/container-->
        <div class="copy">
            <div class="container">
                © Ombak Biru Chalet - by <a href="#">Ocean Blue Lemond</a>
            </div>
        </div>
    </footer>
    <!-- /footer -->
    
    <div class="progress-wrap">
        <svg class="progress-circle svg-content" width="100%" height="100%" viewBox="-1 -1 102 102">
            <path d="M50,1 a49,49 0 0,1 0,98 a49,49 0 0,1 0,-98" />
        </svg>
    </div>
    <!-- /back to top -->
    <!-- COMMON SCRIPTS -->
    <script th:src="@{stylesheets/js/common_scripts.js}"></script>
    <script th:src="@{stylesheets/js/common_functions.js}"></script>
    <script th:src="@{stylesheets/js/datepicker_inline.js}"></script>
    <script th:src="@{stylesheets/phpmailer/validate.js}"></script>
    
    <!-- SPECIFIC SCRIPTS -->
    <script th:src="@{stylesheets/js/jquery.flexslider.min.js}"></script>
    <script th:src="@{stylesheets/js/slider_func.js}"></script>
</body>
</html>
