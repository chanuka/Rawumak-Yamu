<%-- 
    Document   : systemuserinsert
    Created on : Jul 14, 2016, 1:11:43 PM
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
        <title>Insert System User</title>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            $.subscribe('resetAddButton', function (event, data) {
                $("#iusername").val("");
                $("#iserviceid").val("");
                $("#iaddress1").val("");
                $("#ipassword").val("");
                $("#iconfirmpassword").val("");
                $("#idescription").val("");
                $("#inic").val("");
                $("#iuserrole").val("");
                $("#icity").val("");
                $("#idateofbirth").val("");
                $("#icontactno").val("");
                $("#iemail").val("");
                $("#ifullname").val("");
//                $("#iexpirydate").val("");

                $("#divmsginsert").empty();
                $("#istatus").val("");
            });

            function isNumberinsert(evt) {
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

            $(document).ready(function () {
                $.each($('.tooltip'), function (index, element) {
                    $(this).remove();
                });
                $('[data-toggle="tooltip"]').tooltip({
                    'placement': 'right'

                });
            });

        </script>
        <style>
            .tooltip {
               
                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }
            
        </style>

    </head>
    <body >

        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="systemuserinsert" method="post" action="SystemUser" theme="simple" cssClass="form">   

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Username</label>
                        <s:textfield name="username" id="iusername" maxLength="30" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Full Name</label>
                        <s:textfield name="fullname" id="ifullname" maxLength="30" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label>NIC</label>
                        <s:textfield  name="nic" id="inic" cssClass="form-control"  maxlength="12" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)" />
                    </div>
                </div>
            </div>

            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">                   
                        <span style="color: red">*</span><label >Password</label> 
                        <s:password name="password" id="ipassword" maxLength="64" data-toggle="tooltip" data-html="true" title="%{pwtooltip}"  cssClass="form-control" />
                        <!--<label style="font-size: 11px"></label>-->
                    </div>

                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Confirm Password</label>
                        <s:password name="confirmpassword" id="iconfirmpassword" maxLength="64"  cssClass="form-control"/>
                    </div>
                </div>
            </div> 

            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >User Role</label>
                        <s:select  id="iuserrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Status</label>
                        <s:select  id="istatus" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Contact Number</label>
                        <s:textfield  name="contactno" id="icontactno" cssClass="form-control"  maxLength="11" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="return isNumberinsert(event)"/>
                    </div>
                </div>
            </div>

            <div class="row row_popup"> 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Email</label>
                        <s:textfield name="email" id="iemail" cssClass="form-control" maxLength="128"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Address</label>
                        <s:textfield name="address1" id="iaddress1" maxLength="255" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,./' ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,./' ]/g, ''))" />
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >City</label>
                        <s:textfield name="city" id="icity" cssClass="form-control" maxLength="30" onkeypress="return alpha(event)"/>
                    </div>
                </div>
            </div>

            <div class="row row_popup">
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Employee ID</label>
                        <s:textfield name="serviceid" maxLength="20" id="iserviceid" cssClass="form-control" onkeypress="return alpha(event)"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Expiry Date</label>
                        <s:textfield name="expirydate" id="iexpirydate" cssClass="form-control" readonly="true"/>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Date of Birth</label>
                        <sj:datepicker value="%{dateofbirth}" cssClass="form-control" id="idateofbirth" name="dateofbirth" 
                                       readonly="true"  maxDate="d" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="1950:2200" timepicker="false" 
                                       timepickerFormat="hh:mm"/>
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
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <sj:submit 
                            button="true" 
                            value="Reset" 
                            name="reset" 
                            cssClass="btn btn-default btn-sm"
                            onClickTopics="resetAddButton"
                            />                        
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url action="addSystemUser" var="inserturl"/>
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


