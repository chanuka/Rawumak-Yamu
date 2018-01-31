<%-- 
    Document   : transactiontypeedit
    Created on : Aug 1, 2016, 10:34:05 AM
    Author     : samith_k
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">
        <title>Update Transaction Type</title>

        <script>
            function editTransactionType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindTransactionType.action',
                    data: {transactiontypecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#transactiontypecodeedit').val("");
                            $('#transactiontypecodeedit').attr('readOnly', true);
                            $("#transactiontypecodeedit").css("color", "black");
                            $('#descriptionedit').val("");
                            $('#description_receiveredit').val("");
                            $('#sortkeyedit').val("");
                            $('#statusedit').val("");
//                            $('#statusedit').val('<s:property value="defaultStatus" />');
//                            $('#statusedit').prop('disabled', false);
                            $('#OTPReqStatusedit').val("");
                            $('#history_visibilityedit').val("");
                            $('#RiskReqStatusedit').val("");
                            $('#merchantTxnTypeedit').val("");
                            
                            $('#sortOrderEdit').val("");
//                           
                            $('#OTPReqStatusedit').val("");
                            $('#RiskReqStatusedit').val("");
//                            $('#OTPReqStatusedit').val('<s:property value="defaultOTPReqStatus" />');
//                            $('#RiskReqStatusedit').val('<s:property value="defaultRiskReqStatus" />');
//                            
                            $('#amessageedit').text("");
                            $('#updateButtonedit').button("disable");
                        } else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#transactiontypecodeedit').val(data.transactiontypecode);
                            $('#transactiontypecodeedit').attr('readOnly', true);
                            $("#transactiontypecodeedit").css("color", "#858585");
                            $('#descriptionedit').val(data.description);
                            $('#description_receiveredit').val(data.description_receiver);
                            $('#statusedit').prop('disabled', false);
                            $('#statusedit').val(data.status);
                            
                            $('#sortOrderEdit').val(data.sortOrder);
                            $('#oldsortKey').val(data.sortOrder);
                            
                            $('#OTPReqStatusedit').val(data.OTPReqStatus);
                            $('#history_visibilityedit').val(data.history_visibility);
                            $('#RiskReqStatusedit').val(data.riskReqStatus);
                            $('#merchantTxnTypeedit').val(data.merchantTxnType);
                            $('#amessageedit').text("");
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var transactiontypecode = $('#transactiontypecodeedit').val();
                editTransactionType(transactiontypecode);
            }


        </script>      
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="transactiontypeedit" method="post" action="TransactionType" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Transaction Type Code</label>
                        <s:textfield name="transactiontypecode" id="transactiontypecodeedit" maxLength="2" readonly="true" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Description</label>
                        <s:textfield  name="description" id="descriptionedit" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:select  id="statusedit" list="%{statusList}"  name="status" headerValue="--Select Status--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-4">  
                    <div class="form-group">    
                        <span style="color: red"></span><lable>Sort Key </lable>
                            <s:textfield  name="sortOrder" id="sortOrderEdit" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                            <s:textfield type="hidden" name="oldsortKey" id="oldsortKey" />
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>OTP Required</label>
                        <s:select  id="OTPReqStatusedit" list="%{OTPReqstatusList}"  name="OTPReqStatus"  headerValue="--Select OTP Required--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Risk Required </label>
                        <s:select  id="RiskReqStatusedit" list="%{RiskReqstatusList}"  name="RiskReqStatus" headerValue="--Select Risk Required--" headerKey="" listKey="statuscode" listValue="description"  cssClass="form-control"/>
                    </div>
                </div>     
            </div>
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Merchant Transaction Type</label>
                        <s:select  id="merchantTxnTypeedit" list="%{merchantTxnTypeList}"  name="merchantTxnType" headerValue="--Select Merchant Transaction Type--" headerKey="" listKey="typeCode" listValue="description"  cssClass="form-control"/>
                    </div>
                </div>     
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Description Receiver</label>
                        <s:textfield id="description_receiveredit"  name="description_receiver"  maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>History Visibility</label>
                        <s:select  id="history_visibilityedit" list="%{history_visibilityList}"  name="history_visibility" headerValue="--Select History Visibility--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
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
                <div class="col-sm-3 form-inline">
                    <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                        <sj:submit 
                            button="true" 
                            value="Reset" 
                            onClick="cancelData()"
                            cssClass="btn btn-default btn-sm"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                        <s:url action="UpdateTransactionType" var="updateturl"/>
                        <sj:submit
                            button="true"
                            value="Update"
                            href="%{updateturl}"
                            targets="amessageedit"
                            id="updateButtonedit"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9"
                            />     
                    </div>
                </div>
            </div>
        </s:form>
    </body>
</html>

