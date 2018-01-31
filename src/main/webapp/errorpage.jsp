<%-- 
    Document   : errorpage
    Created on : Jun 19, 2017, 4:31:47 PM
    Author     : dilanka_w
--%>

<%@page import="org.exolab.castor.mapping.xml.Param"%>

<!DOCTYPE html>
<html>
    <head>
        <link href="resouces/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link rel="stylesheet" href="resouces/css/font-awesome.min.css">

        <script type="text/javascript">
            function encryp() {
                if ($('#password').val() != "") {
                    var ps = $('#password').val();
                    $('#password').val(CryptoJS.MD5(ps));
                    //                    var value = '&lt;%= request.getMethod() %&gt;';
                    //                    alert(CryptoJS.MD5(ps));

                }
            }

        </script>     
        <style type="text/css">
            .main{
                position: absolute;
                margin: auto;
                top: 25%; left: 0; bottom: 0; right: 0;
            }
            .error-dis{
                text-align: center;                   
            }
            .copyright{
                bottom: 0;
                left: 0;
                position: fixed;
                right: 0;
                z-index: 1000;
                border-top-width: 1px;
                text-align: center;
            }
            .login-form{
                display: block;
                margin-left:20px;
                margin-bottom:20px;
                margin-right: 20px;
            }

        </style>
    </head>
    <!-- end: HEAD -->
    <!-- start: BODY -->
    <body>
        <!-- start: LOGIN BOX -->         
        <div class="container">
            <div class="row">
                <div class="main">
                    <div class="col-md-4 col-md-offset-4">

                        <div>
                            <div class="panel panel-info" style="border-color: #CED1D6;" >
                                <div>
                                    <div class="row">
                                        <img src="resouces/images/ntb.png" alt="NTB Logo" style="margin-top: 15px;
                                             margin-bottom: 5px;">                                    
                                    </div>						
                                </div>

                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <form id="login-form" class="login-form" novalidate="novalidate" action="CheckUserLogin" method="post">
                                                <div class="input-group form-group">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user" style="color:#999999;"></i></span>
                                                    <input type="text" class="form-control" placeholder="Username" name="username">
                                                </div>
                                                <div class="input-group form-group">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock" style="color:#999999;"></i></span>
                                                    <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                                                </div>									
                                                <div class="form-group">
                                                    <input type="submit" class="btn btn-sm form-control" value="LOGIN" style="background-color:#999999;color:white;font-weight:bold">
                                                </div>									
                                            </form>								
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="error-dis">
                                <i class="fa fa-remove-sign" style="color: red;">Page Not Found</i>

                            </div>


                        </div>
                    </div>	
                </div>			
            </div>
        </div>


        <!-- start: COPYRIGHT -->
        <div class="copyright">
            <!--<font id="versionno"></font>-->
            <%                String param1 = application.getInitParameter("version");
                out.println(param1);
            %>

            Copyright © 2016 <a href="http://www.epiclanka.net/">Epic Lanka (pvt) Ltd.</a> All rights reserved.
        </div>
        <!-- end: COPYRIGHT -->
    </body><!-- end: BODY -->
</html>
