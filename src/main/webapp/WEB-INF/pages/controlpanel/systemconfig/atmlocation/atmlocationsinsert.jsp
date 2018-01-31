<%-- 
    Document   : atmlocationsinsert
    Created on : Jul 11, 2016, 9:33:05 AM
    Author     : jayana_i
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">
        <title>Insert ATM Locations</title>       
        <script>
            $.subscribe('resetAddButton', function (event, data) {
                $("#divmsginsert").text("");
                $('#conXL').val("");
                $("#latitude").val("");
                $("#longitude").val("");
                $("#address").val("");
                $("#description").val("");
                $("#atmcode").val("");
                $("#status").val("");
                $("#isAtm").attr('checked', false);
                $("#isCdm").attr('checked', false);
                $("#isBranch").attr('checked', false);
                $("#isAgent").attr('checked', false);
                $("#isShop").attr('checked', false);
            });

            function EraseDiv() {

                $("#divmsginsert").text("");
                $("#reset").click();

            }
        </script>

    </head>

    <body >
        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="taskadd" method="post"  theme="simple" cssClass="form" enctype="multipart/form-data">   

            <!--            <div class="row row_popup form-inline">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <input type="hidden" name="hiddenId" id="hiddenId" value="" />
            <%--<s:hidden id="conXL" name="conXL" ></s:hidden>--%>
            <span style="color: red">*</span><label >Upload CSV files</label>
            <input type="file" id="conXL" name="conXL" />

            <%--<s:file id="conXL" name="conXL" cssClass="form-control" />--%>
        </div>
        <div class="form-group" style=" margin-top: 20px;">
            <!s:url action="uploadATMLocations" var="uploadurl"/>
            <!sj:submit 
                button="true"
                value="Upload"
                href="%{uploadurl}"
                onClickTopics=""
                targets="divmsginsert"
                id="uploadbtn"
                cssClass="btn btn-sm active" 
                cssStyle="background-color: #ada9a9"
                />
        </div>
    </div>
</div>
<div class="row row_popup">
    <div class="horizontal_line_popup"></div>
</div>-->
            <div class="row row_popup">  
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Location Code</label>
                        <s:textfield name="atmcode" id="atmcode" cssClass="form-control" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Location Name</label>
                        <%--<s:textfield name="description" id="description" cssClass="form-control"  maxLength="100" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>--%>
                        <s:textfield name="description" id="description" cssClass="form-control"  maxLength="100" />
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Address</label>
                        <%--<s:textarea name="address" id="address" cssClass="form-control" rows="3"  maxLength="100" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9.,'/ ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9.,/' ]/g,''))"/>--%>
                        <s:textarea name="address" id="address" cssClass="form-control" rows="3"  maxLength="100" />
                        <label ></label>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Latitude</label>
                        <s:textfield name="latitude" id="latitude" cssClass="form-control" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>

                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Longitude</label>
                        <s:textfield name="longitude" id="longitude" cssClass="form-control" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>

                    </div>
                </div>
            </div>    

            <div class="row row_popup">   
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Status</label>
                        <s:select cssClass="form-control" id="status" list="%{statusList}"  name="status" 
                                  headerKey=""  listKey="statuscode" listValue="description" disabled="false" headerValue="-- Select Status --"/>                  
                    </div>
                </div>
                <div class="col-sm-5 form-inline">
                    <div class="form-group">
                        <span style="color: red"></span><label >&nbsp;ATM&nbsp;</label>
                            <s:checkbox id="isAtm" name="isAtm" cssClass="form-control" />                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >&nbsp;CDM&nbsp;</label>
                            <s:checkbox id="isCdm" name="isCdm" cssClass="form-control" />                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >Branch</label>
                            <s:checkbox id="isBranch" name="isBranch" cssClass="form-control" />                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >Agent</label>
                            <s:checkbox id="isAgent" name="isAgent" cssClass="form-control" />                  
                    </div>
                    <div class="form-group">
                        <span style="color: red"></span><label >Shop</label>
                            <s:checkbox id="isShop" name="isShop" cssClass="form-control" />                  
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <!--<label >Status Effective Date</label>-->
                        <%--<sj:datepicker cssClass="form-control" id="expdate" name="expdate" readonly="true"  maxDate="d" changeYear="true" buttonImageOnly="true" displayFormat="dd/mm/yy" onchange="disableButtons()"/>--%>
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
                        <s:url action="AddATMLocations" var="inserturl"/>
                        <sj:submit
                            button="true"
                            value="Add"
                            href="%{inserturl}"
                            onClickTopics=""
                            targets="divmsginsert"
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

