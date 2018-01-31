<%-- 
    Document   : atmlocationsedit
    Created on : Jul 12, 2016, 6:53:16 PM
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
        <title>Update ATM Locations</title>
        <script>
            function editATMLocations(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/findATMLocations.action',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#udescription').val("");
//                            $('#uatmcode').attr('readOnly',false );
                            $('#uaddress').val("");
                            $('#ulatitude').val("");
                            $('#ulongitude').val("");
                            $('#ustatus').val("ACT");
                            $("#isAtmEdit").attr('checked', false);
                            $("#isCdmEdit").attr('checked', false);
                            $("#isBranchEdit").attr('checked', false);
                            $("#isAgentEdit").attr('checked', false);
                            $("#isShopEdit").attr('checked', false);

                        }
                        else {
                            $('#atmcodeu').val(data.uatmcode);
                            $('#divmsgupdate').empty();
                            $('#udescription').val(data.udescription);
                            $('#uaddress').val(data.uaddress);
                            $('#ulatitude').val(data.ulatitude);
                            $('#ulongitude').val(data.ulongitude);
                            $('#ustatus').val(data.ustatus);

                            $("#isAtmEdit").prop("checked", data.isAtm);
                            $("#isCdmEdit").prop('checked', data.isCdm);
                            $("#isBranchEdit").prop('checked', data.isBranch);
                            $("#isAgentEdit").prop('checked', data.isAgent);
                            $("#isShopEdit").prop('checked', data.isShop);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }


            function cancelData() {
                var id = $('#id').val();
                editATMLocations(id);
            }


        </script>

    </head>
    <body>
        <s:div id="divmsgupdate">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="taskedit" method="post"  theme="simple" cssClass="form" >

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Location Code</label>
                        <s:textfield name="uatmcode" id="uatmcode" cssClass="form-control" readonly="true"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Location Name</label>
                        <%--<s:textfield name="udescription" id="udescription" cssClass="form-control"  maxLength="100" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>--%>
                        <s:textfield name="udescription" id="udescription" cssClass="form-control"  maxLength="100" />
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
                        <%--<s:textarea name="uaddress" id="uaddress" cssClass="form-control" rows="3"  maxLength="100" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9.,/' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9.,/' ]/g,''))"/>--%>
                        <s:textarea name="uaddress" id="uaddress" cssClass="form-control" rows="3"  maxLength="100" />
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Latitude</label>
                        <s:textfield name="ulatitude" id="ulatitude" cssClass="form-control" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>

                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Longitude</label>
                        <s:textfield name="ulongitude" id="ulongitude" cssClass="form-control" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>

                    </div>
                </div>
            </div>    

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Status</label>
                        <s:select cssClass="form-control" id="ustatus" list="%{statusList}"  name="ustatus" headerValue="-- Select Status --" headerKey=""  listKey="statuscode" listValue="description" disabled="false"/>                  
                    </div>
                </div>
                <div class="col-sm-5 form-inline">
                    <div class="form-group">
                        <span style="color: red"></span><label >&nbsp;ATM&nbsp;</label>
                            <s:checkbox id="isAtmEdit" name="isAtm" cssClass="form-control"/>                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >&nbsp;CDM&nbsp;</label>
                            <s:checkbox id="isCdmEdit" name="isCdm" cssClass="form-control" />                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >Branch</label>
                            <s:checkbox id="isBranchEdit" name="isBranch" cssClass="form-control" />                  
                    </div>

                    <div class="form-group">
                        <span style="color: red"></span><label >Agent</label>
                            <s:checkbox id="isAgentEdit" name="isAgent" cssClass="form-control" />                  
                    </div>
                    <div class="form-group">
                        <span style="color: red"></span><label >Shop</label>
                            <s:checkbox id="isShopEdit" name="isShop" cssClass="form-control" />                  
                    </div>
                </div>
                <!--                <div class="form-group">
                                    <label >Status Effective Date</label>
                <%--<sj:datepicker cssClass="form-control" id="expdate" name="expdate" readonly="true"  maxDate="d" changeYear="true" buttonImageOnly="true" displayFormat="dd/mm/yy" onchange="disableButtons()"/>--%>
            </div>               -->
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
                            onClick="cancelData()"
                            cssClass="btn btn-default btn-sm"
                            />                          
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="updateATMLocations" var="updateturl"/>
                        <s:hidden id="id" name="id"></s:hidden>
                        <sj:submit
                            button="true"
                            value="Update"
                            href="%{updateturl}"
                            targets="divmsgupdate"
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

