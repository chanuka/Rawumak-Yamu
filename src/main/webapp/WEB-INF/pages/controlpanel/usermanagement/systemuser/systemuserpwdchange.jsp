<%-- 
    Document   : systemuserpwdchange
    Created on : Jul 15, 2016, 11:48:32 AM
    Author     : jayana_i
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
        <title>Update System User</title>
        <script type="text/javascript">
            $.subscribe('resetChangePwdButton', function (event, data) {
                $("#cusername").val("");
                $("#chusername").val("");
                $("#cuserrole").val("");
                $("#cnewpwd").val("");
                $("#crenewpwd").val("");

            });

            function resetAllData() {
                $('#cnewpwd').val("");
                $('#crenewpwd').val("");
                $('#changepassdivmsg').empty();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cnewpwd: '',
                        crenewpwd: '',
                        SearchAudit: '',
                        changepassdivmsg: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
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
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }

        </style>
    </head>

    <body >
        <s:div id="changepassdivmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form action="PasswordReset" theme="simple" method="post" id="pwdResetform" cssClass="form">
            <div class="row row_popup">          
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >Username</label>
                        <s:textfield value="%{username}" name="cusername" maxLength="30" id="cusername" cssClass="form-control" disabled="true"/>
                        <s:hidden name="husername" id="chusername" />
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label >User Role</label>
                        <s:textfield value="%{userrole}" name="cuserrole" id="cuserrole" cssClass="form-control" disabled="true"/>
                    </div>
                </div>
            </div>
            <div class="row row_popup">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row row_popup">                 
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >New Password</label>
                        <s:password value="%{newpwd}" name="cnewpwd" id="cnewpwd" data-toggle="tooltip" data-html="true" title="%{pwtooltip}" cssClass="form-control" maxLength="64" />

                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <span style="color: red">*</span><label >Retype New Password</label>
                        <s:password value="%{renewpwd}" name="crenewpwd" id="crenewpwd" cssClass="form-control" maxLength="64"/>

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
                            onClick="resetAllData()"
                            cssClass="btn btn-default btn-sm"
                            />                           
                    </div>
                    <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                        <s:url var="pwreseturl" action="updatechangepasswordSystemUser"/>   
                        <s:hidden id="newpwd" name="newpwd"></s:hidden>
                        <s:hidden id="renewpwd" name="renewpwd"></s:hidden>
                        <sj:submit 
                            button="true"
                            href="%{pwreseturl}"
                            value="Accept"
                            targets="changepassdivmsg"
                            cssClass="btn btn-sm active" 
                            cssStyle="background-color: #ada9a9" 
                            />                         
                    </div>
                </div>
            </div>
        </s:form> 
    </body>
</html>
