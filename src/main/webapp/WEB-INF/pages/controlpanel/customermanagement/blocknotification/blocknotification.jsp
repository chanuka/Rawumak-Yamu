<%-- 
    Document   : blocknotification
    Created on : Jun 26, 2017, 11:13:38 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="/stylelinks.jspf" %>
        <title>Block Notification</title>
        <script type="text/javascript">
            $.subscribe('onclicksearch', function (event, data) {
                $('#message').empty();

                var walletIdSearch = $('#walletIdSearch').val();
                var statusSearch = $('#statusSearch').val();
                var fromdateSearch = $('#fromdateSearch').val();
                var todateSearch = $('#todateSearch').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        walletIdSearch: walletIdSearch,
                        statusSearch: statusSearch,
                        fromdateSearch: fromdateSearch,
                        todateSearch: todateSearch,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });
            
              function onclicksearch() {
                $('#message').empty();

                var walletIdSearch = $('#walletIdSearch').val();
                var statusSearch = $('#statusSearch').val();
                var fromdateSearch = $('#fromdateSearch').val();
                var todateSearch = $('#todateSearch').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        walletIdSearch: walletIdSearch,
                        statusSearch: statusSearch,
                        fromdateSearch: fromdateSearch,
                        todateSearch: todateSearch,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editBNInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteBNInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editBNInit(keyval) {
                $("#updatedialog").data('walletId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktobn', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("DetailBlockNotification.action?walletId=" + $led.data('walletId'));
            });

            function deleteBNInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Block Notification : ' + keyval + ' ?');
                return false;
            }

            function deleteBN(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteBlockNotification.action',
                    data: {walletId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetAllData() {

                $('#walletIdSearch').val("");
                $('#statusSearch').val("");
                $('#fromdateSearch').val("");
                $('#todateSearch').val("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        walletIdSearch: '',
                        statusSearch: '',
                        fromdateSearch: '',
                        todateSearch: '',
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetFieldData() {

                $('#walletId').val("");
                $('#status').val("");
                $('#fromdate').val("");
                $('#todate').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }
        </script>
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
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="bnsearch" method="post" action="BlockNotification" theme="simple" cssClass="form" >

                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Wallet ID </label>
                                                <s:textfield cssClass="form-control" name="walletIdSearch" id="walletIdSearch" maxLength="10" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                            </div>
                                        </div>
                                        <div class="col-sm-3" style="display: none">
                                            <div class="form-group">
                                                <label >Status</label>
                                                <s:textfield cssClass="form-control" name="defaultStatus" id="oa" value="Block" disabled="true"/>
                                                <%--<s:select cssClass="form-control" name="statusSearch" id="statusSearch" headerValue="-- Select Status --" list="%{statusList}"   headerKey="" listKey="statuscode" listValue="description" value="%{statusSearch}"/>--%>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>From Date </label>
                                                <%--<s:textfield  cssClass="form-control" name="sortKeySearch" id="sortKeySearch" maxLength="2" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>--%>
                                                <sj:datepicker  cssClass="form-control"
                                                                id="fromdateSearch" name="fromdateSearch" readonly="true" 
                                                                changeYear="true"  buttonImageOnly="true" 
                                                                displayFormat="yy-mm-dd" 
                                                                value="%{fromdateSearch}"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>To Date</label>
                                                <%--<s:textfield  cssClass="form-control" name="urlSearch" id="urlSearch" maxLength="128" />--%>
                                                <sj:datepicker  cssClass="form-control"
                                                                id="todateSearch" name="todateSearch" readonly="true" 
                                                                changeYear="true"  buttonImageOnly="true" 
                                                                displayFormat="yy-mm-dd" 
                                                                value="%{todateSearch}"/>
                                            </div>
                                        </div>
                                    </div>
                                </s:form>
                                <div class="row row_1 form-inline">
                                    <div class="col-sm-4">
                                        <div class="form-group">
                                            <sj:submit 
                                                button="true"
                                                value="Search" 
                                                disabled="#vsearch"
                                                onclick="onclicksearch()"
                                                id="searchbut"
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
                                    </div>

                                    <div class="col-sm-5"></div>
                                    <div class="col-sm-3 text-right">
                                        <div class="form-group">                                               
                                            <s:url var="addurl" action="ViewPopupBlockNotification"/>                                                    
                                            <sj:submit 
                                                openDialog="remotedialog"
                                                button="true"
                                                href="%{addurl}"
                                                disabled="#vadd"
                                                value="Add New Block Notification"
                                                id="addButton"  
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>
                                    </div>
                                </div>


                                <!-- Start add dialog box -->
                                <sj:dialog                                     
                                    id="remotedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Add Block Notification"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="900"
                                    height="450"
                                    dialogClass= "dialogclass"
                                    />  
                                <!-- Start update dialog box -->
                                <sj:dialog                                     
                                    id="updatedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    position="center"
                                    title="Update Block Notification"
                                    onOpenTopics="openviewtasktobn" 
                                    loadingText="Loading .."
                                    width="900"
                                    height="450"
                                    dialogClass= "dialogclass"
                                    />
                                <!-- Start delete confirm dialog box -->
                                <sj:dialog 
                                    id="deletedialog" 
                                    buttons="{ 
                                    'OK':function() { deleteBN($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                    'Cancel':function() { $( this ).dialog( 'close' );} 
                                    }" 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Delete Block Notification"                            
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
                                <s:url var="listurl" action="listBlockNotification"/>
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
                                    <sjg:gridColumn name="walletId" index="u.walletId" title="Edit" width="25" align="center" formatter="editformatter" sortable="false" hidden="#vupdatelink" />
                                    <sjg:gridColumn name="walletId" index="u.walletId" title="Delete" width="40" align="center" formatter="deleteformatter" sortable="false" hidden="#vdelete" />  
                                    <sjg:gridColumn name="walletId" index="u.walletId" title="Wallet ID"  sortable="false" frozen="true"/>
                                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="false"/>
                                    <sjg:gridColumn name="fromdate" index="u.fromdate" title="From Date"  sortable="false"/>
                                    <sjg:gridColumn name="todate" index="u.todate" title="To Date"  sortable="false"/>
                                    <sjg:gridColumn name="createtime" index="u.createtime" title="Created Time"  sortable="false"/>
                                    <%--<sjg:gridColumn name="lastupdatedtime" index="u.lastupdatedtime" title="Last Updated Time"  sortable="true" />--%>
                                    <%--<sjg:gridColumn name="lastupdateduser" index="u.lastupdateduser" title="Last Updated User"  sortable="true" />--%>

                                </sjg:grid> 
                            </div>
                        </div>
                    </div>
                    <!-- end: PAGE CONTENT-->
                </div>
            </div>
            <!-- end: PAGE -->
        </div>
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->



        <!-- end: BODY -->
    </body>
</html>