<%-- 
    Document   : promotioninsert
    Created on : Jul 9, 2016, 1:15:56 PM
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
        <title>Insert Campaign</title>       
        <script>
            $.subscribe('resetAddButton', function (event, data) {
                $('#amessage').empty();
                $('#tabImg').val("");
                $('#mobileImg').val("");
                $('#description').val("");
                $('#title').val("");
                $('#startDate').val("");
                $('#expDate').val("");
                $('#sortOrder').val("");
                $('#userLevel').val("");
                $('#status').val("");
                $("#tabImg_add").attr("src", "");
                $('#mobileImg_add').attr("src", "");
            });

            function EraseDiv() {

                $("#amessage").text("");
//                $("#reset").click();

            }

            function changeTabImage() {
                $("#tabImg").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#tabImg_add").attr("src", tmppath);
                });
            }

            function changeMobileImage() {
                $("#mobileImg").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#mobileImg_add").attr("src", tmppath);
                });
            }


        </script>
        <style>
            .verticalLine {
                border-left:solid #e6e5e5;    
                height:75px;
                width:5em;
            }
        </style>
    </head>
    <body>
        <s:div id="amessage">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="promotionadd" method="post" action="PromotionMgt" theme="simple" cssClass="form" enctype="multipart/form-data">                      

            <div class="row row_popup">
                <div class="col-sm-6">
                    <div class="form-group">
                        <input type="hidden" name="hiddenId" id="hiddenId" value="" />
                        <span style="color: red">*</span><label >Tab Image (Maximum size (w*h) : 1200x800 pixels)</label>
                        <div class="row">
                            <div class="col-sm-3">
                                <img class="image" src="data:image/jpeg;base64,<s:property value="tabImgedit"/>" id="tabImg_add">
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-7" style="margin-top: 40px;">
                                <s:file name="tabImg" id="tabImg" onclick="changeTabImage();"/>                               
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
                                <img class="image" src="data:image/jpeg;base64,<s:property value="mobileImgedit"/>" id="mobileImg_add">
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-8" style="margin-top: 40px;">
                                <s:file name="mobileImg" id="mobileImg" onclick="changeMobileImage();"/>
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
                        <s:textfield name="promotionId" id="promotionId" cssClass="form-control" readonly="true"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Description</label>
                        <s:textfield name="description" id="description" cssClass="form-control" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Title</label>
                        <s:textfield name="title" id="title" cssClass="form-control" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Status</label>
                        <s:select cssClass="form-control" id="status" list="%{statusList}" headerValue="-- Select Status --"  headerKey=""
                                  name="status" listKey="statuscode" listValue="description" />                  
                    </div> 
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Sort Order</label>
                        <s:select cssClass="form-control" id="sortOrder" name="sortOrder" list="%{sortOrderList}"  
                                  headerValue="-- Select Sort Order --" headerKey=""  listKey="key" listValue="value"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>User Level</label>
                        <s:select cssClass="form-control" id="userLevel" name="userLevel" list="%{userLevelList}" 
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
                            id="startDate" 
                            name="startDate" 
                            readonly="true" 
                            buttonImageOnly="true" 
                            timepicker="true" 
                            displayFormat="dd-mm-yy" 
                            minDate="0"/>
                    </div>
                </div>
                <!--                <div class="col-sm-2">                    
                                    <div class="verticalLine"></div>                  
                                </div>-->
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >To Date</label>
                        <sj:datepicker 
                            cssClass="form-control" 
                            id="expDate" 
                            name="expDate" 
                            readonly="true" 
                            buttonImageOnly="true"
                            timepicker="true" 
                            displayFormat="dd-mm-yy" 
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
                            name="reset" 
                            id="reset" 
                            cssClass="btn btn-default btn-sm"
                            onClickTopics="resetAddButton"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="AddPromotionMgt" var="inserturl"/>
                        <sj:submit
                            button="true"
                            value="Add"
                            href="%{inserturl}"
                            onClickTopics=""
                            targets="amessage"
                            id="addbtn"
                            onclick="EraseDiv()"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9"                          
                            />                        
                    </div>
                </div>
            </div>
        </s:form>
    </body>
</html>
