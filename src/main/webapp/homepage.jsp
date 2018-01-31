
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <sj:head jqueryui="true" jquerytheme="mytheme" customBasepath="resouces/themes"/>   
        <jsp:include page="/stylelinks.jspf"/>

    </head>


    <body style="">
        <!-- start: HEADER -->
        <jsp:include page="/header.jsp"/>
        <!-- end: HEADER -->
        <!-- start: MAIN CONTAINER -->
        <div class="main-container">

            <!-- start: SIDEBAR -->
        <jsp:include page="/leftmenu.jsp">              
         <jsp:param name="firstpage" value="100" />
       </jsp:include>
                
                
                
            <!-- end: SIDEBAR -->

            <!-- start: PAGE -->
            <div class="main-content">

                <div class="container" style="min-height: 760px;">

                    <!-- start: PAGE NAVIGATION BAR -->
                    <jsp:include page="/navbar.jsp"/>
                    <!-- end: NAVIGATION BAR -->


                    <!-- start: PAGE CONTENT -->
                    <div class="row">
                                                    <%--<s:div id="divmsg">--%>

                                <%--<s:actionerror theme="jquery"/>--%>
                                <%--<s:actionmessage theme="jquery"/>--%>
                            <%--</s:div>--%>

<!--                        <div class="col-sm-4">
                            <div class="core-box">
                                <div class="heading">
                                    <i class="clip-user-4 circle-icon circle-green"></i>
                                    <h2>Manage Users</h2>
                                </div>
                                <div class="content1">
                                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                                </div>
                                <a class="view-more" href="#">
                                    View More <i class="clip-arrow-right-2"></i>
                                </a>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="core-box">
                                <div class="heading">
                                    <i class="clip-clip circle-icon circle-teal"></i>
                                    <h2>Manage Orders</h2>
                                </div>
                                <div class="content">
                                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                                </div>
                                <a class="view-more" href="#">
                                    View More <i class="clip-arrow-right-2"></i>
                                </a>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="core-box">
                                <div class="heading">
                                    <i class="clip-database circle-icon circle-bricky"></i>
                                    <h2>Manage Data</h2>
                                </div>
                                <div class="content">
                                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                                </div>
                                <a class="view-more" href="#">
                                    View More <i class="clip-arrow-right-2"></i>
                                </a>
                            </div>
                        </div>-->
                    </div>




                    <!-- end: PAGE CONTENT-->
                </div>
            </div>
            <!-- end: PAGE -->
        </div>
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->



        <!-- end: BODY -->
    </body>
</html>