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
                    url: '${pageContext.request.contextPath}/findMSMobUserParam.action',
                    data: {paramCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            //                            alert(data.message)
                            $('#paramcodeedit').val("");
                            $("#paramcodeedit").css("color", "black");
                            $('#paramcodeedit').attr('readOnly', true);
                            $('#descriptionedit').val("");
                            $('#valueedit').val("");
                            $('#amessageedit').text("");

                        } else {
                            $('#oldvalueedit').val(data.oldvalue);
                            $('#paramcodeedit').val(data.paramCode);
                            $('#paramcodeedit').attr('readOnly', true);
                            $("#paramcodeedit").css("color", "#858585");
                            $('#descriptionedit').val(data.description);
                            $('#valueedit').val(data.value);
                            $('#amessageedit').text("");
                        }
                    },
                    error: function (data) {
                        //                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }


            function resetFieldData() {
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function cancelDataEdit() {
                var sectioncode = $('#paramcodeedit').val();
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
                        <span style="color: red">*</span><lable>Param Code </lable>
                            <s:textfield  cssClass="form-control" name="paramCode" id="paramcodeedit" maxLength="16"   readonly="true"/>                        
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">        
                        <span style="color: red">*</span><lable>Description </lable>
                            <s:textfield  name="description" id="descriptionedit" maxLength="128" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-12">  
                    <div class="form-group">    
                        <span style="color: red">*</span><lable>Value</lable>
                            <s:textarea  name="value" id="valueedit" maxLength="1000" cssClass="form-control"/>

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
                        <s:url action="updateMSMobUserParam" var="updateturl"/>
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
