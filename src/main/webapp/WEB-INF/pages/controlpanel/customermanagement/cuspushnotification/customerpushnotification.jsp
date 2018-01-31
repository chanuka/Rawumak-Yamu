<%-- 
    Document   : customerpushnotification
    Created on : May 24, 2017, 3:59:22 PM
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

            function refreshCusPushNoti() {
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchCusPushNoti() {
                $('#divmsg').empty();

                var fileId = $('#fileId').val();
                var fileNamef = $('#fileNamef').val();
                var statusFile = $('#statusFile').val();


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        fileId: fileId,
                        fileNamef: fileNamef,
                        statusFile: statusFile,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }


            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("DetailCusPushNotification.action?id=" + $led.data('id'));
            });



            function resetAllData() {
                $('#fileId').val("");
                $('#fileNamef').val("");
                $('#statusFile').val("");


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        fileId: '',
                        fileNamef: '',
                        statusFile: '',
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#fileId').val("");
                $('#fileNamef').val("");
                $('#statusFile').val("");

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }



        </script>
        <style>
            th.ui-th-column div{
                white-space:normal !important;
                height:auto !important;
                padding:2px; 
            }

        </style>
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


                            <s:set id="vupload" var="vupload"><s:property value="vupload" default="true"/></s:set>
                            <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                            <s:set id="vview" var="vview"><s:property value="vview" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="cuspushnotification" method="post" action="CusPushNotification" theme="simple" cssClass="form" >

                                    <div class="row row_1"> 

                                        <div class="col-sm-3" style="display: none">
                                            <div class="form-group">
                                                <label>ID</label>
                                                <s:textfield name="fileId" id="fileId" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>File Name</label>
                                                <s:textfield name="fileNamef" id="fileNamef" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Status</label>
                                                <s:select  id="statusFile" list="%{statusFileList}"  name="statusFile" headerKey=""  headerValue="-- Select Status--" listKey="statuscode" listValue="description"  disabled="false" cssClass="form-control"/>
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
                                                href="#"
                                                disabled="#vsearch"  
                                                onclick="searchCusPushNoti()"  
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
                                    <div class="col-sm-2"></div>
                                    <div class="col-sm-6 text-right">
                                        <div class="form-group">
                                            <s:url var="uploadurl" action="ViewPopupcsvCusPushNotification"/>   
                                            <sj:submit                                                      
                                                openDialog="remotedialog"
                                                button="true"
                                                href="%{uploadurl}"
                                                disabled="#vupload"
                                                value="Upload Push Notification"
                                                id="uploadButton" 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>


                                    </div>
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

                                <sj:dialog                                     
                                    id="viewdialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    position="center"
                                    title="View Customer Push Notification"
                                    onOpenTopics="openviewtasktopage" 
                                    loadingText="Loading .."
                                    width="950"
                                    height="550"
                                    dialogClass= "dialogclass"
                                    cssStyle="padding: 30px;padding-top:15px;"
                                    />  

                            </div>

                            <div id="tablediv">
                                <s:url var="listurl" action="ListFileCusPushNotification"/>

                                <s:set var="pcaption">${CURRENTPAGE}</s:set>


                                <sjg:grid
                                    id="gridtable"
                                    caption="Customer Push Notification Files"
                                    dataType="json"
                                    href="%{listurl}"
                                    pager="true"
                                    gridModel="gridFileModel"
                                    rowList="10,15,20"
                                    rowNum="10"                                    
                                    autowidth="true"
                                    rownumbers="true"
                                    onCompleteTopics="completetopics"
                                    rowTotal="false"                                    
                                    viewrecords="true"
                                    onErrorTopics="anyerrors"

                                    >
                                    <sjg:gridColumn name="id" index="u.id" width="35" align="center" title="View" formatter="viewformatter" hidden="#vviewlink" sortable="false" frozen="true" />
                                    <sjg:gridColumn name="id" index="u.id" title="Id"  sortable="true" frozen="false" hidden="true"/>
                                    <sjg:gridColumn name="fileName" index="u.fileName" title="File Name"  sortable="true" frozen="false"/>
                                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true" />
                                    <sjg:gridColumn name="createTime" index="u.createTime" title="Created Time"  sortable="true" />
                                    <sjg:gridColumn name="lastUpdatedTime" index="u.lastUpdatedTime" title="Last Updated Time"  sortable="true" />

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



