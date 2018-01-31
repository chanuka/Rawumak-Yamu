<%-- 
    Document   : systemuseredit
    Created on : Jul 15, 2016, 11:48:46 AM
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
        <title>Update System User</title>
        <script>

            function editSystemUser(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findSystemUser.action',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            //                                                        alert(data.message)
                            $('#e_username').attr('readOnly', false);
                            $('#e_username').val("");
                            $("#e_username").css("color", "black");
                            $('#e_password').val("");
                            $('#e_confirmpassword').val("");
                            //                            $('#expirydate').val("");
                            $('#e_expirydate').val(data.expirydate);
                            $("#e_expirydate").css("color", "#858585");
                            $('#e_userrole').val("");
//                            $('#dualauthuser').val("");
                            $('#e_status').val("");
                            $('#e_fullname').val("");
                            $('#e_serviceid').val("");
                            $('#e_address1').val("");
                            $('#e_city').val("");
                            $('#e_contactno').val("");
                            $('#e_email').val("");
                            $('#e_nic').val("");
                            $('#e_dateofbirth').val("");
                            $('#divmsgupdate').text("");

                        } else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#e_username').val(data.username);
                            $('#e_username').attr('readOnly', true);
                            $("#e_username").css("color", "#858585");
                            $('#e_expirydate').val(data.expirydate);
                            $("#e_expirydate").css("color", "#858585");
                            $('#e_userrole').val(data.userrole);
                            $('#e_status').val(data.status);
                            $('#e_fullname').val(data.fullname);
                            $('#e_serviceid').val(data.serviceid);
                            $('#e_address1').val(data.address1);
                            $('#e_city').val(data.city);
                            $('#e_contactno').val(data.contactno);
                            $('#e_email').val(data.email);
                            $('#e_nic').val(data.nic);
                            $('#e_dateofbirth').val(data.dateofbirth);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }
            function cancelData() {
                var id = $('#e_username').val();
                editSystemUser(id);
            }

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }

        </script>

    </head>
    <body >

        <s:div id="divmsgupdate">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>


        <s:form id="systemuseredit" method="post" action="SystemUser"  theme="simple" cssClass="form"> 
            <s:hidden name="oldvalue" id="oldvalue"/>

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Username</label>
                        <s:textfield name="username" id="e_username"  maxLength="30" readonly="true" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Full Name</label>
                        <s:textfield name="fullname" id="e_fullname" cssClass="form-control" maxLength="30" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >NIC</label>
                        <s:textfield name="nic" id="e_nic" cssClass="form-control" maxLength="12"  onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                    </div>
                </div>

            </div>

            <div class="row row_popup">   
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span> <label >User Role</label>
                        <s:select  id="e_userrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span> <label >Status</label>
                        <s:select  id="e_status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Contact Number</label>
                        <s:textfield name="contactno" id="e_contactno" cssClass="form-control"  maxLength="11" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"   onkeypress="return isNumber(event)" />
                    </div>
                </div>

            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Email</label>
                        <s:textfield name="email" id="e_email" cssClass="form-control" maxLength="128"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Address</label>
                        <s:textfield name="address1" id="e_address1" cssClass="form-control" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,./' ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,./' ]/g, ''))" />
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >City</label>
                        <s:textfield name="city" maxLength="30" id="e_city" cssClass="form-control" onkeypress="return alpha(event)"/>
                    </div>
                </div>

            </div>
            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Employee ID</label>
                        <s:textfield name="serviceid" id="e_serviceid" maxLength="15" cssClass="form-control" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Expiry Date</label>
                        <s:textfield name="expirydate" id="e_expirydate" cssClass="form-control" readonly="true" cssStyle="color: #858585"/>
                    </div>
                </div>

                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Date of Birth</label>
                        <sj:datepicker value="%{dateofbirth}" cssClass="form-control" id="e_dateofbirth" name="dateofbirth" readonly="true"  maxDate="d" 
                                       changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="1950:2200" timepicker="false" 
                                       />
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
                            cssClass="btn btn-default btn-sm"
                            onClick="cancelData()"
                            />                        
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="updateSystemUser" var="updateturl"/>
                        <sj:submit
                            button="true"
                            value="Update"
                            href="%{updateturl}"
                            targets="divmsgupdate"
                            id="updatebtn"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9" 
                            />                        
                    </div>
                </div>
            </div>  
        </s:form>        

    </body>
</html>


