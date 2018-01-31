<%-- 
    Document   : customerpushnotificationview
    Created on : Jun 27, 2017, 2:48:00 PM
    Author     : prathibha_s
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
        <title>Notification</title> 
        <script type="text/javascript">

            function viewformatter(cellvalue) {
                return "<a href='#' title='View Transaction' onClick='javascript:viewCPNFInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function viewCPNFInit(keyval) {
                $("#viewdialog").data('id', keyval).dialog('open');
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function searchCusPushNotiview() {
                $('#divmsgs').empty();

                var walletId = $('#walletId').val();
                var messages = $('#messages').val();
                var fileName = $('#fileName').val();
                var status = $('#statusView').val();


                $("#gridtable2").jqGrid('setGridParam', {
                    postData: {
                        walletId: walletId,
                        messages: messages,
                        fileName: fileName,
                        status: status,
                        search: true
                    }
                });

                $("#gridtable2").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable2").trigger("reloadGrid");

            }

            function resetAllDatav() {
                $('#divmsgs').empty();
                $('#walletId').val("");
                $('#messages').val("");
                $('#fileName').val("");
                $('#statusView').val("");


                $("#gridtable2").jqGrid('setGridParam', {
                    postData: {
                        walletId: '',
                        messages: '',
                        fileName: '',
                        status: '',
                        search: false
                    }
                });
                jQuery("#gridtable2").trigger("reloadGrid");
            }

            function resetFieldDatas() {
                $('#walletId').val("");
                $('#messages').val("");
                $('#fileName').val("");
                $('#status').val("");

                $("#gridtable2").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable2").trigger("reloadGrid");

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }



            function sendStatus() {

                $('#sendID').button("disable");

                $.ajax({
                    url: '${pageContext.request.contextPath}/ChangeStatusCusPushNotification.action',
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.message == null) {
                            document.getElementById("divmsgs").innerHTML = "<div class='ui-widget actionMessage'>"
                                    + "<div class='ui-state-highlight ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'>"
                                    + "<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>"
                                    + "Successfully send notifications"
                                    + "</span></p></div></div>";
                            resetFieldDatas();
                        } else {
                            document.getElementById("divmsgs").innerHTML = "<div class='ui-widget actionError'>"
                                    + "<div class='ui-state-error ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'>"
                                    + "<p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: 0.3em;'></span><span>"
                                    + data.message
                                    + "</span></p></div></div>";
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }


            $(document).ready(function () {
                var vsend = ('${VSEND}' === "true");
                var vsearch = ('${VSEARCH}' === "true");

                $("#sendID").attr('disabled', vsend);
                $("#searchID").attr('disabled', vsearch);

                if (vsend) {
                    $("#sendID").attr('disabled', vsend);
                } else {
                    var vsendstatus = ('${STATUSFROMID}' != "PNINI");
                    $("#sendID").attr('disabled', vsendstatus);
                }
            });



        </script>
        <style>
            #tabledivs {
                margin-left: 0;
                width: 95%;
                margin-bottom: 20px;
            }
        </style>
    </head>

    <body>
        <s:div id="divmsgs">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <div class="row row_popup">
            <div class="row">

                <s:form id="cuspushnotificationn" method="post" action="ListCusPushNotification" theme="simple" cssClass="form" >

                    <div class="row row_1"> 
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label>Wallet ID</label>
                                <s:textfield maxLength="10" name="walletId" id="walletId" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label>Message</label>
                                <s:textfield name="messages" id="messages" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label >Status</label>
                                <s:select  id="statusView" list="%{statusList}"  name="status" headerKey=""  headerValue="-- Select Status--" listKey="statuscode" listValue="description"  disabled="false" cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                </s:form>
            </div>

            <div class="row form-inline">
                <div class="col-sm-1">
                    <div class="form-group">
                        <sj:submit 
                            id="searchID"
                            button="true" 
                            value="Search" 

                            onclick="searchCusPushNotiview()"
                            cssClass="form-control btn_normal"
                            cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                            />
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="form-group">                                                   
                        <sj:submit 
                            button="true" 
                            value="Reset" 
                            name="reset" 
                            onClick="resetAllDatav()"
                            cssClass="form-control btn_normal"
                            cssStyle="border-radius: 12px;"
                            /> 
                    </div>
                </div>

                <div class="col-sm-1">
                    <div class="form-group">
                        <sj:submit 
                            id="sendID"
                            button="true" 
                            value="Send" 
                            disabled="#vsend"  
                            onclick="sendStatus()"
                            cssClass="form-control btn_normal"
                            cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                            />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="horizontal_line_popup"></div>
            </div>


            <sj:dialog                                     
                id="remotedialog"                                 
                autoOpen="false" 
                modal="true" 
                title="Upload Push Notification"                            
                loadingText="Loading .."                            
                position="center"                            
                width="650"
                height="350"
                dialogClass= "dialogclass"
                />



            <div id="tabledivs">
                <s:url var="listurl" action="ListCusPushNotification?id=%{id}"/>
                <sjg:grid
                    id="gridtable2"
                    caption="Customer Push Notification"
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
                    <sjg:gridColumn name="id" index="u.id" title="Id"  sortable="true" frozen="false" hidden="true"/>
                    <sjg:gridColumn name="walletId" index="u.walletId" title="Wallet ID"  sortable="true" frozen="false"/>
                    <sjg:gridColumn name="message" index="u.message" title="Message"  sortable="true" />
                    <sjg:gridColumn name="fileName" index="u.fileName" title="File Name"  sortable="true" />
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true"/>
                    <sjg:gridColumn name="createTime" index="u.createTime" title="Created Time"  sortable="true" />

                </sjg:grid> 
            </div>
        </div>
    </body>
</html>
