<%-- 
    Document   : sectionmgtinsert
    Created on : Jul 8, 2016, 9:36:25 AM
    Author     : dilanka_w
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">

        <title>Insert Section</title>
        <script>

            function resetAllDataAdd() {

                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                var startStatus = '<s:property value="vadd" />'
                $('#sectionCode').val("");
                $('#sectionCode').attr('readOnly', false);
                $("#sectionCode").css("color", "black");
                $('#description').val("");
                $('#sortKey').val("");
                $('#status').val("");
                $('#amessage').text("");
                $('#SearchAudit').val("");
                //               alert(" 2nd alert add status " + a  +" and updated stauts " + u);
                if (a == true && u == true) {
                    //                   alert("call 1st");
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'false') {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'true') {
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == false && u == true) {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
                jQuery("#gridtable").trigger("reloadGrid");
            }


            $.subscribe('resetAddButton', function(event, data) {
                $('#sectionCode').val("");
                $('#description').val("");
                $('#sortKey').val("");
                $('#status').val("");

            });
        </script>
    </head>
    <body>
        <s:div id="amessage">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form cssClass="form" id="sectionadd" method="post" action="Section" theme="simple" >
            <div class="row row_popup">  
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><lable>Section Code </lable>
                            <s:textfield name="sectionCode" id="sectionCode" maxLength="6" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"/>                        
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">        
                        <span style="color: red">*</span><lable>Description </lable>
                            <s:textfield  name="description" id="description" maxLength="26" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">    
                        <span style="color: red">*</span><lable>Sort Key </lable>
                            <s:textfield  name="sortKey" id="sortKey" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><lable>Status </lable>
                            <s:select  value="%{status}" id="status" list="%{statusList}"  headerKey="" headerValue="--Select Status--" cssClass="form-control" name="status" listKey="statuscode" listValue="description" />
                    </div>
                </div>   
            </div>
            <div class="row row_popup form-inline">
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
                            onClick="resetAllDataAdd()"
                            cssClass="btn btn-default btn-sm"
                            />                         
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="AddSection" var="inserturl"/>
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

