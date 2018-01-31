
<%--

    Document   : loginhistory
    Created on : Feb 5, 2016, 11:39:25 AM
    Author     : jayana_i
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">

    <head>

        <%@include file="/stylelinks.jspf" %>

        <script type="text/javascript">

            function viewformatter(cellvalue) {
                return "<a href='viewDetailEventHistory.action?auditId=" + cellvalue + "' title='View Audit Record' ><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function searchAudit() {
                var walletid = $('#walletid').val();
                var pushid = $('#pushid').val();
                var status = $('#status').val();
                var sessionkey = $('#sessionkey').val();
                var loggedtime = $('#loggedtime').val();
                var xcoordinate = $('#xcoordinate').val();
                var ycoordinate = $('#ycoordinate').val();
                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var customerid = $('#customerid').val();
                var nic = $('#nic').val();
                var mobilenumber = $('#mobilenumber').val();
                var task = $('#task').val();
                var logintype = $('#logintype').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        walletid: walletid,
                        customerid: customerid,
                        nic: nic,
                        mobilenumber: mobilenumber,
                        pushid: pushid,
                        status: status,
                        logintype: logintype,
                        loggedtime: loggedtime,
                        sessionkey: sessionkey,
                        xcoordinate: xcoordinate,
                        ycoordinate: ycoordinate,
                        fromdate: fromdate,
                        todate: todate,
                        task: task,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

                var isGenerate = <s:property value="vgenerate"/>;

                if (isGenerate == false) {
                    $('#subview').button("enable");
                    $('#subview1').button("enable");
                }
                else {
                    $('#subview').button("disable");
                    $('#subview1').button("disable");

                }
            }

            function resetAllData() {

                $('#walletid').val("");
                $('#customerid').val("");
                $('#mobilenumber').val("");
                $('#pushid').val("");
                $('#xcoordinate').val("");
                $('#ycoordinate').val("");
                $('#status').val("");
                $('#sessionkey').val("");
                $('#nic').val("");
                $('#loggedtime').val("");
                $('#divmsg').text("");
                $("#username").attr('disabled', false);
                $("#mobile").attr('disabled', false);
                $("#task").val("");
                $("#logintype").val("");

                setdate();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        walletid: '',
                        customerid: '',
                        nic: '',
                        mobilenumber: '',
                        pushid: '',
                        status: '',
                        logintype: '',
                        loggedtime: '',
                        sessionkey: '',
                        xcoordinate: '',
                        ycoordinate: '',
                        fromdate: '',
                        todate: '',
                        task: '',
                        search: false
                    }
                });

                jQuery("#gridtable").trigger("reloadGrid");
            }



            $.subscribe('anyerrors', function (event, data) {
                //                   alert('status: ' + event.originalEvent.status + '\n\nrequest status: ' +event.originalEvent.request.status+ '\n\nerror: ' + event.originalEvent.error);
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });




            $.subscribe('completetopics', function (event, data) {

                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                var isGenerate = <s:property value="vgenerate"/>;

                if (recors > 0 && isGenerate == false) {
                    $('#subview').button("enable");
                    $('#subview1').button("enable");

                } else {
                    $('#subview').button("disable");
                    $('#subview1').button("disable");
                }
            });

            function setdate() {
                $("#fromdate").datepicker("setDate", new Date());
                $("#todate").datepicker("setDate", new Date());
            }

            $(document).ready(function () {
                $("#mobile").change(function () {
                    $("#username").attr('disabled', 'disabled');
                });
                $("#username").change(function () {
                    $("#mobile").attr('disabled', 'disabled');
                });
            });

            function todo() {
                //            window.open();
                $('#reporttype').val("pdf");
                form = document.getElementById('auditform');
                //                    form.target = '_blank';
                form.action = 'reportGenerateLoginHistory';
                form.submit();
                //            window.open(form);
                $('#subview').button("disable");
                $('#subview1').button("disable");
            }

            function todoexel() {
                //            window.open();
                $('#reporttype').val("exel");
                form = document.getElementById('auditform');
                //                    form.target = '_blank';
                form.action = 'reportGenerateLoginHistory';
                form.submit();
                //            window.open(form);
                $('#subview').button("disable");
                $('#subview1').button("disable");
            }

            function search(e) {
                var key = e.keyCode || e.which;
                if (key == 13) {
                    searchAudit();
                }
            }
        </script>
        <title></title>
    </head>

    <body onload="setdate()">
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
                            <s:set id="vgenerate" var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="auditform" method="post" theme="simple" cssClass="form">
                                    <s:hidden name="reporttype" id="reporttype"/>                                    
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>From Date</label>
                                                <sj:datepicker cssClass="form-control" id="fromdate" name="fromdate" readonly="true" changeYear="true" yearRange="2000:2200"
                                                               maxDate="d" buttonImageOnly="true" displayFormat="yy-mm-dd"  />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >To Date</label>
                                                <sj:datepicker  cssClass="form-control" id="todate" name="todate" readonly="true" changeYear="true" yearRange="2000:2200"
                                                                maxDate="+1d" buttonImageOnly="true" displayFormat="yy-mm-dd"  />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Wallet ID</label>
                                                <s:textfield cssClass="form-control" name="walletid" id="walletid" onkeypress="search(event)" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="10"/>
                                            </div>
                                        </div>                                        
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Mobile Number</label>
                                                <s:textfield cssClass="form-control" name="mobilenumber" id="mobilenumber" onkeypress="search(event)" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="10" />
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >NIC</label>
                                                <s:textfield cssClass="form-control" name="nic" id="nic" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9vxVX]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9vxVX]/g,''))" onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Status</label>
                                                <s:select cssClass="form-control" id="status" list="%{StatusList}" name="status"
                                                          headerKey="" headerValue="--Select Status--"
                                                          listKey="statuscode" listValue="description"/>
                                            </div>
                                        </div>
                                        <div class="row row_1">
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <label >Task</label>
                                                    <s:select cssClass="form-control" id="task" name="task" list="%{taskList}" 
                                                              headerValue="--Select Task--" headerKey=""  listKey="key" listValue="value"/>  
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <label >Type</label>
                                                    <s:select cssClass="form-control" id="logintype" name="logintype" list="%{logintypeList}" 
                                                              headerKey="" headerValue="--Select Type--" listKey="id" listValue="description"/> 
                                                </div>
                                            </div>    
                                        </div>
                                    </div>
                                </s:form>
                                <div class="row row_1"></div>
                                <div class="row row_1 form-inline">
                                    <div class="col-sm-8">
                                        <div class="form-group">
                                            <sj:submit  
                                                value="Search"
                                                button="true" 
                                                id="searchButton"
                                                onclick="searchAudit(true)"
                                                disabled="#vsearch" 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>
                                        <div class="form-group">
                                            <sj:submit 
                                                button="true" 
                                                value="Reset" 
                                                name="reset" 
                                                onClick="resetAllData()" 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;"                                              
                                                /> 
                                        </div>
                                        <div class="form-group">
                                            <sj:submit 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                button="true" 
                                                value="View PDF" 
                                                name="subview" 
                                                id="subview" 
                                                onClick="todo()" 
                                                disabled="#vgenerate"/> 
                                        </div>

                                        <div class="form-group">
                                            <sj:submit 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                button="true" 
                                                value="View Excel" 
                                                name="subview1" 
                                                id="subview1" 
                                                onClick="todoexel()" 
                                                disabled="#vgenerate"/> 
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <!--start newly changed-->
                            <sj:dialog 
                                id="adddialog" 
                                buttons="{ 
                                'OK':function() { AddSection($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Add Section"                            
                                />
                            <!-- Start add process dialog box -->
                            <sj:dialog 
                                id="addsuccdialog" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}
                                }"  
                                autoOpen="false" 
                                modal="true" 
                                title="Adding Process." 
                                />
                            <!--end newly changed-->

                            <!-- Start delete confirm dialog box -->
                            <sj:dialog 
                                id="deletedialog" 
                                buttons="{ 
                                'OK':function() { deleteTask($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Delete Task"                            
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
                        <div id="tablediv">
                            <s:url var="listurl" action="listLoginHistory"/>
                            <s:set var="pcaption">${CURRENTPAGE}</s:set>

                            <sjg:grid
                                id="gridtable"
                                caption="%{pcaption}"
                                dataType="json"
                                href="%{listurl}"
                                pager="true"
                                gridModel="gridModel"
                                rowList="10,15,20"
                                rowNum="10"                                    
                                autowidth="true"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"                                    
                                viewrecords="true"
                                onErrorTopics="anyerrors"
                                >
                                <sjg:gridColumn name="logedtime" index="u.loggedtime" title="Logged Time"  sortable="true" frozen="true"/>
                                <sjg:gridColumn name="historyid" index="historyid" title="History ID"  sortable="true" frozen="true" />
                                <sjg:gridColumn name="walletid" index="walletid" title="Wallet ID"  sortable="true" frozen="true"/>
                                <sjg:gridColumn name="status" index="status" title="Status" sortable="true" /> 
                                <%--<sjg:gridColumn name="customerid" index="customerid" title="Customer ID"  sortable="true"/>--%>
                                <sjg:gridColumn name="mobilenumber" index="mobilenumber" title="Mobile Number" sortable="true" /> 
                                <sjg:gridColumn name="nic" index="nic" title="NIC"  sortable="true"/>
                                <sjg:gridColumn name="task" index="task" title="Task"  sortable="true"/>
                                <sjg:gridColumn name="logintype" index="logintype" title="Type"  sortable="true"/>
                                <%--<sjg:gridColumn name="pushid" index="pushid" title="Push ID"  sortable="true"/>--%>
                                <%--<sjg:gridColumn name="sessionkey" index="sessionkey" title="Session Key"  sortable="true"/>--%>
                                <%--<sjg:gridColumn name="xcoordinate" index="XCoordinate" title="Latitude"  sortable="true"/>--%>
                                <%--<sjg:gridColumn name="ycoordinate" index="YCoordinate" title="Longitude"  sortable="true"/>--%>

                            </sjg:grid> 
                        </div>
                    </div>
                </div>
                <!-- end: PAGE CONTENT-->
            </div>
        </div>
        <!-- end: PAGE -->
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->
        <!-- end: BODY -->
    </body>
</html>