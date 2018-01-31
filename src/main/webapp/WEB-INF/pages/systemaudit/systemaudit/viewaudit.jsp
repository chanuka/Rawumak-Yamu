<%-- 
    Document   : viewaudit
    Created on : Jan 8, 2014, 1:23:34 PM
    Author     : chalitha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">
        <title>Insert Promotion</title>       
        <script type="text/javascript">
            
            //global variables for previous height
            var height_a;
            var height_u;
            $("#min").hide();
            // maximize popup dialog 
            function maximize(width, height) {
                $(window).scrollTop(0);
                height_a = $("#viewdialog").height();
                $("#viewdialog").height($(window).height() - 60);
                $(".ui-dialog").width($(window).width() - 40);
                $(".ui-dialog").height($(window).height() - 40);
                $(".ui-dialog").center();
                $("#max").hide();
                $("#min").show();
            }
            // reset to previous popup dialog
            function resetwindow() {
                $(window).scrollTop(0);
                $("#viewdialog").height(height_a);
                $(".ui-dialog").width("900");
                $(".ui-dialog").height("590");
                $(".ui-dialog").center();
                $("#max").show();
                $("#min").hide();
            }
            //center popup dialog
            jQuery.fn.center = function () {
                this.css("position", "fixed");
                this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) +
                        $(window).scrollTop()) + "px");
                this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) +
                        $(window).scrollLeft()) + "px");
                return this;
            };
            
            
            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewSystemAudit.action?";
            }

            function todo() {
                
                
                form = document.getElementById('auditform2');
                form.action = 'individualReportSystemAudit';
//                form.submit();
            }


        </script>
    </head>            
    <body>
        <div class="col-sm-12">
            <div style="text-align: right">
                <sj:submit 
                    id="max"
                    button="true" 
                    value="Maximize" 
                    onClick="maximize()"
                    onClickTopics="Maximize"
                    cssStyle="height: 16px;padding: 1px;width: 55px;font-size: 11px;background: orange;color: white;border-color: gray;"
                    />                          
                <sj:submit 
                    id="min"
                    button="true" 
                    value="Minimize" 
                    onClick="resetwindow()"
                    onClickTopics="Minimize"
                    cssStyle="height: 16px;padding: 1px;width: 55px;font-size: 11px;background: orange;color: white;border-color: gray;"
                    />                     
            </div>
        </div>
        <s:div id="divmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:set id="vview" var="vview"><s:property value="vview" default="false"/></s:set>    
        <s:set id="vgenerateview" var="vgenerateview"><s:property value="vgenerateview" default=""/></s:set>    
        <s:form id="auditform2" method="post" action="*SystemAudit" cssClass="form" theme="simple">

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Audit ID</label>
                        <s:label style="margin-bottom: 0px;" name="auditId"  value="%{auditDataBean.id}" cssClass="form-control"/>
                    </div>  
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >User Role</label>
                        <s:label style="margin-bottom: 0px;" name="userRole"  value="%{auditDataBean.userrole}"  cssClass="form-control"/>
                    </div>
                </div> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >IP Address</label>
                        <s:label style="margin-bottom: 0px;" name="ip"  value="%{auditDataBean.ip}" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Last Updated User</label>
                        <s:label style="margin-bottom: 0px;" name="user"  value="%{auditDataBean.user}" cssClass="form-control"/>
                    </div>  
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Last Updated Time</label>
                        <s:label style="margin-bottom: 0px;" name="created Date"  value="%{auditDataBean.lastUpdatedDate}" cssClass="form-control"/>
                    </div>
                </div>    
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>Task</label>
                        <s:label style="margin-bottom: 0px;" name="task"  value="%{auditDataBean.task}" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label >Section</label>
                        <s:label style="margin-bottom: 0px;" name="section"  value="%{auditDataBean.section}" cssClass="form-control"/>
                    </div> 
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label >Page</label>
                        <s:label style="margin-bottom: 0px;" name="page"  value="%{auditDataBean.page}" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>Description</label>
                        <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="description"  value="%{auditDataBean.description}" cssClass="form-control"/>
                    </div>
                </div>
            </div>        
            <div class="row row_popup"> 
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>Old Values</label>
                        <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="oldvalue"  value="%{auditDataBean.oldvalue}" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup"> 
                <div class="col-sm-12">
                    <div class="form-group">
                        <label >New Values</label>
                        <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="newvalue"  value="%{auditDataBean.newvalue}" cssClass="form-control"/>
                    </div>
                </div>
            </div>

            <div class="row row_popup text-right">
                <div class="col-sm-9"></div>
                <div class="col-sm-3">

                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <sj:submit
                            button="true"
                            value="View PDF"
                            id="viewindi" 
                            name="viewindi" 
                            onclick="todo()"  
                            disabled="#vgenerateview"
                            
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9"     
                            
                            />                        
                    </div>
                </div>
            </div>
        </s:form>

        <!-- end: BODY -->
    </body>
</html>