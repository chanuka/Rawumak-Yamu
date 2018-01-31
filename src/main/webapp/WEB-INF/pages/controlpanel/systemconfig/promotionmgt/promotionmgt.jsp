<%-- 
    Document   : promotion
    Created on : Jul 9, 2016, 1:15:20 PM
    Author     : dilanka_w
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
            $.subscribe('completetopics', function (event, data) {
                var isGenerate = <s:property value="vgenerate"/>;
                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                if (recors > 0 && isGenerate == false) {
                    $('#subview').button("enable");
                    $('#subview1').button("enable");
                } else {
                    $('#subview').button("disable");
                    $('#subview1').button("disable");

                }
            });

            $.subscribe('onclicksearch', function (event, data) {
                $('#message').empty();

                var promotionIdSearch = $('#promotionIdSearch').val();
                var descriptionSearch = $('#descriptionSearch').val();
                var titleSearch = $('#titleSearch').val();
                var statusSearch = $('#statusSearch').val();
                var sortOrderSearch = $('#sortOrderSearch').val();
                var userLevelSearch = $('#userLevelSearch').val();
                var clickCountSearch = $('#clickCountSearch').val();
                var shareCountSearch = $('#shareCountSearch').val();
                var likeCountSearch = $('#likeCountSearch').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        promotionIdSearch: promotionIdSearch,
                        descriptionSearch: descriptionSearch,
                        titleSearch: titleSearch,
                        statusSearch: statusSearch,
                        sortOrderSearch: sortOrderSearch,
                        userLevelSearch: userLevelSearch,
                        clickCountSearch: clickCountSearch,
                        shareCountSearch: shareCountSearch,
                        likeCountSearch: likeCountSearch,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
                $('#subview').button("enable");
                $('#subview1').button("enable");

            });

           function onclicksearch () {
                $('#message').empty();

                var promotionIdSearch = $('#promotionIdSearch').val();
                var descriptionSearch = $('#descriptionSearch').val();
                var titleSearch = $('#titleSearch').val();
                var statusSearch = $('#statusSearch').val();
                var sortOrderSearch = $('#sortOrderSearch').val();
                var userLevelSearch = $('#userLevelSearch').val();
                var clickCountSearch = $('#clickCountSearch').val();
                var shareCountSearch = $('#shareCountSearch').val();
                var likeCountSearch = $('#likeCountSearch').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        promotionIdSearch: promotionIdSearch,
                        descriptionSearch: descriptionSearch,
                        titleSearch: titleSearch,
                        statusSearch: statusSearch,
                        sortOrderSearch: sortOrderSearch,
                        userLevelSearch: userLevelSearch,
                        clickCountSearch: clickCountSearch,
                        shareCountSearch: shareCountSearch,
                        likeCountSearch: likeCountSearch,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
                $('#subview').button("enable");
                $('#subview1').button("enable");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editPromotionInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deletePromotionInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editPromotionInit(keyval) {
                $("#updatedialog").data('promotionId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("DetailPromotionMgt.action?promotionId=" + $led.data('promotionId'));
            });

            function deletePromotionInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete campaign : ' + keyval + ' ?');
                return false;
            }

            function todoexel() {
                $('#reporttype').val("exel");
                form = document.getElementById('promotionform');
                form.action = 'ReportGeneratePromotionMgt';
                form.submit();
                $('#subview1').button("disable");
                $('#subview').button("disable");
            }

            function subtodo() {
                $('#reporttype').val("pdf");
                form = document.getElementById('promotionform');
                form.action = 'ReportGeneratePromotionMgt';
                form.submit();
                $('#subview1').button("disable");
                $('#subview').button("disable");
            }

            function deletePromotion(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletePromotionMgt.action',
                    data: {promotionId: keyval},
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

                $('#promotionIdSearch').val("");
                $('#descriptionSearch').val("");
                $('#titleSearch').val("");
                $('#statusSearch').val("");
                $('#sortOrderSearch').val("");
                $('#userLevelSearch').val("");
                $('#clickCountSearch').val("");
                $('#shareCountSearch').val("");
                $('#likeCountSearch').val("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        promotionIdSearch: '',
                        descriptionSearch: '',
                        titleSearch: '',
                        statusSearch: '',
                        sortOrderSearch: '',
                        userLevelSearch: '',
                        clickCountSearch: '',
                        shareCountSearch: '',
                        likeCountSearch: '',
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {

                var rw = $('#promotionId').val();
                rw = +rw + 1;
                $('#promotionId').val(rw);
                $('#description').val("");
                $('#title').val("");
                $('#startDate').val("");
                $('#expDate').val("");
                $('#sortOrder').val("");
                $('#userLevel').val("");
                $('#tabImg').val("");
                $('#mobileImg').val("");
                $('#status').val("");
                $("#tabImg_add").attr("src", "");
                $('#mobileImg_add').attr("src", "");

                $('#tabImgFile').val("");
                $('#mobileImgFile').val("");

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
                            <s:set var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="promotionform" method="post" action="PromotionMgt" theme="simple" cssClass="form" >
                                    <s:hidden name="reporttype" id="reporttype"></s:hidden>

                                        <div class="row row_1">
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <label >Campaign ID</label>
                                                <s:textfield name="promotionIdSearch" id="promotionIdSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Description</label>
                                                <s:textfield name="descriptionSearch" id="descriptionSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Title</label>
                                                <s:textfield  name="titleSearch" id="titleSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[]/g,''))" onmouseout="$(this).val($(this).val().replace(/[]/g,''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Click Count</label>
                                                <s:textfield name="clickCountSearch" id="clickCountSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g, ''))"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Share Count</label>
                                                <s:textfield name="shareCountSearch" id="shareCountSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g, ''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Like Count</label>
                                                <s:textfield  name="likeCountSearch" id="likeCountSearch" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g, ''))"/>
                                            </div>
                                        </div>                                                                          
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Status</label>
                                                <s:select cssClass="form-control" id="statusSearch" list="%{statusList}"  name="statusSearch" 
                                                          headerValue="-- Select Status--" headerKey=""  listKey="statuscode" listValue="description"/>                  
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label >Sort Order</label>
                                                <s:select cssClass="form-control" id="sortOrderSearch" name="sortOrderSearch" list="%{sortOrderList}"  
                                                          headerValue="--Select Sort Order--" headerKey=""  listKey="key" listValue="value"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>User Level</label>
                                                <s:select cssClass="form-control" id="userLevelSearch" name="userLevelSearch" list="%{userLevelList}" 
                                                          headerValue="--Select User Level--" headerKey=""  listKey="code" listValue="description"/>                                                                                             
                                            </div>
                                        </div>
                                    </div>
                                </s:form> 
                                <div class="row row_1 form-inline">
                                    <div class="col-sm-8">
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
                                        <div class="form-group">
                                            <sj:submit 
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                button="true" 
                                                value="View PDF" 
                                                name="subview" 
                                                id="subview" 
                                                onClick="subtodo()" 
                                                disabled="#vgenerate"/> 
                                        </div>
                                        <div class="form-group">
                                            <sj:submit                                                      
                                                button="true" 
                                                value="View Excel" 
                                                name="subview1" 
                                                id="subview1" 
                                                onClick="todoexel()"
                                                disabled="#vgenerate"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                /> 
                                        </div>
                                    </div>
                                    <!--<div class="col-sm-5"></div>-->
                                    <div class="col-sm-4 text-right">
                                        <div class="form-group">
                                            <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                                            <s:url var="addurl" action="ViewPopupPromotionMgt"/>
                                            <s:url var="updateurl" action="UpdatePromotionMgt"/>
                                            <sj:submit 
                                                openDialog="remotedialog"
                                                button="true"
                                                href="%{addurl}"
                                                disabled="#vadd"
                                                value="Add New Campaign"
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
                                    title="Add Campaign"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="900"
                                    height="550"
                                    dialogClass= "dialogclass"
                                    />
                                <!-- Start update dialog box -->
                                <sj:dialog                                     
                                    id="updatedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    position="center"
                                    title="Update Campaign"
                                    onOpenTopics="openviewtasktopage" 
                                    loadingText="Loading .."
                                    width="900"
                                    height="550"
                                    dialogClass= "dialogclass"
                                    />
                                <!-- Start delete confirm dialog box -->
                                <sj:dialog 
                                    id="deletedialog" 
                                    buttons="{ 
                                    'OK':function() { deletePromotion($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                    'Cancel':function() { $( this ).dialog( 'close' );} 
                                    }" 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Delete Campaign"                            
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
                                <s:url var="listurl" action="ListPromotionMgt"/>
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
                                    shrinkToFit="false"
                                    onErrorTopics="anyerrors"
                                    >
                                    <sjg:gridColumn name="promotionId" index="u.promotionId" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink" frozen="true"/>
                                    <sjg:gridColumn name="promotionId" index="u.promotionId" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete" frozen="true"/>  
                                    <sjg:gridColumn name="promotionId" index="u.promotionId" title="Campaign ID" sortable="true" frozen="true"/>
                                    <sjg:gridColumn name="title" index="u.title" title="Title"  sortable="true"  width="300"/>
                                    <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true" width="300"/>
                                    <sjg:gridColumn name="status" index="u.status.description" title="Status"  sortable="true" width="150"/>                                    
                                    <sjg:gridColumn name="startDate" index="u.startDate" title="Start Date"  sortable="true" width="150" formatter="date" formatoptions="{newformat : 'd-m-Y h:i a', srcformat : 'd-m-Y H:i'}"/>
                                    <sjg:gridColumn name="expDate" index="u.expDate" title="Expiry date"  sortable="true" width="150"  formatter="date" formatoptions="{newformat : 'd-m-Y h:i a', srcformat : 'd-m-Y H:i'}"/>
                                    <sjg:gridColumn name="sortOrder" index="u.sortOrder" title="Sort Order"  sortable="true"/>
                                    <sjg:gridColumn name="userLevel" index="u.userLevel" title="User Level"  sortable="true"/>                                    
                                    <sjg:gridColumn name="clickCount" index="u.clickCount" title="Click Count"  sortable="true"/>
                                    <sjg:gridColumn name="shareCount" index="u.shareCount" title="Share Count"  sortable="true"/>
                                    <sjg:gridColumn name="likeCount" index="u.likeCount" title="Like Count"  sortable="true"/>
                                    <%--<sjg:gridColumn name="updateSequence" index="u.updateSequence" title="Update Sequence"  sortable="true"/>--%>
                                    <sjg:gridColumn name="shareAttemptCount" index="u.shareAttemptCount" title="Share Attempt Count"  sortable="true"/>
                                    <%--<sjg:gridColumn name="createTime" index="u.createTime" title="Created Time"  sortable="true" formatter="date" formatoptions="{newformat : 'd/m/Y h:i a', srcformat : 'Y-m-d H:i'}"/>--%>                                                                                                          
                                    <sjg:gridColumn name="createTime" index="u.createTime" title="Created Time"  sortable="true" />                                                                                                          

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


