<%-- 
    Document   : blocknotificationedit
    Created on : Jun 26, 2017, 11:14:28 AM
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
            function editBN(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findBlockNotification.action',
                    data: {walletId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#amessageedit').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#walletIdEdit').val("");
                            $("#walletIdEdit").css("color", "black");
                            $('#walletIdEdit').attr('readOnly', false);
                            $('#statusEdit').val("");
                            $('#fromdateEdit').val("");
                            $('#todateEdit').val("");
                            $('#divmsg').text("");
                        }
                        else {
                            $('#walletIdEdit').val(data.walletId);
                            $('#walletIdEdit').attr('readOnly', true);
                            $('#statusEdit').val(data.status);
                            $('#fromdateEdit').val(data.fromdate);
                            $('#todateEdit').val(data.todate);

                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var walletID = $('#walletIdEdit').val();
                editBN(walletID);
            }

            function statusset() {
                var status = $('#statusEdit').val();
                $('#statusUpdate').val(status);
            }

        </script>
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="pageedit" method="post" action="Page" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Wallet ID </label>
                        <s:textfield value="%{walletId}" cssClass="form-control" name="walletId" id="walletIdEdit" maxLength="10"  readonly="true" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:hidden name="statusEdit" id="statusUpdate"/>
                        <s:textfield value="Block" cssClass="form-control" name="status" id="o" maxLength="10"  disabled="true"/>
                        <%--<s:select value="%{status}" cssClass="form-control" name="status" id="statusEdit" list="%{statusList}"  disabled="true" headerValue="--Select Status--" headerKey="" listKey="statuscode" listValue="description"  />--%>
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
                        <%--<s:textfield  value="%{fromdate}" cssClass="form-control" name="fromdate" id="fromdateEdit" maxLength="128" />--%>
                        <sj:datepicker  minDate="d" cssClass="form-control"
                                        id="fromdateEdit" name="fromdate" readonly="true" 
                                        changeYear="true"  buttonImageOnly="true" 
                                        displayFormat="yy-mm-dd" 
                                        value="%{fromdate}"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>To Date</label>
                        <%--<s:textfield  value="%{todate}" cssClass="form-control" name="todate" id="todateEdit" maxLength="128" />--%>
                        <sj:datepicker  minDate="d" cssClass="form-control"
                                        id="todateEdit" name="todate" readonly="true" 
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
                            onClick="cancelData()"
                            cssClass="btn btn-default btn-sm"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">                                               
                        <s:url action="updateBlockNotification" var="updateturl"/>
                        <sj:submit

                            button="true"
                            value="Update"
                            href="%{updateturl}"
                            targets="amessageedit"
                            id="updateButton"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9"
                            />  
                    </div>
                </div>
            </div>
        </s:form>
    </body>
</html>