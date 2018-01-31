<%-- 
    Document   : userrolemanagementedit
    Created on : Jul 14, 2016, 11:15:31 AM
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
        <title>Update User Role</title>

        <script>


            function editUserRole(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindUserRole.action',
                    data: {userRoleCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            //                                alert(data.message)
                            $('#userRoleCodeedit').val("");
                            $("#userRoleCodeedit").css("color", "black");
                            $('#userRoleCodeedit').attr('readOnly', false);
                            $('#descriptionedit').val("");
//                            $('#userRoleLeveledit').val("");
//                            $('#userRoleLeveledit').prop('disabled', true);
                            $('#statusedit').val('<s:property value="defaultStatus" />');
                            $('#statusedit').prop('disabled', true);
                            $('#divmsgedit').text("");
                            $('#updateButtonedit').button("disable");

                            var startStatus = <s:property value="vadd" />;
                            if (startStatus) {
                                $('#addButtonedit').button("disable");
                            } else {
                                $('#addButtonedit').button("enable");
                            }


                        } else {
                            $('#userRoleCodeedit').val(data.userRoleCode);
                            $('#userRoleCodeedit').attr('readOnly', true);
                            $("#userRoleCodeedit").css("color", "#858585");
                            $('#descriptionedit').val(data.description);
                            $('#statusedit').prop('disabled', false);
                            $('#statusedit').val(data.status);
//                            $('#userRoleLeveledit').prop('disabled', false);
//                            $('#userRoleLeveledit').val(data.userRoleLevel);
                            $('#addButtonedit').button("disable");
                            $('#updateButtonedit').button("enable");
                            $('#amessageedit').text("");


                        }
                    },
                    error: function (data) {
                        //                            $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelDataEdit() {
                var userrolecode = $('#userRoleCodeedit').val();
                editUserRole(userrolecode);

            }


        </script>      
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="userroleedit" method="post" action="UserRole" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>User Role code</label>
                        <s:textfield readonly="true" value="%{userRoleCode}" cssClass="form-control" name="userRoleCode" id="userRoleCodeedit" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))"/>
                    </div> 
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Description</label>
                        <s:textfield value="%{description}" cssClass="form-control" name="description" id="descriptionedit" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status </label>
                        <s:select  value="%{status}" id="statusedit" list="%{statusList}"  headerKey="" headerValue="--Select Status--" name="status" listKey="statuscode" listValue="description" cssClass="form-control"/>
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
                        <s:url action="UpdateUserRole" var="updateturl"/>
                        <sj:submit
                            button="true"
                            value="Update"
                            href="%{updateturl}"
                            targets="amessageedit"
                            onclick="clickEdit()"
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
