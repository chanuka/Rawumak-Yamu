<%-- 
    Document   : viewtransaction
    Created on : Feb 8, 2016, 4:27:59 PM
    Author     : thushanth
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
        <title>View transaction</title> 

        <script type="text/javascript">
            //global variables for previous height
            var height_a;
            var height_u;
            $("#min").hide();
            // maximize popup dialog 
            function maximize() {
                $(window).scrollTop(0);
                height_a = $("#viewdialog").height();// get sizes
                //height_u = $("#updatedialog").height();// get sizes
                $("#viewdialog").height($(window).height() - 60);
                //$("#updatedialog").height($(window).height() - 60);
                $(".ui-dialog").width($(window).width() - 40);
                $(".ui-dialog").height($(window).height() - 10);
                $("#gridtableindi").jqGrid('setGridWidth', $(".ui-dialog").width() - 77);
//                $("#resize").css('columns', '100px 2'); // content to 2 columns
                $(".ui-dialog").center();
                $("#max").hide();
                $("#min").show();
            }
            // reset to previous popup dialog
            function resetwindow() {
                $(window).scrollTop(0);
                $("#viewdialog").height(height_a);// set sizes
                //$("#updatedialog").height(height_u);// set sizes
                $(".ui-dialog").width("900");
                $(".ui-dialog").height("490");
                $("#gridtableindi").jqGrid('setGridWidth', $(".ui-dialog").width() - 77);
//                $("#resize").css('columns', '100px 1');
                $(".ui-dialog").center();
                $("#max").show();
                $("#min").hide();
            }
            //center popup dialog
            jQuery.fn.center = function () {
                this.css("position", "fixed");
                this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) +
                        $(window).scrollTop()) + "px");
                this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) +
                        $(window).scrollLeft()) + "px");
                return this;
            };

            function backToMain() {

                window.location = "${pageContext.request.contextPath}/ViewTransactionExplorer.action?load=yes";

            }
            function closeWindow() {
                window.close();
            }

            function todox() {
                //            window.open();

                form = document.getElementById('auditform2');

                //                    form.target = '_blank';
                form.action = 'individualReportTransactionExplorer.action';
//                form.submit();
                //            window.open(form);
            }
        </script>
    </head>
    <body>
        <div class="col-sm-12">
            <div style="text-align: right">
                <sj:submit 
                    id="max"
                    button="true" 
                    value="Maximize" 
                    onClick="maximize()"
                    onClickTopics="Maximize"
                    cssStyle="height: 16px;padding: 1px;width: 55px;font-size: 11px;background: orange;color: white;border-color: gray;"
                    />                          
                <sj:submit 
                    id="min"
                    button="true" 
                    value="Minimize" 
                    onClick="resetwindow()"
                    onClickTopics="Minimize"
                    cssStyle="height: 16px;padding: 1px;width: 55px;font-size: 11px;background: orange;color: white;border-color: gray;"
                    />                     
            </div>
        </div>

        <s:div id="divmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>
        <s:set var="vviewlink"><s:property value="vviewlink" default="true"/></s:set>
        <s:set id="vgenerateview" var="vgenerateview"><s:property value="vgenerateview" default=""/></s:set> 

        <s:form id="auditform2" method="post" action="TransactionExplorer" theme="simple" cssClass="form">

            <sj:tabbedpanel id="localtabs" cssClass="list form" selectedTab="0" disabledTabs="[]" >

                <sj:tab id="tab1" target="tone" label="Transaction Info"/>
                <sj:tab id="tab2" target="ttwo" label="Transaction History" disabled="true"/>

                <div id="tone">
                    <div class="col-sm-12" id="resize">
                        <table border="0" cellspacing="5">

                            <tbody>

                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Transaction ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="txnid"  value="%{transdataBean.txnid}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Wallet ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="walletid"  value="%{transdataBean.walletid}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>To Wallet</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="towallet"  value="%{transdataBean.towallet}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>From Card No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="maskedcard"  value="%{transdataBean.maskedcard}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>To Card No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="tomaskedcard"  value="%{transdataBean.tomaskedcard}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>From Mobile No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="mti"  value="%{transdataBean.mobileNo}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>To Mobile No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="tomobile"  value="%{transdataBean.tomobile}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>To Email</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="toemail"  value="%{transdataBean.toemail}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Merchant ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="mid"  value="%{transdataBean.mid}" />
                                    </td>
                                </tr>   
                                <tr>
                                    <td><b>Terminal ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="tid"  value="%{transdataBean.tid}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Transaction Type </b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="txntypecode"  value="%{transdataBean.TxntypeDes}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Trace No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="traceno"  value="%{transdataBean.traceno}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Card Brand</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="cardBrand"  value="%{transdataBean.cardBrand}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Card Type</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="cardType"  value="%{transdataBean.cardType}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Currency</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="currency"  value="%{transdataBean.currencydes}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Amount</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="amount"  value="%{transdataBean.amount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Promotion Amount</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="promoAmount"  value="%{transdataBean.promoAmount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Commission Amount</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="commissionAmount"  value="%{transdataBean.commissionAmount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Deduct Amount</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="deductAmount"  value="%{transdataBean.deductAmount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Status</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="status"  value="%{transdataBean.status}" />
                                    </td>
                                </tr>     
                                <tr>
                                    <td><b>Approval Status</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="approvalstatus"  value="%{transdataBean.approvalstatus}" />
                                    </td>
                                </tr>     
                                <tr>
                                    <td><b>Response</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="rescode"  value="%{transdataBean.responseDes}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>RRN</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="rrn"  value="%{transdataBean.rrn}" />
                                    </td>
                                </tr>                                   
                                <tr>
                                    <td><b>Token ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="tokenid"  value="%{transdataBean.tokenid}" />
                                    </td>
                                </tr>                                   
                                <tr>
                                    <td><b>Invoice No</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="invoiceno"  value="%{transdataBean.invoiceno}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Processing Code</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="processingcode"  value="%{transdataBean.processingcode}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Approved Code</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="approvecode"  value="%{transdataBean.approvecode}" />
                                    </td>
                                </tr>  
                                <tr>
                                    <td><b>From Account</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="fromAccount"  value="%{transdataBean.fromAccount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>To Account</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="toAccount"  value="%{transdataBean.toAccount}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>POS Condition Code</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="posconcode"  value="%{transdataBean.posconcode}" />
                                    </td>
                                </tr>

                                <tr>
                                    <td><b>NII</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="nii"  value="%{transdataBean.nii}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>MTI</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="mti"  value="%{transdataBean.mti}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Local Date</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="localdate"  value="%{transdataBean.localdate}" />
                                    </td>
                                </tr> 

                                <tr>
                                    <td><b>Local Time</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="localtime"  value="%{transdataBean.localtime}" />
                                    </td>
                                </tr> 

                                <tr>
                                    <td><b>POS Entry Mode</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="posentrymode"  value="%{transdataBean.posentrymode}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Card Expire Date</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="cardexdate"  value="%{transdataBean.cardexdate}" />
                                    </td>
                                </tr> 

                                <tr>
                                    <td><b>Last Updated Time</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="lastupdatedtime"  value="%{transdataBean.lastupdatedtime}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td><b>Created Time</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label style="margin-bottom: 0px;" name="createdtime"  value="%{transdataBean.createdtime}" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>
                                        <sj:submit 
                                            button="true" 
                                            id="viewindi" 
                                            onclick="todox()" 
                                            disabled="#vgenerateview"
                                            value="View PDF"
                                            cssClass="btn_normal btn-sm"
                                            cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                            />
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>

                <div id="ttwo">

                    <!-- Start delete confirm dialog box -->
                    <sj:dialog 
                        id="deletedialog" 
                        buttons="{ 
                        'OK':function() { deletePage($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete System Page"                            
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

                    <div id="tablediv" >

                        <s:url var="listurl" action="ListTransactionExplorer?transactionID=%{transdataBean.txnid}"/>
                        <sjg:grid 
                            id="gridtableindi"
                            caption="Transaction History"                             
                            dataType="json"
                            href="%{listurl}"
                            pager="true"
                            gridModel="gridHisModel"
                            rowList="10,15,20"
                            rowNum="10"
                            autowidth="true"
                            rownumbers="true"
                            onCompleteTopics="completetopics"
                            rowTotal="false"
                            viewrecords="true"

                            >
                            <sjg:gridColumn name="transactionhistoryid" index="transactionhistoryid" title="Transaction History ID" sortable="true"/>
                            <sjg:gridColumn name="transactionid" index="transactionid" title="Transaction ID" sortable="true"/>
                            <sjg:gridColumn name="statuscode" index="status" title="Status" sortable="true" />                                        
                            <sjg:gridColumn name="createdtime" index="createtime" title="Created Time" sortable="true" />
                        </sjg:grid>
                    </div> 
                </div>
            </sj:tabbedpanel>      
        </s:form>

    </body>
</html>

