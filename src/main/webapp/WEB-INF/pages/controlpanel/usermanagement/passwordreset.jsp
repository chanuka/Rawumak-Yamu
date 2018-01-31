<%-- 
    Document   : passwordreset
    Created on : Mar 7, 2014, 12:31:53 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<!--<html xmlns="http://www.w3.org/1999/xhtml">-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="resouces/css/common/common_popup.css">-->
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">
            function resetAllData() {
                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
                $('#divmsg').text("");
                logout(false);
            }
            function resetFieldData() {

                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
            }
            function logout(param) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/LogOutUserChangePassword.action',
                    data: {},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });

            }
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip({
                    'placement': 'right',
                    'container': 'body',
                });

            });
        </script>
        <style>               
            .tooltip {
                
                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
            }
            .tooltip-inner {
                min-width: 100px;
                max-width: 100%; 
                text-align: left;
            }
            .tooltip:after {
                right: 100%;
                top: 50%;
                border: solid transparent;
                content: " ";
                position: absolute;
                pointer-events: none;
                border-color: rgba(0, 0, 0, 0);
                border-right-color: #000000;
                border-width: 5px;
                margin-top: -5px;
            }
        </style>
    </head>

    <body style="">
        <jsp:include page="/header.jsp"/>

        <div class="main-container">


            <jsp:include page="/leftmenu.jsp">                    
                <jsp:param name="firstpage" value="0" />
            </jsp:include>  

            <div class="main-content">

                <div class="container" style="min-height: 760px;">


                    <!-- start: PAGE NAVIGATION BAR -->
                    <jsp:include page="/navbar.jsp"/>
                    <!-- end: NAVIGATION BAR -->

                    <div class="row">
                        <div id="content1">
                            <s:div id="space">
                            </s:div>
                            <s:div id="divmsg">
                                <s:actionerror theme="jquery"/>
                                <s:actionmessage theme="jquery"/>
                            </s:div>
                            <s:set var="vupdatepwd"><s:property value="vupdatepwd" default="true"/></s:set>
                                <div id="formstyle">
                                <s:form action="PasswordReset" theme="simple" method="post" id="pwdResetform" cssClass="form-inline">
                                    <s:hidden name="husername" id="husername" />
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Username </label>
                                                <s:textfield cssClass="form-control" name="username" id="username" disabled="true"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>User Role</label>
                                                <s:textfield cssClass="form-control" id="userrole"  name="userrole" disabled="true"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1"></div>

                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>Current Password </label>
                                                <s:password cssClass="form-control" name="currpwd" id="currpwd"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>New Password </label>
                                                <s:password cssClass="form-control a" name="newpwd" id="newpwd" cssStyle="form-control" data-toggle="tooltip"  data-html="true" title="%{pwtooltip}" />

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>Retype New Password </label>
                                                <s:password cssClass="form-control" name="renewpwd" id="renewpwd" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-9">
                                            <div class="form-group">
                                                <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                            </div>
                                        </div></div>
                                    <div class="row row_1">
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <s:url var="pwreseturl" action="Updatepaschanged"/>  
                                                <sj:submit 
                                                    button="true"
                                                    href="%{pwreseturl}"
                                                    disabled="#vupdatepwd"
                                                    value="Accept"
                                                    targets="divmsg"
                                                    cssClass="form-control btn_normal"
                                                    cssStyle="border-radius: 12px;background-color:#969595;color:white;"                                                 
                                                    />
                                            </div> 
                                            <div class="form-group">

                                                <sj:submit 
                                                    button="true" 
                                                    value="Reset" 
                                                    onClick="resetAllData()"                                                   
                                                    cssClass="form-control btn_normal"
                                                    cssStyle="border-radius: 12px;"
                                                    />
                                            </div>


                                        </div>
                                    </div>
                                </s:form>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>

        <jsp:include page="/footer.jsp"/>

    </body>
</html>



