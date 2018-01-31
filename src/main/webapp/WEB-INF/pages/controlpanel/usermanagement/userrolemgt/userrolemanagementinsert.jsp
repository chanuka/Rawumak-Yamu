<%-- 
    Document   : userrolemanagementinsert
    Created on : Jul 14, 2016, 9:29:07 AM
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
        <title>Add User Role</title>

        <script>




            function resetAllDataAdd() {

                $('#userRoleCode').val("");
                $('#userRoleCode').attr('readOnly', false);
                $("#userRoleCode").css("color", "black");
                $('#description').val("");
                $('#status').val("");


                $('#amessage').text("");

                jQuery("#gridtable").trigger("reloadGrid");
            }


        </script>
    </head>
    <body>
        <s:div id="amessage">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form cssClass="form" id="userroleadd" method="post" action="UserRole" theme="simple" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >User Role Code</label>
                        <s:textfield value="%{userRoleCode}" name="userRoleCode" id="userRoleCode" cssClass="form-control" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Description </label>
                        <s:textfield  value="%{description}" name="description" id="description" maxLength="64" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status </label>
                        <s:select  value="%{status}" id="status" list="%{statusList}"  headerKey="" headerValue="--Select Status--" cssClass="form-control" name="status" listKey="statuscode" listValue="description"/>
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
                            id="canclebtn"
                            onClick="resetAllDataAdd()"
                            cssClass="btn btn-default btn-sm"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="AddUserRole" var="inserturl"/>
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
