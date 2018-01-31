<%-- 
    Document   : promotionmgtedit
    Created on : Jul 11, 2016, 2:50:07 PM
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
        <title>Update Campaign</title>
        <script>
//            $(document).ready(function () {
//                alert("qq" + $('#startDateedit').val());
//
//            });


            function editPromotion(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindPromotionMgt.action',
                    data: {promotionId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#amessageedit').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#promotionIdedit').val("");
                            $('#descriptionedit').val("");
                            $('#titleedit').val("");
                            $('#startDateedit').val("");
                            $('#expDateedit').val("");
                            $('#sortOrderedit').val("");
                            $('#userLeveledit').val("");
                            $('#statusedit').val("");
                            $('#tabImgFile').val("");
                            $('#mobileImgFile').val("");
                            $('#updateButtonedit').button("disable");
                            $('#amessageeditedit').text("");
                        }
                        else {
                            $('#promotionIdedit').val(data.promotionId);
                            $('#promotionIdedit').attr('readOnly', true);
                            $('#descriptionedit').val(data.description);
                            $('#titleedit').val(data.title);
                            $('#startDateedit').val(data.startDate);
                            $('#expDateedit').val(data.expDate);
                            $('#sortOrderedit').val(data.sortOrder);
                            $('#userLeveledit').val(data.userLevel);
                            $('#statusedit').val(data.status);
                            $('#tabImgFile').val("");
                            $('#mobileImgFile').val("");
                            $("#tabImg_edit").attr("src", "data:image/jpeg;base64," + data.tabImgedit);
                            $('#mobileImg_edit').attr("src", "data:image/jpeg;base64," + data.mobileImgedit);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var promotionId = $('#promotionIdedit').val();
                editPromotion(promotionId);
            }

            function imageTMReset(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindPromotionMgt.action',
                    data: {promotionId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
//                        $('#amessageedit').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#promotionIdedit').val("");
                            $('#descriptionedit').val("");
                            $('#titleedit').val("");
                            $('#startDateedit').val("");
                            $('#expDateedit').val("");
                            $('#sortOrderedit').val("");
                            $('#userLeveledit').val("");
                            $('#statusedit').val("");
                            $('#tabImgFile').val("");
                            $('#mobileImgFile').val("");
                            $('#updateButtonedit').button("disable");
                            $('#SearchAudit').val("");
//                            $('#amessageeditedit').text("");
                        }
                        else {
                            $('#promotionIdedit').val(data.promotionId);
                            $('#promotionIdedit').attr('readOnly', true);
                            $('#descriptionedit').val(data.description);
                            $('#titleedit').val(data.title);
                            $('#startDateedit').val(data.startDate);
                            $('#expDateedit').val(data.expDate);
                            $('#sortOrderedit').val(data.sortOrder);
                            $('#userLeveledit').val(data.userLevel);
                            $('#statusedit').val(data.status);
                            $('#tabImgFile').val("");
                            $('#mobileImgFile').val("");
                            $("#tabImg_edit").attr("src", "data:image/jpeg;base64," + data.tabImgedit);
                            $('#mobileImg_edit').attr("src", "data:image/jpeg;base64," + data.mobileImgedit);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelDataTM() {
                var promotionId = $('#promotionIdedit').val();
//                imageTMReset(promotionId);
            }

            function changeTabImage() {
                $("#tabImgFile").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#tabImg_edit").attr("src", tmppath);
                });
            }

            function changeMobileImage() {
                $("#mobileImgFile").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#mobileImg_edit").attr("src", tmppath);
                });
            }

            function tabImgZoom(src) {

//                if (src == "data:image/jpeg;base64," || includes(src, "resouces/images/")) {
//                    alert("3");
//                } else {
                $("#tabDialog").dialog({
                    title: "Tab Image",
                    modal: true,
                    dialogClass: "dialogclass",
                    height: 450,
                    minWidth: 450
                });

                $('#tabView').attr('src', src);
//                }
            }

            function mobileImgZoom(src) {

//                if (src == "data:image/jpeg;base64," || includes(src, "resouces/images/"))
//                {
//
//                } else {
                $("#mobileDialog").dialog({
                    title: "Mobile Image",
                    modal: true,
                    dialogClass: "dialogclass",
                    height: 450,
                    minWidth: 450
                });
                $('#mobileView').attr('src', src);
//                }
            }
        </script>
        <style>
            .verticalLine {
                border-left:solid #e6e5e5;    
                height:100px;
                width:5em;
            }
        </style>
    </head>
    <body>
        <s:div id="amessageedit">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="promotionedit" method="post" action="PromotionMgt" theme="simple" cssClass="form" enctype="multipart/form-data">           

            <div class="row row_popup">
                <div class="col-sm-6">
                    <div class="form-group">
                        <input type="hidden" name="hiddenIdEdit" id="hiddenIdEdit" value="" />
                        <span style="color: red">*</span><label >Tab Image (Maximum size (w*h) : 1200x800 pixels)</label>
                        <div class="row">
                            <div class="col-sm-3">                               
                                <img onclick="tabImgZoom(this.src)" class="image"  src="data:image/jpeg;base64,<s:property value="tabImgedit"/>" id="tabImg_edit" name="tabImgedit">
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-7" style="margin-top: 40px;">
                                <s:file name="tabImg" id="tabImgFile" onclick="changeTabImage();"/>
                            </div>
                            <div class="col-sm-1">                    
                                <div class="verticalLine"></div>                  
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Mobile Image (Maximum size (w*h) : 1200x800 pixels)</label>   
                        <div class="row">
                            <div class="col-sm-3">
                                <img onclick="mobileImgZoom(this.src)" class="image" src="data:image/jpeg;base64,<s:property value="mobileImgedit"/>" id="mobileImg_edit" name="mobileImgedit">
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-8" style="margin-top: 40px;">
                                <s:file name="mobileImg" id="mobileImgFile" onclick="changeMobileImage();"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Campaign ID</label>
                        <s:textfield  name="promotionId" id="promotionIdedit" cssClass="form-control" readonly="true"/>
                    </div>   
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Description</label>
                        <s:textfield  name="description" id="descriptionedit" cssClass="form-control" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Title</label>
                        <s:textfield  name="title" id="titleedit" cssClass="form-control" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>Status</label>
                        <s:select  cssClass="form-control" value="%{status}" headerValue="-- Select Status --" headerKey=""  id="statusedit" list="%{statusList}"  name="status" listKey="statuscode" listValue="description" />                  
                    </div> 
                </div>
                <div class="col-sm-4">
                    <div class="form-group">                    
                        <span style="color: red">*</span><label>Sort Order</label>
                        <s:select cssClass="form-control" value="%{sortOrder}" id="sortOrderedit" name="sortOrder" list="%{sortOrderList}"  
                                  headerValue="-- Select Sort Order --" headerKey=""  listKey="key" listValue="value"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>User Level</label>
                        <s:select cssClass="form-control" id="userLeveledit" name="userLevel" list="%{userLevelList}" 
                                  headerValue="-- Select User Level --" headerKey=""  listKey="code" listValue="description"/>                                                                                            
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">

                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >From Date</label>
                        <sj:datepicker  
                            cssClass="form-control"
                            id="startDateedit" 
                            name="startDate"
                            readonly="true" 
                            buttonImageOnly="true" 
                            displayFormat="dd-mm-yy"
                            timepicker="true" 
                            minDate="0"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >To Date</label>
                        <sj:datepicker  
                            cssClass="form-control"
                            id="expDateedit" 
                            name="expDate"
                            readonly="true" 
                            buttonImageOnly="true" 
                            displayFormat="dd-mm-yy"
                            timepicker="true" 
                            minDate="0"/>
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
                        <s:url action="UpdatePromotionMgt" var="updateturl"/>
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
        </body>
        <div id="tabDialog" hidden="true" class="text-center">
            <img id="tabView" src="data:image/jpeg;base64,<s:property value="tabImgedit"/>"  style="width:350px;height:350px;margin-top: 20px;" alt="Tab Image">
        </div>
        <div id="mobileDialog" hidden="true" class="text-center">   
            <img id="mobileView" src="data:image/jpeg;base64,<s:property value="mobileImgedit"/>" style="width:350px;height:350px;margin-top: 20px;" alt="Mobile Copy">
        </div>
    </s:form>

</html>
