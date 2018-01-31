<%-- 
    Document   : transactionexplorer
    Created on : Feb 8, 2016, 4:27:46 PM
    Author     : thushanth
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
//                return "<a href='viewDetailTransactionExplorer.action?transactionID=" + cellvalue + "' title='View Transaction' ><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
                return "<a href='#' title='View Transaction' onClick='javascript:viewTranInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function viewTranInit(keyval) {
                $("#viewdialog").data('transactionID', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailTransactionExplorer.action?transactionID=" + $led.data('transactionID'));
            });

            function ShowFileExplorer(fileid) {
                var url = "viewDetailTransactionExplorer.acs?transactionID=" + fileid;
                window.open(url, "View Transaction Explorer", "status = 1, height = 600, width = 1000, resizable = yes, scrollbars=1");
            }

            function searchTxn(param) {

                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var txnTypeCode = $('#txnTypeCode').val();
                var amount = $('#amount').val();
                var deviceID = $('#deviceID').val();
                var transactionID = $('#transactionID').val();
                var approveCode = $('#approveCode').val();
                var rrn = $('#rrn').val();
                var traceNo = $('#traceNo').val();
                var resCode = $('#resCode').val();
                var processingcode = $('#processingcode').val();
                var status = $('#status').val();
                var cardNo = $('#cardNo').val();
                var walletid = $('#walletid').val();
                var mobileNo = $('#mobileNo').val();
                var mid = $('#mid').val();
                var tid = $('#tid').val();
                var currency = $('#currency').val();
                var nic = $('#nic').val();
                var fromcardno = $('#fromcardno').val();
//                var listeneruid = $('#listeneruid').val();
                var invoiceno = $('#invoiceno').val();
                var tocardno = $('#tocardno').val();
                var toMobileNo = $('#toMobileNo').val();
                var tokenid = $('#tokenid').val();
                var toEmail = $('#toEmail').val();
                var approvalstatus = $('#approvalstatus').val();

                var load = "no";
                var json = "json";
                $("#gridtable").jqGrid('setGridParam', {postData: {
                        fromdate: fromdate,
                        todate: todate,
                        txnTypeCode: txnTypeCode,
                        amount: amount,
                        deviceID: deviceID,
                        approveCode: approveCode,
                        transactionID: transactionID,
                        rrn: rrn,
                        traceNo: traceNo,
                        load: load,
                        resCode: resCode,
                        processingcode: processingcode,
                        status: status,
                        cardNo: cardNo,
                        nic: nic,
                        walletid: walletid,
                        mobileNo: mobileNo,
                        fromcardno: fromcardno,
                        tocardno: tocardno,
                        tid: tid,
//                        listeneruid: listeneruid,
                        invoiceno: invoiceno,
                        mid: mid,
                        toMobileNo: toMobileNo,
                        tokenid: tokenid,
                        toEmail: toEmail,
                        approvalstatus: approvalstatus,
                        search: param}});

                $("#gridtable").jqGrid('setGridParam', {page: 1, datatype: json});
                jQuery("#gridtable").add();
                jQuery("#gridtable").trigger("reloadGrid");
//
//                $('#view').button("enable");
//                $('#view1').button("enable");

            }
            ;

            $.subscribe('completetopics', function (event, data) {

                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                var isGenerate = <s:property value="vgenerate"/>;

                if (recors > 0 && isGenerate == false) {
                    $('#view').button("enable");
                    $('#view1').button("enable");
                } else {
                    $('#view').button("disable");
                    $('#view1').button("disable");
                }
            });

            $.subscribe('onbeforetopics', function (event, data) {
                $("#gridtable").jqGrid('setGridParam', {postData: {
                        load: "yes"
                    }});
            });

            function resetAllData() {

                $('#fromdate').val("");
                $('#todate').val("");
                $('#fromdate').val("");
                $('#todate').val("");
                $('#txnTypeCode').val("");
                $('#amount').val("");
                $('#deviceID').val("");
                $('#transactionID').val("");
                $('#approveCode').val("");
                $('#rrn').val("");
                $('#nic').val("");
                $('#traceNo').val("");
                $('#resCode').val("");
                $('#processingcode').val("");
                $('#status').val("");
                $('#cardNo').val("");
                $('#walletid').val("");
                $('#mobileNo').val("");
                $('#tid').val("");
                $('#currency').val("");
                $('#tocardno').val("");
                $('#fromcardno').val("");
//                $('#listeneruid').val("");
                $('#invoiceno').val("");
                $('#mid').val("");
                $('#toMobileNo').val("");
                $('#tokenid').val("");
                $('#toEmail').val("");
                $('#approvalstatus').val("");
//                $('#generateButton').button("disable");

                setdate(false);

                $("#gridtable").jqGrid('clearGridData', true);
                searchTxn(false);

            }

            function setdate(parm) {

                var path = window.location.href;
                path = path.indexOf('load=yes');

                if (path == -1) {
                    $("#fromdate").datepicker("setDate", new Date());
                    $("#todate").datepicker("setDate", new Date());
                } else {
                    if (parm) {
                        var json = "json";
                        var load = "yes";
                        $("#gridtable").jqGrid('setGridParam', {postData: {
                                search: false, load: load

                            }});
                        $("#gridtable").jqGrid('setGridParam', {page: 1, datatype: json});
                        jQuery("#gridtable").trigger("reloadGrid");

                    } else {
                        $("#fromdate").datepicker("setDate", new Date());
                        $("#todate").datepicker("setDate", new Date());
                    }
                }
            }

            function todoexel() {
                //            window.open();
                $('#reporttype').val("exel");
                form = document.getElementById('tranexpo');
                //                    form.target = '_blank';
                form.action = 'reportGenerateTransactionExplorer.action';
                form.submit();
                //            window.open(form);
                $('#view').button("disable");
                $('#view1').button("disable");
            }

            function todo() {
                //            window.open();
                $('#reporttype').val("pdf");
                form = document.getElementById('tranexpo');

                //                    form.target = '_blank';
                form.action = 'reportGenerateTransactionExplorer.action';
                form.submit();
                //            window.open(form);
                $('#view').button("disable");
                $('#view1').button("disable");
            }

            $.subscribe('anyerrors', function (event, data) {
                //                   alert('status: ' + event.originalEvent.status + '\n\nrequest status: ' +event.originalEvent.request.status+ '\n\nerror: ' + event.originalEvent.error);
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function isNumberserch(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function search(e) {
                var key = e.keyCode || e.which;
                if (key == 13) {
                    searchTxn(true);
                }
            }
            function formatNumber(num) {
                var renum = num.replace(/[^0-9.]/g, '');
                return renum.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
            }
            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k >= 48 && k <= 57));
            }
        </script>
        <style>
            th.ui-th-column div{
                white-space:normal !important;
                height:auto !important;
                padding:2px; 
            }
        </style>
    </head>

    <body onload="setdate(true)">
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


                            <s:set var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>


                                <div id="formstyle">
                                <s:form id="tranexpo" method="post" action="TransactionExplorer" theme="simple" cssClass="form-inline">
                                    <s:hidden name="reporttype" id="reporttype"></s:hidden>

                                        <div class="row row_1">
                                            <div class="col-sm-3">
                                                <div class="form-group form-inline">
                                                    <label >From Date</label>
                                                <sj:datepicker  cssClass="form-control"
                                                                id="fromdate" name="fromdate" readonly="true" 
                                                                changeYear="true" maxDate="d" buttonImageOnly="true" 
                                                                displayFormat="yy-mm-dd" 
                                                                value="%{transdataBean.fromdate}"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >To Date</label>
                                                <sj:datepicker cssClass="form-control"  id="todate" name="todate" readonly="true" changeYear="true" maxDate="+1d" buttonImageOnly="true" displayFormat="yy-mm-dd"  value="%{transdataBean.todate}"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Transaction ID </label>
                                                <s:textfield cssClass="form-control" name="transactionID" id="transactionID" maxLength="100" value="%{transdataBean.txnid}" onkeypress="search(event)"
                                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Wallet ID</label>
                                                <s:textfield cssClass="form-control" name="walletid" id="walletid" maxLength="10" value="%{transdataBean.walletid}"onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="search(event)"/>

                                            </div>
                                        </div>

                                    </div> 
                                    <div class="row row_1">   
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >From Card No</label>
                                                <s:textfield cssClass="form-control" id="cardNo" name="cardNo" value="%{transdataBean.cardno}" 
                                                             maxLength="50" onkeypress="search(event)" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >To Card No</label>
                                                <s:textfield cssClass="form-control" name="tocardno" id="tocardno" maxLength="50" value="%{transdataBean.tocardno}"onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >From Mobile No</label>
                                                <s:textfield cssClass="form-control" name="mobileNo" id="mobileNo" value="%{transdataBean.mobileNo}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="10" onkeypress="search(event)"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >To Mobile No</label>
                                                <s:textfield cssClass="form-control" name="toMobileNo" id="toMobileNo" value="%{transdataBean.toMobileNo}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="10" onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1"> 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >To Email</label>
                                                <s:textfield cssClass="form-control" name="toEmail" id="toEmail" value="%{transdataBean.toEmail}" maxLength="100" onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Merchant ID</label>
                                                <s:textfield cssClass="form-control" cssStyle="width: 219px" name="mid" id="mid" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                             maxLength="15" value="%{transdataBean.mid}" onkeypress="search(event)"/>                                          

                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Terminal ID </label>
                                                <s:textfield cssClass="form-control" name="tid" id="tid" maxLength="8" 
                                                             value="%{transdataBean.tid}" onkeypress="search(event)" 
                                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Transaction Type</label>
                                                <s:select cssClass="form-control"  id="txnTypeCode" list="%{txnTypeList}"  
                                                          name="txnTypeCode" headerKey="" headerValue="--Select Transaction Type--" 
                                                          listKey="typecode" listValue="description" value="%{transdataBean.txntypecode}"/>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">        
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Trace No</label>
                                                <s:textfield cssClass="form-control" name="traceNo" id="traceNo" maxLength="50"
                                                             value="%{transdataBean.traceno}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Amount</label>
                                                <s:textfield cssClass="form-control" name="amount" id="amount" maxLength="20"
                                                             value="%{transdataBean.amount}" 
                                                             onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" 
                                                             onmouseout="$(this).val($(this).val().replace(/[^0-9.]/g,''))" 
                                                             onkeypress="search(event)" />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Status </label>
                                                <s:select cssClass="form-control" cssStyle="width: 219px" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{transdataBean.status}"/>
                                            </div>
                                        </div> 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Approval Status </label>
                                                <s:select cssClass="form-control" cssStyle="width: 219px" id="approvalstatus" list="%{approvalStatusList}"  name="approvalstatus" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{transdataBean.approvalstatus}"/>
                                            </div>
                                        </div>                                        
                                    </div>        
                                    <div class="row row_1"> 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >RRN</label>
                                                <s:textfield cssClass="form-control" id="rrn" name="rrn"  value="%{transdataBean.rrn}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="50" onkeypress="search(event)"/>

                                            </div>
                                        </div> 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Response </label>
                                                <s:select cssClass="form-control"  maxLength="10" id="resCode" list="%{responseList}"  name="resCode" headerKey="" headerValue="--Select Response --" listKey="responsecode" listValue="description" value="%{transdataBean.rescode}"/>
                                            </div>
                                        </div> 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Invoice Number</label>
                                                <s:textfield cssClass="form-control" name="invoiceno" id="invoiceno" maxLength="6" value="%{transdataBean.invoiceno}"onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="search(event)"/>
                                            </div>
                                        </div>                                                 
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Processing Code </label>
                                                <s:textfield cssClass="form-control" id="processingcode" name="processingcode" 
                                                             value="%{transdataBean.processingcode}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="6"  onkeypress="search(event)"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline">
                                                <label >Token ID</label>
                                                <s:textfield cssClass="form-control" name="tokenid" id="tokenid" maxLength="20"
                                                             value="%{transdataBean.tokenid}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="search(event)"
                                                             />
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
                                                onclick="searchTxn(true)"
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
                                                name="view" 
                                                id="view" 
                                                onClick="todo()" 
                                                disabled="#vgenerate"/> 
                                        </div>

                                        <div class="form-group">
                                            <sj:submit 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                button="true" 
                                                value="View Excel" 
                                                name="view1" 
                                                id="view1" 
                                                onClick="todoexel()" 
                                                disabled="#vgenerate"/> 
                                        </div>
                                    </div>

                                </div>

                                <sj:dialog                                     
                                    id="viewdialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    position="center"
                                    title="View Transaction Explorer"
                                    onOpenTopics="openviewtasktopage" 
                                    loadingText="Loading .."
                                    width="900"
                                    height="500"
                                    dialogClass= "dialogclass"

                                    />  

                            </div>


                            <div id="tablediv">
                                <s:url var="listurl" action="SearchTransactionExplorer"/>
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
                                    shrinkToFit="false"                        
                                    >
                                    <sjg:gridColumn name="txnid" index="g.transactionid" width="35" align="center" title="View" formatter="viewformatter" hidden="#vviewlink" sortable="false" frozen="true"/>
                                    <sjg:gridColumn name="txnid" index="g.transactionid" title="Transaction ID"  sortable="true" frozen="true" width="250"/>
                                    <sjg:gridColumn name="walletid" index="g.WALLETID" title="Wallet ID"  sortable="true" width="85"/>      
                                    <sjg:gridColumn name="towallet" index="g.TOWALLET" title="To Wallet ID"  sortable="true" width="85"/>      
                                    <sjg:gridColumn name="maskedcard" index="g.MASKING_CARD" title="From Card Number"  sortable="true" width="100"/>  
                                    <sjg:gridColumn name="tomaskedcard" index="g.TO_MASKING_CARD" title="To Card Number"  sortable="true" width="100"/>   
                                    <sjg:gridColumn name="mobile" index="g.MOBILENUMBER" title="From Mobile"  sortable="true" width="75"/>  
                                    <sjg:gridColumn name="tomobile" index="g.TOMOBILE" title="To Mobile"  sortable="true" width="75"/>                               
                                    <sjg:gridColumn name="toemail" index="g.TO_EMAIL" title="To Email"  sortable="true"/>                               
                                    <sjg:gridColumn name="mid" index="g.mid" title="Merchant ID"  sortable="true" width="100"/>
                                    <sjg:gridColumn name="tid" index="g.tid" title="Terminal ID"  sortable="true" width="100"/>
                                    <sjg:gridColumn name="txntypecode" index="g.txntypecode" title="Transaction Type"  sortable="true"/>
                                    <sjg:gridColumn name="traceno" index="g.tracenumber" title="Trace No" sortable="true"/>  
                                    <sjg:gridColumn name="cardBrand" index="g.CARD_TYPE" title="Card Brand" sortable="true" width="60"/>  
                                    <sjg:gridColumn name="cardType" index="g.CARD_BRAND" title="Card Type" sortable="true" width="60"/>  
                                    <sjg:gridColumn name="currency" index="g.CURRENCYCODE" title="Currency"  sortable="true"  width="110"/>
                                    <sjg:gridColumn name="amount" index="g.amount" title="Amount"  sortable="true" width="100" align="right"/>
                                    <sjg:gridColumn name="promoAmount" index="g.PROMO_AMOUNT" title="Promotion Amount"  sortable="true" width="100" align="right"/>
                                    <sjg:gridColumn name="commissionAmount" index="g.COMM_AMOUNT" title="Commission Amount"  sortable="true" width="100" align="right"/>
                                    <sjg:gridColumn name="deductAmount" index="g.DEDUCT_AMOUNT" title="Deduct Amount"  sortable="true" width="100" align="right"/>
                                    <sjg:gridColumn name="statucode" index="g.STATUS" title="Status" sortable="true"/> 
                                    <sjg:gridColumn name="approvalstatus" index="g.APPROVAL_STATUS" title="Approval Status" sortable="true"/> 
                                    <sjg:gridColumn name="rescode" index="g.RESPONCECODE" title="Response" sortable="true"/>
                                    <sjg:gridColumn name="rrn" index="g.rrn" title="RRN" sortable="true"/> 
                                    <sjg:gridColumn name="tokenid" index="g.tokenid" title="Token ID" sortable="true"/>                                    
                                    <sjg:gridColumn name="invoiceno" index="g.invoiceno" title="Invoice No" sortable="true" width="60"/>
                                    <sjg:gridColumn name="processingcode" index="g.processingcode" title="Processing Code" sortable="true" width="70"/> 
                                    <sjg:gridColumn name="approvecode" index="g.approvecode" title="Approve Code" sortable="true"/>     
                                    <%--<sjg:gridColumn name="listeneruid" index="g.LISTNERUID" title="Listener UID"  sortable="true"/>--%>                                                                                      
                                    <sjg:gridColumn name="createdtime" index="g.createtime" title="Created Time"  sortable="true" width="120"/>

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


