<%-- 
    Document   : transactiontype
    Created on : Feb 3, 2016, 4:10:07 PM
    Author     : chathuri_t
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editTransactionTypeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteTransactionTypeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editTransactionTypeInit(keyval) {
                $("#updatedialog").data('transactiontypecode', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {

                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailTransactionType.action?transactiontypecode=" + $led.data('transactiontypecode'));
            });


            function deleteTransactionTypeInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete transaction type ' + keyval + ' ?');
                return false;
            }

            function deleteTransactionType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteTransactionType.action',
                    data: {transactiontypecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetSearchData() {
                $('#transactiontypecodesearch').val("");
                $('#descriptionsearch').val("");
                $('#statussearch').val("");
                $('#OTPReqStatussearch').val("");
                $('#RiskReqStatussearch').val("");
                $('#merchantTxnTypesearch').val("");
                $('#description_receiversearch').val("");
                $('#history_visibilitysearch').val("");
                $('#sortKeysearch').val("");


                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_transactiontypecode: '',
                        s_description: '',
                        s_status: '',
                        s_OTPReqStatus: '',
                        s_history_visibility: '',
                        s_description_receiver: '',
                        s_RiskReqStatus: '',
                        s_merchantTxnType: '',
                        s_sortOrder: '',
                        search: false
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function searchTransactionType() {
                var transactiontypecode = $('#transactiontypecodesearch').val();
                var description = $('#descriptionsearch').val();
                var status = $('#statussearch').val();
                var otpreqstatus = $('#OTPReqStatussearch').val();
                var riskreqstatus = $('#RiskReqStatussearch').val();
                var merchantTxnType = $('#merchantTxnTypesearch').val();
                var description_receiver = $('#description_receiversearch').val();
                var history_visibility = $('#history_visibilitysearch').val();
                var sortKeysearch = $('#sortKeysearch').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_transactiontypecode: transactiontypecode,
                        s_description: description,
                        s_status: status,
                        s_OTPReqStatus: otpreqstatus,
                        s_history_visibility: history_visibility,
                        s_description_receiver: description_receiver,
                        s_RiskReqStatus: riskreqstatus,
                        s_merchantTxnType: merchantTxnType,
                        s_sortOrder: sortKeysearch,
                        search: true
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");
            }
            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function resetFieldData() {
                $('#transactiontypecode').val("");
                $('#description').val("");
                $('#OTPReqStatus').val("");
                $('#description_receiver').val("");
                $('#history_visibility').val("");
                $('#RiskReqStatus').val("");
                $('#merchantTxnType').val("");
                $('#merchantTxnType').val("");
                $('#sortOrder').val("");
                $('#status').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
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
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="transactiontypsearch" method="post" action="TransactionType" theme="simple" cssClass="form" >


                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Transaction Type Code </label>
                                                <s:textfield name="transactiontypecodesearch" id="transactiontypecodesearch" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Description </label>
                                                <s:textfield  name="descriptionsearch" id="descriptionsearch" maxLength="64" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                            </div>                                      
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Status</label>
                                                <s:select  id="statussearch" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="statussearch" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Sort Key </label>
                                                <s:textfield  name="sortKeysearch" id="sortKeysearch" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Description Receiver</label>
                                                <s:textfield  name="description_receiversearch" id="description_receiversearch" maxLength="64" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                                                onClick="searchTransactionType()"  
                                                disabled="#vsearch"
                                                id="searchbut"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>
                                        <div class="form-group">                               
                                            <sj:submit 
                                                button="true" 
                                                id="cancelsearch"
                                                value="Reset" 
                                                onClick="resetSearchData()"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;"
                                                /> 
                                        </div>
                                    </div>
                                    <div class="col-sm-5"></div>
                                    <%--     <div class="col-sm-3 text-right">
                                            <div class="form-group">
                                                <s:url var="addurl" action="ViewPopupTransactionType"/>   
                                                                   <sj:submit                                                      
                                                                        openDialog="remotedialog"
                                                                        button="true"
                                                                        href="%{addurl}"
                                                                        disabled="#vadd"
                                                                        value="Add New Transaction Type"
                                                                        id="addButton"
                                                                        targets="remotedialog"   
                                                                        cssClass="form-control btn_normal"
                                                                        cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                                        /> 
                                            </div> --%>
                                </div> 
                            </div> 
                            <%--</s:form>--%>
                        </div>
                        <sj:dialog                                     
                            id="updatedialog"                                 
                            autoOpen="false" 
                            modal="true" 
                            position="center"
                            title="Update Transaction Type"
                            onOpenTopics="openviewtasktopage" 
                            loadingText="Loading .."
                            width="900"
                            height="450"
                            dialogClass= "dialogclass"
                            />
                        <sj:dialog                                     
                            id="remotedialog"                                 
                            autoOpen="false" 
                            modal="true" 
                            title="Add Transaction Type"                            
                            loadingText="Loading .."                            
                            position="center"                            
                            width="900"
                            height="490"
                            dialogClass= "dialogclass"
                            />
                        <!-- Start delete confirm dialog box -->
                        <sj:dialog 
                            id="deletedialog" 
                            buttons="{ 
                            'OK':function() { deleteTransactionType($(this).data('keyval'));$( this ).dialog( 'close' ); },
                            'Cancel':function() { $( this ).dialog( 'close' );} 
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Delete Transaction Type"                            
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

                        <div id="tablediv">
                            <s:url var="listurl" action="ListTransactionType"/>
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
                                <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Edit" width="40" align="center" sortable="false" formatter="editformatter" hidden="#vupdatelink" frozen="true"/>
                                <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Delete" width="50" align="center" sortable="false" formatter="deleteformatter" hidden="#vdelete" frozen="true"/>  
                                <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Transaction Type Code"  sortable="true" frozen="true"/>
                                <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                                <sjg:gridColumn name="sortOrder" index="u.sortOrder" title="Sort Order"  sortable="true"/>
                                <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true"/>
                                <sjg:gridColumn name="OTPReqStatus" index="u.otpRequired" title="OTP Required Status"  sortable="true"/>
                                <sjg:gridColumn name="riskReqStatus" index="u.riskRequired" title="Acquirer Risk Required Status"  sortable="true"/>
                                <sjg:gridColumn name="merchantTxnType" index="u.merchantTxnType" title="Merchant Txn Type"  sortable="true"/>
                                <sjg:gridColumn name="description_receiver" index="u.description_receiver" title="Description Receiver"  sortable="true"/>
                                <sjg:gridColumn name="history_visibility" index="u.history_visibility" title="History Visibility"  sortable="true"/>
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
