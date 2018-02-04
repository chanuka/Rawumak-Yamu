<%-- 
    Document   : drivertrackingmgt
    Created on : Feb 4, 2018, 11:58:22 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="/stylelinks.jspf" %>
        <title>Page Management</title>

        <style>
            #map {
                height: 550px;
                width: 100%;
            }

        </style>
    </head>
    <body style="">
        <jsp:include page="/header.jsp"/>
        <div class="main-container">
            <jsp:include page="/leftmenu.jsp"/>
            <div class="main-content">
                <div class="container" style="min-height: 760px;">
                    <!-- start: PAGE NAVIGATION BAR -->
                    <jsp:include page="/navbar.jsp"/>
                    <!-- end: NAVIGATION BAR -->
                    <div class="row">
                        <div id="content1">

                            <div id="map"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>

            function initMap() {

                var hambanthota = {
                    info: '<strong>Hambanthota</strong><br>\
                                        5224 N Broadway St<br> Chicago, IL 60640<br>',
                    lat: 6.124593,
                    long: 81.101074
                };

                var kandy = {
                    info: '<strong>Kandy</strong><br>\
                                        1025 W Belmont Ave<br> Chicago, IL 60657<br>',
                    lat: 7.290572,
                    long: 80.633728
                };

                var colombo = {
                    info: '<strong>Colombo</strong><br>\r\
                                        6600 N Sheridan Rd<br> Chicago, IL 60626<br>',
                    lat: 6.927079,
                    long: 79.861244
                };

                var maharagama = {
                    info: '<strong>Maharagama</strong><br>\r\
                                        Railway Station<br> Maharagama<br>',
                    lat: 6.848226,
                    long: 79.926769
                };

                var pannipitiya = {
                    info: '<strong>Pannipitiya</strong><br>\r\
                                        Darmapala Vidyalaya<br> Pannipitiya, Maharagama<br>',
                    lat: 6.8433,
                    long: 79.9553
                };

                var Pamunuwa = {
                    info: '<strong>Pamunuwa</strong><br>\r\
                                        32/5 CIB Shop<br> Pamunuwa, Homagama<br>',
                    lat: 6.858974,
                    long: 79.931862
                };

                var Pizzahut = {
                    info: '<strong>Pizza Hut</strong><br>\r\
                                        301 N Pizza Hut<br>Maharagama<br>',
                    lat: 6.858974,
                    long: 79.930783
                };

                var kottawa = {
                    info: '<strong>kottawa</strong><br>\r\
                                        Bus Stand<br>Kottawa<br>',
                    lat: 6.841165,
                    long: 79.965432
                };

                var homagama = {
                    info: '<strong>Homagama</strong><br>\r\
                                        NSB Bank<br>Homagama<br>',
                    lat: 6.843276,
                    long: 80.003183
                };

                var malabe = {
                    info: '<strong>Malabe</strong><br>\r\
                                        Bus Stand<br>Kandy road,Malabe<br>',
                    lat: 6.906079,
                    long: 79.969628
                };

                var hokandara = {
                    info: '<strong>Hokandara</strong><br>\r\
                                        Hokandara Junction<br>Hokandara<br>',
                    lat: 6.874319,
                    long: 79.969628
                };

                var mcnugegoda = {
                    info: '<strong>McDonalds</strong><br>\r\
                                        McDonalds<br>Nugegoda<br>',
                    lat: 6.870545,
                    long: 79.886227
                };

                var bokundara = {
                    info: '<strong>Bokundara</strong><br>\r\
                                        Bokundara<br>Piliyandala<br>',
                    lat: 6.816433,
                    long: 79.920669
                };

                var locations = [
                    [hambanthota.info, hambanthota.lat, hambanthota.long, 0],
                    [kandy.info, kandy.lat, kandy.long, 1],
                    [colombo.info, colombo.lat, colombo.long, 2],
                    [maharagama.info, maharagama.lat, maharagama.long, 2],
                    [pannipitiya.info, pannipitiya.lat, pannipitiya.long, 2],
                    [Pamunuwa.info, Pamunuwa.lat, Pamunuwa.long, 2],
                    [Pizzahut.info, Pizzahut.lat, Pizzahut.long, 2],
                    [kottawa.info, kottawa.lat, kottawa.long, 2],
                    [homagama.info, homagama.lat, homagama.long, 2],
                    [malabe.info, malabe.lat, malabe.long, 2],
                    [hokandara.info, hokandara.lat, hokandara.long, 2],
                    [mcnugegoda.info, mcnugegoda.lat, mcnugegoda.long, 2],
                    [bokundara.info, bokundara.lat, bokundara.long, 2]
                ];

                var map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 12,
                    center: new google.maps.LatLng(6.848226, 79.926769),
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });

                var icon = {
                    url: "resouces/images/abc.png",
                    anchor: new google.maps.Point(25, 50),
                    scaledSize: new google.maps.Size(30, 50),
                    animated: true
                }

                var infowindow = new google.maps.InfoWindow({});

                var marker, i;

                for (i = 0; i < locations.length; i++) {
                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                        map: map,
                        icon: icon
                    });

                    google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            infowindow.setContent(locations[i][0]);
                            infowindow.open(map, marker);
                        }
                    })(marker, i));
                }
            }
        </script>
        <script async defer 
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAslstfBjdBfAkcRyX-ISvB5rT7IZI8hos&callback=initMap">
        </script>
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->
        <!-- end: BODY -->
    </body>
</html>
