<%-- 
    Document   : sectionmgtedit
    Created on : Jul 18, 2016, 9:51:54 AM
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
        <title>Update Section</title>

        <script>
            function editSection(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindSection.action',
                    data: {sectionCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            //                            alert(data.message)
                            $('#sectionCodeedit').val("");
                            $("#sectionCodeedit").css("color", "black");
                            $('#sectionCodeedit').attr('readOnly', true);
                            $('#descriptionedit').val("");
                            $('#sortKeyedit').val("");
                            $('#statusedit').val("");
                            $('#amessageedit').text("");
                            $('#updateButtonedit').button("disable");

                            var startStatus = <s:property value="vadd" />;
                            if (startStatus) {
                                $('#addButtonedit').button("disable");
                            } else {
                                $('#addButtonedit').button("enable");
                            }
                        }
                        else {
                            $('#oldvalueedit').val(data.oldvalue);
                            $('#sectionCodeedit').val(data.sectionCode);
                            $('#sectionCodeedit').attr('readOnly', true);
                            $("#sectionCodeedit").css("color", "#858585");
                            $('#descriptionedit').val(data.description);
                            $('#sortKeyedit').val(data.sortKey);
                            $('#oldsortKey').val(data.sortKey);
                            $('#statusedit').val(data.status);
                            $('#addButtonedit').button("disable");
                            $('#updateButtonedit').button("enable");
                            $('#amessageedit').text("");
                        }
                    },
                    error: function(data) {
                        //                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelDataEdit() {
                var sectioncode = $('#sectionCodeedit').val();
                editSection(sectioncode);
            }


        </script>      
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="userroleedit" method="post" action="Section" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><lable>Section Code </lable>
                            <s:textfield  cssClass="form-control" name="sectionCode" id="sectionCodeedit" maxLength="6"  onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" readonly="true"/>                        
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">        
                        <span style="color: red">*</span><lable>Description </lable>
                            <s:textfield  name="description" id="descriptionedit" maxLength="26" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                            <s:textfield  name="sortKey" id="sortKeyedit" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                            <s:textfield type="hidden" name="oldsortKey" id="oldsortKey" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><lable>Status </lable>
                            <s:select  value="%{status}" id="statusedit" list="%{statusList}" cssClass="form-control"  headerKey="" headerValue="--Select Status--"  name="status" listKey="statuscode" listValue="description" />
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
                            onClick="cancelDataEdit()"
                            cssClass="btn btn-default btn-sm"
                            />                         
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="UpdateSection" var="updateturl"/>
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
