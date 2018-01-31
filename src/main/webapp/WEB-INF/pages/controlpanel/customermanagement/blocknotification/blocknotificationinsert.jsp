<%-- 
    Document   : blocknotificationinsert
    Created on : Jun 26, 2017, 11:13:58 AM
    Author     : prathibha_s
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
        <title>Insert Block Notification</title> 
        <script type="text/javascript">
            $.subscribe('resetAddButton', function (event, data) {
                $('#walletId').val("");
                $('#status').val("");
                $('#fromdate').val("");
                $('#todate').val("");
            });
        </script>
    </head>
    <body>
        <s:div id="amessage">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="blockNotificationadd" method="post" action="BlockNotification" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Wallet ID </label>
                        <s:textfield cssClass="form-control" name="walletId" id="walletId" maxLength="10" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:textfield cssClass="form-control" name="defaultStatus" id="statusSearcho" value="Block" disabled="true"/>
                        <%--<s:select cssClass="form-control" id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="statuscode" listValue="description" />--%>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>From Date</label>
                        <%--<s:textfield cssClass="form-control" name="fromdate" id="fromdate" maxLength="128" />--%>
                        <sj:datepicker  minDate="d" cssClass="form-control"
                                        id="fromdate" name="fromdate" readonly="true" 
                                        changeYear="true"  buttonImageOnly="true"  
                                        
                                        displayFormat="yy-mm-dd" 
                                        value="%{fromdate}"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>To Date</label>
                        <%--<s:textfield cssClass="form-control" name="todate" id="todate" maxLength="128" />--%>
                        <sj:datepicker  minDate="d" cssClass="form-control"
                                        id="todate" name="todate" readonly="true" 
                                        changeYear="true"  buttonImageOnly="true" 
                                        displayFormat="yy-mm-dd" 
                                        value="%{todate}"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>

            <div class="row row_popup form-inline">
                <div class="col-sm-9">
                    <div class="form-group">
                        <span class="mandatoryfield">Mandatory fields are marked with *</span>
                    </div>
                </div>
                <div class="col-sm-3 text-right">
                    <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                        <sj:submit 
                            button="true" 
                            value="Reset" 
                            name="reset" 
                            cssClass="btn btn-default btn-sm"
                            onClickTopics="resetAddButton"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="addBlockNotification" var="inserturl"/>
                        <sj:submit
                            button="true"
                            value="Add"
                            href="%{inserturl}"
                            onClickTopics=""
                            targets="amessage"
                            id="addbtn"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9"                          
                            />                        
                    </div>
                </div>
            </div>
        </s:form>
    </body>
</html>
