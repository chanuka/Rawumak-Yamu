<%-- 
    Document   : taskedit
    Created on : Jul 16, 2016, 11:29:04 PM
    Author     : dilanka_w
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
        <title>Insert Task</title> 
        <script type="text/javascript">
            function editTask(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindTask.action',
                    data: {taskCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#amessageedit').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#taskCodeEdit').val("");
                            $('#taskCodeEdit').attr('readOnly', false);
                            $("#taskCodeEdit").css("color", "black");
                            $('#descriptionEdit').val("");
//                            $('#sortKeyEdit').val("");
                            $('#statusEdit').val("");
                            $('#divmsg').text("");
                        }
                        else {
                            $('#taskCodeEdit').val(data.taskCode);
                            $('#taskCodeEdit').attr('readOnly', true);
                            $("#taskCodeEdit").css("color", "#858585");
                            $('#descriptionEdit').val(data.description);
                            $('#statusEdit').val(data.status);
//                            $('#sortKeyEdit').val(data.sortKey);
                        }
                    },
                    error: function(data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var taskCode = $('#taskCodeEdit').val();
                editTask(taskCode);
            }

        </script>
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <s:form id="taskedit" method="post" action="Task" theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Task Code </label>
                        <s:textfield value="%{taskCode}" cssClass="form-control" name="taskCode" id="taskCodeEdit" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" readonly="true"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Description</label>
                        <s:textfield value="%{description}" cssClass="form-control" name="description" id="descriptionEdit" maxLength="30" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
                <!--</div>-->
                <!--            <div class="row row_popup">
                                <div class="horizontal_line_popup"></div>
                            </div>-->
                <!--<div class="row row_popup">-->
                <!--                <div class="col-sm-4">
                                    <div class="form-group">
                <s:hidden id="oldsortkey" value="sortKey"></s:hidden>
                <span style="color: red">*</span><label>Sort Key </label>
                <s:textfield value="%{sortKey}" cssClass="form-control" name="sortKey" id="sortKeyEdit" maxLength="2" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
            </div>
        </div>-->
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:select value="%{status}" cssClass="form-control" id="statusEdit" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description"/>
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
                        <s:url action="UpdateTask" var="updateturl"/>
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
