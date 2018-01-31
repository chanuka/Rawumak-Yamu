<%-- 
    Document   : mobmsg
    Created on : Mar 22, 2017, 12:44:04 PM
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
        <title>JSP Page</title>
        <script type="text/javascript">

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editMobMsgInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editMobMsgInit(keyval) {

                $("#updatedialog").data('paramCode', keyval).dialog('open');
            }

            $.subscribe('openviewtasktomobmsg', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("DetailMobMsgMgt.action?paramCode=" + $led.data('paramCode'));
            });


            function resetAllDataSearch() {
                $('#paramCodeSearch').val("");
                $('#descriptionSearch').val("");
                $('#valueSearch').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        paramCodeSearch: '',
                        descriptionSearch: '',
                        valueSearch: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
            }


            function resetFieldData() {

                $('#paramcode').val("");
                $('#paramcode').attr('readOnly', false);
                $('#value').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchParam() {

                var paramCodeSearch = $('#paramCodeSearch').val();
                var descriptionSearch = $('#descriptionSearch').val();
                var valueSearch = $('#valueSearch').val();


                $("#gridtable").jqGrid('setGridParam', {postData: {
                        paramCodeSearch: paramCodeSearch,
                        descriptionSearch: descriptionSearch,
                        valueSearch: valueSearch,
                        search: true
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });
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

                            <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                            <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                            <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form cssClass="form" id="mobmsgsearch" method="post" action="MobMsgMgt" theme="simple" >
                                    <div class="row row_1">     
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Parameter Code</label>
                                                <s:textfield name="paramCodeSearch" id="paramCodeSearch" maxLength="16" cssClass="form-control" />
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Description </label>
                                                <s:textfield  name="descriptionSearch" id="descriptionSearch" maxLength="128" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Parameter Value</label>
                                                <s:textfield  name="valueSearch" id="valueSearch" maxLength="128" cssClass="form-control"  accesskey=""/>
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
                                                onClick="searchParam()"  
                                                id="searchbut"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />                                                
                                        </div>
                                        <div class="form-group">
                                            <sj:submit 
                                                button="true" 
                                                value="Reset" 
                                                name="cancel" 
                                                id="cancel"
                                                onClick="resetAllDataSearch()"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;"
                                                />
                                        </div>
                                    </div>
                                    <div class="col-sm-5"></div>  
                                </div>

                            </div>
                            <sj:dialog                                     
                                id="updatedialog"                                 
                                autoOpen="false" 
                                modal="true" 
                                position="center"
                                title="Update Mobile Message Management"
                                onOpenTopics="openviewtasktomobmsg" 
                                loadingText="Loading .."
                                width="900"
                                height="450"
                                dialogClass= "dialogclass"
                                />
                            <!-- Start delete confirm dialog box -->
                            <sj:dialog 
                                id="deletedialog" 
                                buttons="{ 
                                'OK':function() { deleteMobMsg($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Delete Section"                            
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
                            <div id="tablediv">
                                <s:url var="listurl" action="listMobMsgMgt"/>
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
                                    <sjg:gridColumn name="paramcode" index="paramcode" title="Edit" width="50" align="center"  formatter="editformatter" hidden="#vupdatelink" /> 
                                    <sjg:gridColumn name="paramcode" index="paramcode" title="Parameter Code"  sortable="true"  width="75"/>
                                    <sjg:gridColumn name="description" index="description" title="Description"  sortable="true"/>
                                    <sjg:gridColumn name="value" index="value" title="Parameter Value"  sortable="true"/>
                                    <sjg:gridColumn name="createdtime" index="createdtime" title="Created Time"  sortable="true" />
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
