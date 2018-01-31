<%-- 
    Document   : transactiontypeinsert
    Created on : Jul 29, 2016, 4:41:18 PM
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
        <title>Insert Transaction Type</title>

        <script>
            function resetAllData() {
                $('#transactiontypecode').val("");
                $('#transactiontypecode').attr('readOnly', false);
                $('#description').val("");
                $('#amessage').text("");
                $('#OTPReqStatus').val("");
                $('#RiskReqStatus').val("");
                $('#status').val("");
                $('#sortOrder').val("");
            }

        </script>    

    </head>
    <body>
        <s:div id="amessage">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="transactiontypeadd" method="post" action="TransactionType" theme="simple" cssClass="form" enctype="multipart/form-data">
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Transaction Type Code</label>
                        <s:textfield name="transactiontypecode" id="transactiontypecode" maxLength="2" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Description</label>
                        <s:textfield  name="description" id="description" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:select  id="status" list="%{statusList}"  name="status" headerValue="--Select Status--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
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
                            <s:textfield  name="sortOrder" id="sortOrder" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>OTP Required</label>
                        <s:select  id="OTPReqStatus" list="%{OTPReqstatusList}"  name="OTPReqStatus"  headerValue="--Select OTP Required--" headerKey="" listKey="statuscode" listValue="description"  cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Risk Required</label>
                        <s:select  id="RiskReqStatus" list="%{RiskReqstatusList}"  name="RiskReqStatus"  headerValue="--Select Risk Required--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
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
                            id="canceladd"
                            onClick="resetAllData()"
                            cssClass="btn btn-default btn-sm"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="AddTransactionType" var="inserturl"/>
                        <sj:submit
                            button="true"
                            value="Add"
                            href="%{inserturl}"
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