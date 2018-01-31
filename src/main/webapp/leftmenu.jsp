<%-- 
    Document   : leftmenu
    Created on : Dec 23, 2013, 12:24:38 PM
    Author     : janaka_h
--%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.rawmakyamu.util.common.SectionComparator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rawmakyamu.util.varlist.SessionVarlist"%>
<%@page import="com.rawmakyamu.util.mapping.Section"%>
<%@page import="com.rawmakyamu.util.mapping.Page"%>
<head>
    <link href="resouces/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="resouces/css/font-awesome.min.css">
    <link rel="stylesheet" href="resouces/css/style.css">
    <link rel="stylesheet" href="resouces/css/main.css">
    <link rel="stylesheet" href="resouces/css/main-responsive.css">
    <!--<link rel="stylesheet" href="resouces/css/all.css">-->
    <link rel="stylesheet" href="resouces/css/bootstrap-colorpalette.css">
    <link rel="stylesheet" href="resouces/css/perfect-scrollbar.css">
    <link rel="stylesheet" href="resouces/css/theme_light.css" type="text/css" id="skin_color">

    <script>
        jQuery(document).ready(function () {
            Main.init();
            //            Index.init();
        });
    </script>

    <script type="text/javascript">

//        $(document).ready(function () {
//
//            $(document).ajaxStart(function () {
//                $.blockUI({css: {
//                        border: 'transparent',
//                        backgroundColor: 'transparent',
//                        zIndex: '100000000'
//                    },
//                    message: '<img height="100" width="100" src="${pageContext.request.contextPath}/resouces/images/loading.gif" />',
//                    baseZ: 20000
//                });
//
//            });
//
//            $(document).ajaxStop(function () {
//                $.unblockUI();
//            });
//
//        });



        $(document).ready(function () {
            $(document).ajaxStart(function () {
                $("#wait").css("display", "block");
                $("#wait2").animate({backgroundColor: "rgba(0,0,0,0.57)"}, 10);
                $("#wait2").css("display", "block");
            });
            $(document).ajaxComplete(function () {
                $("#wait").fadeOut(300, function () {
                    $("#wait").css("display", "none");
                });
                $("#wait2").fadeOut(200, function () {
                    $("#wait2").animate({backgroundColor: "rgba(0,0,0,0)"}, 10);
                    $("#wait2").css("display", "none");
                });
            });
        });

        function sectionClick(myid) {
            //            var idsec=$.cookie("selectedsec");
            //            var idsecval="#"+idsec;
            //            alert(idsecval);
            //            $(idsecval).removeClass("open");
            //            $(idsecval).removeClass("active open");
            //            $(idsecval).addClass("");


            $.cookie("selectedsec", myid, {path: "/", expires: 1});
        }

        function pageClick(myid) {
            //            
            //            var idpage=$.cookie("selectedpage");
            //            var idval="#"+idpage;
            //            $(idval).removeClass("active open");

            $.cookie("selectedpage", myid, {path: "/", expires: 1});

        }

    </script>

    <script type="text/javascript">
        jQuery(document).ready(function () {

            var firPage = ${param.firstpage} + "1";

            if (firPage === "1001") {
                $.cookie("selectedpage", 1001, {path: "/", expires: 1});
                $.cookie("selectedsec", 1, {path: "/", expires: 1});
                var firURL = $("a", '#1001').attr('href');
                window.location = firURL;
                //            window.location= firURL+'?operationMode=STORE';  
            }

            if (firPage === "01") {
                $.cookie("selectedpage", 1001, {path: "/", expires: -1});
                $.cookie("selectedsec", 1, {path: "/", expires: -1});
            }

            var idsec = $.cookie("selectedsec");
            var idsecval = "#" + idsec;
            $(idsecval).addClass("active open");
            //            $(idsecval).addClass("active open"); 
            var id = $.cookie("selectedpage");
            var idval = "#" + id;
            $(idval).addClass("active open");
            //                        $(idval).removeClass("active open");

        });
    </script>

    <style>
        .btn-circle-micro {
            width: auto;
            height: 16px;
            text-align: center;
            padding: 8px 0;   
            font-size: 12px;
            line-height: 0.1;
            border-radius: 30px;
            padding-right: 1px;
            padding-left: 1px;
        }

    </style>
</head>

<div>

    <div id="wait" style="transition: 0.5s;display:none;width:89px;height:89px;position:absolute;top:40%;left:50%;padding:2px;z-index: 2000000000"><img src='${pageContext.request.contextPath}/resouces/images/loading.gif' width="100" height="100" /></div>
    <div id="wait2" style="
         transition: 0.5s;
         width: 100%;
         height: 100%;
         background-color: rgba(0, 0, 0, 0.57);
         position: fixed;
         z-index: 200000;
         top: 0;
         left: 0;
         display: none;
         "></div>
    <div class="main-navigation navbar-collapse collapse"style=" border-right:solid #e6e5e5;border-width: 0.5pt;">


        <!-- remove leftmenu collapse feature   -->
        <!--<div class="navigation-toggler">-->
        <!--            <i class="clip-chevron-left"></i>
                    <i class="clip-chevron-right"></i>-->

        <!--</div>-->

        <div class="ntb_main">
            Main Menu
        </div>
        <ul class="main-navigation-menu">


            <%

                try {

                    HashMap<Section, List<Page>> sectionPageList = (HashMap<Section, List<Page>>) request.getSession().getAttribute(SessionVarlist.SECTIONPAGELIST);
//                    String warnmsg = request.getSession().getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD);
            %>



            <%
                    SectionComparator sec1 = new SectionComparator();
                    Set<Section> section = new TreeSet<Section>(sec1);

                    Set<Section> section1 = sectionPageList.keySet();
                    for (Section sec2 : section1) {
                        section.add(sec2);
                    }

                    int secId = 1;
                    int pageId = 1000;

                    out.println("<ul class=\'main-navigation-menu\' id=\'treemenu\' >");

                    for (Section sec : section) {
                        int a = 0;

                        out.println("<li id=\'" + secId + "\'>");
                        out.println("<a href=\'javascript:void(0)\' onclick=\'sectionClick(" + secId + ")\'>");
                        out.println("<span class=\'title\'>" + sec.getDescription() + "</span><span class=\'glyphicon glyphicon-chevron-down pull-right\'></span>");
                        out.println("</a>");
                        out.println("<ul class=\'sub-menu\'>");

                        //                        out.println("d.add(" + i + "," + 0 + ",\'" + sec.getDescription() + "\');");
                        List<Page> pageList = sectionPageList.get(sec);
                        for (Page pageBean : pageList) {

                            pageId++;
                            out.println("<li id=\'" + pageId + "\'>");
                            out.println("<a href=\'" + request.getContextPath() + pageBean.getUrl() + ".action\'  onclick=\'pageClick(" + pageId + ")\'>");
                            out.println("<span class=\'title\'>" + pageBean.getDescription() + "</span>");
                            out.println("</a>");
                            out.println("</li>");

//                            out.println("d.add(" + j + "," + i + ",\'" + pageBean.getDescription() + "\'" + ",\' " + request.getContextPath() + pageBean.getUrl() + ".mpi\');");
                        }

                        out.println(" </ul>");
                        out.println(" </li>");

                        secId++;
                    }
                    out.println("</ul>");

                } catch (Exception ee) {

                    ee.printStackTrace();
                }
            %>


        </ul>

    </div></div>
