<%-- 
    Document   : userrole
    Created on : Jan 7, 2014, 9:40:14 AM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <%@include file="/stylelinks.jspf" %>
            <script type="text/javascript">

                function assigndes() {
                    var e = document.getElementById("userRole");
                    var strUser = e.options[e.selectedIndex].text;
                    $("#userRoleDes").val(strUser);                   
                    $('#amessage').text("");
                }

            </script>
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
                            <s:div id="amessage">
                                <s:actionerror theme="jquery"/>
                                <s:actionmessage theme="jquery"/>
                            </s:div>
                                <div id="formstyle">
                                <s:form id="userrolemgtmanage" method="post" action="ManageUserRolePrivilege" theme="simple" >
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>User Role </label>
                                                <s:hidden id="userRoleDes" name="userRoleDes"/>
                                                <s:select cssClass="form-control" id="userRole" list="%{userRoleList}"  name="userRole" listKey="userrolecode" listValue="description" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group radio">
                                                <s:radio id="Category" list="{'Sections'}" name="Category" value="'Sections'" />                                                
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group radio">
                                                <s:radio id="Category" list="{'Pages'}" name="Category"  />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group radio">
                                                <s:radio id="Category" list="{'Operations'}" name="Category" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group"> 
                                                <s:url var="manageurl" action="ManageUserRolePrivilege"/> 
                                                <sj:submit 
                                                    openDialog="remotedialog"
                                                    button="true"
                                                    onclick="assigndes()"
                                                    href="%{manageurl}"
                                                    value="Manage User Privilege"
                                                    id="addButton"
                                                    targets="remotedialog"   
                                                    cssClass="form-control btn_normal"
                                                    cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                    />
                                            </div>
                                        </div>
                                    </div>
                                </s:form>
                                <!-- Start add dialog box -->
                                <sj:dialog                                     
                                    id="remotedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Manage User Privilege"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="900"
                                    height="450"
                                    dialogClass= "dialogclass"
                                    />  
                            </div> 
                        </div>
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
