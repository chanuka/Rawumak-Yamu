<%--
  User: ruwan_e
  Date: 1/16/14
  Time: 3:16 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">

            window.onload = function() {
                $("#passwordpolicyid").css("color", "#858585");
            };

            function editPasswordPolicy(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findPasswordPolicy',
                    data: {passwordpolicyid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        }
                        else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#passwordpolicyid').attr('readOnly', true);
                            $("#passwordpolicyid").css("color", "#858585");
                            $('#passwordpolicyid').val(data.passwordpolicyid);
                            $('#minimumlength').val(data.minimumlength);
                            $('#maximumlength').val(data.maximumlength);
                            $('#minimumspecialcharacters').val(data.minimumspecialcharacters);
                            $('#minimumuppercasecharacters').val(data.minimumuppercasecharacters);
                            $('#minimumnumericalcharacters').val(data.minimumnumericalcharacters);
                            $('#minimumlowercasecharacters').val(data.minimumlowercasecharacters);
                            $('#repeatcharactersallow').val(data.repeatcharactersallow);
//                            $('#initialpasswordexpirystatus').val(data.initialpasswordexpirystatus);
                            $('#passwordexpiryperiod').val(data.passwordexpiryperiod);
                            $('#noofhistorypassword').val(data.noofhistorypassword);
                            $('#minimumpasswordchangeperiod').val(data.minimumpasswordchangeperiod);
                            $('#idleaccountexpiryperiod').val(data.idleaccountexpiryperiod);
                            $('#noofinvalidloginattempt').val(data.noofinvalidloginattempt);
//                            $('#addButton').button("disable");
//                            $('#updateButton').button("enable");
                        }
                    },
                    error: function(data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
//                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                    }
                });
            }

            function resetAllData() {

                var s = $("#resetButton").is(':disabled')
                if (s == true) {
                    $('#passwordpolicyid').attr('readOnly', false);
                    $('#passwordpolicyid').val("");
                    $("#passwordpolicyid").css("color", "black");
                    $('#minimumlength').val("");
                    $('#maximumlength').val("");
                    $('#minimumspecialcharacters').val("");
                    $('#minimumuppercasecharacters').val("");
                    $('#minimumnumericalcharacters').val("");
                    $('#minimumlowercasecharacters').val("");
                    $('#repeatcharactersallow').val("");
//                    $('#initialpasswordexpirystatus').val("");
                    $('#passwordexpiryperiod').val("");
                    $('#noofhistorypassword').val("");
                    $('#minimumpasswordchangeperiod').val("");
                    $('#idleaccountexpiryperiod').val("");
                    $('#noofinvalidloginattempt').val("");
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
                    $('#divmsg').text("");
                } else {
//                    print("ssssELSEEEEE");
                    var passwordpolicyid = $('#passwordpolicyid').val();
                    $('#passwordpolicyid').attr('readOnly', true);
                    editPasswordPolicy(passwordpolicyid);
                }

            }

            function resetFieldData() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/resetPasswordPolicy.action',
                    data: {},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        var msg = data.message;
                        if (msg) {
                            $('#passwordpolicyid').attr('readOnly', false);
                            $('#passwordpolicyid').val("");
                            $("#passwordpolicyid").css("color", "black");
                            $('#minimumlength').val("");
                            $('#maximumlength').val("");
                            $('#minimumspecialcharacters').val("");
                            $('#minimumuppercasecharacters').val("");
                            $('#minimumnumericalcharacters').val("");
                            $('#minimumlowercasecharacters').val("");
                            $('#repeatcharactersallow').val("");
//                            $('#initialpasswordexpirystatus').val("");
                            $('#passwordexpiryperiod').val("");
                            $('#noofhistorypassword').val("");
                            $('#minimumpasswordchangeperiod').val("");
                            $('#idleaccountexpiryperiod').val("");
                            $('#noofinvalidloginattempt').val("");
//                            $('#addButton').button("enable");
                            $('#updateButton').button("disable");
                        }
                        else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#passwordpolicyid').attr('readOnly', true);
                            $('#passwordpolicyid').val(data.passwordpolicyid);
                            $("#passwordpolicyid").css("color", "#858585");
                            $('#minimumlength').val(data.minimumlength);
                            $('#maximumlength').val(data.maximumlength);
                            $('#minimumspecialcharacters').val(data.minimumspecialcharacters);
                            $('#minimumuppercasecharacters').val(data.minimumuppercasecharacters);
                            $('#minimumnumericalcharacters').val(data.minimumnumericalcharacters);
                            $('#minimumlowercasecharacters').val(data.minimumlowercasecharacters);
                            $('#repeatcharactersallow').val(data.repeatcharactersallow);
//                            $('#initialpasswordexpirystatus').val(data.initialpasswordexpirystatus);
                            $('#passwordexpiryperiod').val(data.passwordexpiryperiod);
                            $('#noofhistorypassword').val(data.noofhistorypassword);
                            $('#minimumpasswordchangeperiod').val(data.minimumpasswordchangeperiod);
                            $('#idleaccountexpiryperiod').val(data.idleaccountexpiryperiod);
                            $('#noofinvalidloginattempt').val(data.noofinvalidloginattempt);
//                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function(data) {
//                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });

            }

        </script>
        <title></title>
    </head>
    <body style="">
        <jsp:include page="/header.jsp"/>

        <div class="main-container">


            <jsp:include page="/leftmenu.jsp"/>

            <div class="main-content">

                <div class="container" style="min-height: 760px;">


                    <!-- start: PAGE NAVIGATION BAR -->
                    <jsp:include page="/navbar.jsp"/>
                    <!-- end: NAVIGATION BAR -->

                    <div class="row">


                        <div id="content1">


                            <s:div id="divmsg">

                                <s:actionerror theme="jquery"/>
                                <s:actionmessage theme="jquery"/>
                            </s:div>

                            <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                            <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                            <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                            <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>                            
                            <s:set var="policyid"><s:property value="policyid" default="true"/></s:set>

                            <div id="formstyle">

                                <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                                <s:url var="addurl" action="addPasswordPolicy"/>
                                <s:url var="updateurl" action="updatePasswordPolicy"/>

                                <s:form id="customercategoryadd" method="post" action="PasswordPolicy" theme="simple">

                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Password Policy ID</label>
                                                <s:textfield cssClass="form-control" name="passwordpolicyid" id="passwordpolicyid"                                                        
                                                             maxLength="5" 
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                             readonly="#policyid"/>                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Minimum Length</label>
                                                <s:textfield cssClass="form-control" name="minimumlength" id="minimumlength"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Maximum Length</label>
                                                <s:textfield cssClass="form-control" name="maximumlength" id="maximumlength"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                        </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Minimum Special Characters</label>
                                                <s:textfield cssClass="form-control" name="minimumspecialcharacters" id="minimumspecialcharacters"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                                
                                            </div>
                                        </div>       

                                    </div>

                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Minimum Upper Case Characters</label>
                                                <s:textfield cssClass="form-control" name="minimumuppercasecharacters" id="minimumuppercasecharacters"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                          </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Minimum Lower Case Characters</label>
                                                <s:textfield cssClass="form-control" name="minimumlowercasecharacters" id="minimumlowercasecharacters"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Minimum Numerical Characters</label>
                                                <s:textfield cssClass="form-control" name="minimumnumericalcharacters" id="minimumnumericalcharacters"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                       </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Allowed Repeat Characters</label>
                                                <s:textfield cssClass="form-control" name="repeatcharactersallow" id="repeatcharactersallow"
                                                             maxLength="2"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                              
                                            </div>
                                        </div>       

                                    </div>


                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Password Expiry Period</label>
                                                <s:textfield cssClass="form-control" name="passwordexpiryperiod" id="passwordexpiryperiod"
                                                             maxLength="5"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             /> (days)                                         
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Password Expiry Notification Period</label>
                                                <s:textfield cssClass="form-control" name="minimumpasswordchangeperiod" id="minimumpasswordchangeperiod"
                                                             maxLength="5"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />  (days)                                          </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>No. of History Passwords</label>
                                                <s:textfield cssClass="form-control" name="noofhistorypassword" id="noofhistorypassword"
                                                             maxLength="5"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                       </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Idle Account Expiry Period</label>
                                                <s:textfield cssClass="form-control" name="idleaccountexpiryperiod" id="idleaccountexpiryperiod"
                                                             maxLength="5"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             /> (days)                                            
                                            </div>
                                        </div>       

                                    </div>

                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>No. of Invalid Login Attempts</label>
                                                <s:textfield cssClass="form-control" name="noofinvalidloginattempt" id="noofinvalidloginattempt"
                                                             maxLength="5"
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                             />                                         </div>
                                        </div>



                                    </div>

                                    <div class="row row_1">
                                        <div class="col-sm-4">
                                            <div class="form-group form-inline">
                                                <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row row_1"></div>
                                    <div class="row row_1 form-inline">
                                        <!--<div class="col-sm-2"></div>-->
                                        <div class="col-sm-8">
                                            <div class="form-group">
                                                <sj:submit button="true" 
                                                           value="Reset" 
                                                           name="reset" 
                                                           onClick="resetAllData()"
                                                           cssClass="form-control btn_normal"
                                                           cssStyle="border-radius: 12px;" />

                                            </div>
                                            <div class="form-group">
                                                <sj:submit button="true" 
                                                           href="%{updateurl}" 
                                                           value="Update" 
                                                           disabled="#vupdatebutt"
                                                           targets="divmsg"   
                                                           id="updateButton"
                                                           cssClass="form-control btn_normal"
                                                           cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                           />

                                            </div>



                                        </div>

                                    </div>


                                </s:form>
                            </div>
                            <!-- Start delete confirm dialog box -->
                            <sj:dialog 
                                id="deletedialog" 
                                buttons="{ 
                                'OK':function() { deletePage($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Delete Page"                            
                                />
                            <!-- Start delete process dialog box -->
                            <sj:dialog 
                                id="deletesuccdialog" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}
                                }"  
                                autoOpen="false" 
                                modal="true" 
                                title="Deleting Process." 
                                />
                            <!-- Start delete error dialog box -->
                            <sj:dialog 
                                id="deleteerrordialog" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}                                    
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Delete error."
                                />

                        </div>
                    </div>
                </div>
            </div>
        </div>                            
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->



    </body>
</html>
