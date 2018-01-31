<%-- 
    Document   : atmlocationscsv
    Created on : Sep 26, 2016, 7:44:06 PM
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
        <title>Insert Merchant</title>

        <script>

            function resetAllDataCSV() {

                $('#conXL').val("");
                $('#csvmessage').text("");
            }

            function resetCSV() {

                $('#conXL').val("");
                refreshATMlocations();
            }
            function resetMessage() {
                
                $('#csvmessage').text("");
            }
            function todo() {
                form = document.getElementById('merchantcsv');
                form.action = 'templateATMLocations';
                form.submit();
            }

        </script>
    </head>
    <body>
        <s:div id="csvmessage">
            <s:actionerror theme="jquery" />
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:set id="vupload" var="vupload"><s:property value="vupload" default="true"/></s:set>
        <s:form id="merchantcsv" method="post"   theme="simple" cssClass="form" >


            <div class="row row_popup">
                <div class="row form-inline">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <input type="hidden" name="hiddenId" id="hiddenId" value="" />
                            <%--<s:hidden id="conXL" name="conXL" ></s:hidden>--%>
                            <span style="color: red">*</span><label >Upload CSV files</label>
                            <input type="file" id="conXL" name="conXL" />
                            <%--<s:file id="conXL" name="conXL" cssClass="form-control" />--%>
                        </div>
                    </div>
                       
                </div>
                <div class="row">
                    <div class="horizontal_line_popup"></div>
                </div>

                <div class="row row_popup form-inline">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <span class="mandatoryfield">Mandatory fields are marked with *</span>
                        </div>
                    </div>
                    <div class="col-sm-6 text-right">
                        <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                            <sj:submit 
                                button="true" 
                                value="Template" 
                                onClick="todo()"
                                cssClass="btn btn-default btn-sm"
                                />                          
                        </div>
                        <div class="form-group" style=" margin-left: 0px;margin-right: 0px;">
                            <sj:submit 
                                button="true" 
                                value="Reset" 
                                onClick="resetAllDataCSV()"
                                cssClass="btn btn-default btn-sm"
                                />                          
                        </div>               
                        <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                            <s:url action="uploadATMLocations" var="uploadurl"/>
                            <sj:submit 
                                button="true"
                                    href="%{uploadurl}"
                                    value="Upload"
                                    disabled="#vupload"
                                    onClickTopics=""
                                    targets="csvmessage"
                                    id="uploadbtncsv"
                                    cssClass="btn btn-sm active" 
                                    cssStyle="background-color: #ada9a9"
                                />
                        </div>

                    </div>
                </div>        


            </div>

        </s:form>
    </body>
</html>