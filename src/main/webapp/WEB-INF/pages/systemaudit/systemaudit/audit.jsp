<%-- 
    Document   : audit
    Created on : Jan 7, 2014, 1:17:40 PM
    Author     : chalitha
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
                return "<a href='#' title='View' onClick='javascript:viewAuditInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";

            }

            function viewAuditInit(keyval) {
                $("#viewdialog").data('auditId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailSystemAudit.action?auditId=" + $led.data('auditId'));
            });

            function searchAudit(param) {
                var user = $('#user').val();
                var section = $('#section').val();
                var sdblpage = $('#sdblpage').val();
                var task = $('#task').val();
                var fdate = $('#fdate').val();
                var tdate = $('#tdate').val();
                var description = $('#description').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        user: user,
                        section: section,
                        sdblpage: sdblpage,
                        task: task,
                        description: description,
                        fdate: fdate,
                        tdate: tdate,
                        search: param
                    }});

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
            $.subscribe('completetopics', function (event, data) {
                var isGenerate = <s:property value="vgenerate"/>;
                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                if (recors > 0 && isGenerate == false) {
                    $('#subview').button("enable");
                    $('#subview1').button("enable");
                }
                else {
                    $('#subview').button("disable");
                    $('#subview1').button("disable");

                }
//                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
//                if (recors > 0) {
//                    $('#subview').button("enable");
//                    $('#subview1').button("enable");
//                } else {
//                    $('#subview').button("disable");
//                    $('#subview1').button("disable");
//                }
            });



            function setdate() {
                $("#fdate").datepicker("setDate", new Date());
                $("#tdate").datepicker("setDate", new Date());
            }

            function resetAllData() {
                $('#user').val("");
                $('#section').val("");
                $('#sdblpage').val("");
                $('#task').val("");
                $('#description').val("");
                $('#fdate').val("");
                $('#tdate').val("");
                $('#divmsg').text("");
                $('#subview').button("disable");
                $('#subview1').button("disable");
                setdate();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        user: '',
                        section: '',
                        sdblpage: '',
                        task: '',
                        description: '',
                        fdate: '',
                        tdate: '',
                        search: false
                    }});

                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function todoexel() {
                $('#reporttype').val("exel");
                form = document.getElementById('auditform');
                form.action = 'reportGenerateSystemAudit';
                form.submit();
                $('#subview1').button("disable");
                $('#subview').button("disable");
            }

            function subtodo() {
                $('#reporttype').val("pdf");
                form = document.getElementById('auditform');
                form.action = 'reportGenerateSystemAudit';
                form.submit();
                $('#subview1').button("disable");
                $('#subview').button("disable");
            }

            function search(e) {
                var key = e.keyCode || e.which;
                if (key == 13) {
                    searchAudit(true);
                }
            }

        </script>

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

                            <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                            <s:set id="vgenerate" var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                            <s:set id="vviewlink" var="vviewlink"><s:property value="vviewlink" default="true"/></s:set>
                            <s:set id="vview" var="vview"><s:property value="vview" default="true"/></s:set>    

                                <div id="formstyle">
                                <s:form id="auditform" method="post" action="SystemAudit" theme="simple" cssClass="form">

                                    <s:hidden name="reporttype" id="reporttype"></s:hidden>
                                        <div class="row row_1">
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <label >From Date</label>
                                                <sj:datepicker cssClass="form-control" id="fdate" name="fdate" readonly="true" maxDate="d" changeYear="true"
                                                               buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="2000:2200" />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >To Date</label>
                                                <sj:datepicker cssClass="form-control" id="tdate" name="tdate" readonly="true" maxDate="+1d" changeYear="true"
                                                               buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="2000:2200"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Username</label>
                                                <s:select cssClass="form-control" id="user" list="%{userList}" name="user"
                                                          headerKey="" headerValue="--Select Username--"
                                                          listKey="username" listValue="username"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Section</label>
                                                <s:select cssClass="form-control" id="section" list="%{sectionList}" name="section"
                                                          headerKey="" headerValue="--Select Section--"
                                                          listKey="sectioncode" listValue="description"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Page</label>
                                                <s:select cssClass="form-control" id="sdblpage" list="%{pageList}" name="sdblpage"
                                                          headerKey="" headerValue="--Select Page--"
                                                          listKey="pagecode" listValue="description"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Task</label>
                                                <s:select cssClass="form-control" id="task" list="%{taskList}" name="task"
                                                          headerKey="" headerValue="--Select Task--"
                                                          listKey="taskcode" listValue="description" />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Description</label>
                                                <s:textfield  name="description" id="description" 
                                                              maxLength="64" cssClass="form-control"
                                                              onkeypress="search(event)"
                                                              onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                              onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>

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
                                                disabled="#vgenerate"
                                                id="subview" 
                                                onClick="subtodo()" 
                                                /> 
                                        </div>

                                        <div class="form-group">
                                            <sj:submit 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                button="true" 
                                                value="View Excel" 
                                                name="subview1" 
                                                disabled="#vgenerate"
                                                id="subview1" 
                                                onClick="todoexel()" /> 
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <!-- Start update dialog box -->
                            <sj:dialog                                     
                                id="viewdialog"                                 
                                autoOpen="false" 
                                modal="true" 
                                position="center"
                                title="View System Audit"
                                onOpenTopics="openviewtasktopage" 
                                loadingText="Loading .."
                                width="900"
                                height="590"
                                dialogClass= "dialogclass"
                                />


                        </div>

                        <div id="tablediv">

                            <s:url var="listurl" action="listSystemAudit"/>
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
                                onErrorTopics="anyerrors">                                    

                                <sjg:gridColumn name="id" title="View" width="50" align="center"  formatter="viewformatter" hidden="#vviewlink" frozen="true"/>
                                <sjg:gridColumn name="id" index="systemauditid" title="ID" width="50" sortable="true" frozen="true"/>
                                <sjg:gridColumn name="description" index="description" width="250" title="Description" sortable="true"/>
                                <sjg:gridColumn name="sectionDes" index="sectioncode" title="Section" sortable="true"/>
                                <sjg:gridColumn name="pageDes" index="pagecode" title="Page" sortable="true"/>
                                <sjg:gridColumn name="taskDes" index="taskcode" width="50" title="Task" sortable="true"/>
                                <sjg:gridColumn name="user" index="lastupdateduser" width="50" title="Username" sortable="true"/>
                                <sjg:gridColumn name="lastUpdatedDate" index="lastupdatedtime" title="Last Updated Time" sortable="true"/>
                                <sjg:gridColumn name="createtime" index="createtime" title="Created Time"  sortable="true" />


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