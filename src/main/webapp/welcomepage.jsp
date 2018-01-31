<%-- 
    Document   : welcomepage
    Created on : Jun 6, 2014, 1:45:39 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 

        <title>TAXI Service</title>

        <%@include file="/stylelinks.jspf" %>
    </head>
    <script type="text/javascript">
        function myFunction() {
            window.open("${pageContext.request.contextPath}/login.jsp", "MsgWindow", "width=1350, height=660,scrollbars=1,resizable=yes");
        }

    </script>
    <style>
        img.displayed{


            /*margin-top: 100px;*/
            margin-bottom: auto;

        }
        .fit { /* set relative picture size */
            max-width: 100%;
            max-height: 100%;
        }
        .center {
            /*display: block;*/
            margin: auto;
        }

    </style>
    <body style="background-color: black !important;">
               <div style=" background: black;
         height: 5px;margin-top: 100px;">
    </div>
               <div style=" background: #e6b800;
         height: 2px;">
    </div>
                 <div style=" background: black;
         height: 2px;">
    </div>
        <div>
        <img class="displayed fit center" alt="welcome image"  src="resouces/images/welcome.png"  usemap="#loginmap" />
        <map name="loginmap">
            <area shape="rect" coords="450,90,1000,350" alt="text" onclick="myFunction()" href="#"  >
        </map>
        </div>
             <div style=" background: black;
        height: 2px;"></div>
            <div style=" background: #e6b800;
         height: 2px;">
    </div>
    </body>
</html>
