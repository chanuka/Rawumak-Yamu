<%-- 
    Document   : sectionmgt
    Created on : 07/01/2014, 9:45:52 AM
    Author     : jeevan
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
                return "<a href='#' title='Edit' onClick='javascript:editSectionInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editSectionInit(keyval) {

                $("#updatedialog").data('paramCode', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMSMobUserParam.action?paramCode=" + $led.data('paramCode'));
            });


            function resetAllDataSearch() {
                $('#paramCodesearch').val("");
                $('#descriptionsearch').val("");
                $('#valuesearch').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_paramcode: '',
                        s_description: '',
                        s_value: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
            }


            function resetFieldData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                var startStatus = '<s:property value="vadd" />'
                $('#paramcode').val("");
                $('#paramcode').attr('readOnly', false);
                $("#sectionCodesearch").css("color", "black");
                $('#descriptionsearch').val("");
                $('#value').val("");

                $('#statussearch').prop('disabled', true);
                //               alert(" 2nd alert add status " + a  +" and updated stauts " + u);
                if (a == true && u == true) {
                    //                   alert("call 1st");
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'false') {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'true') {
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == false && u == true) {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function cancelAllData() {
                editSection(null);
            }

            window.onload = function () {
                $("#sectionCodesearch").css("color", "black");
            };

            function searchParam() {

                var paramCodesearch = $('#paramCodesearch').val();
                var deascriptionsearch = $('#descriptionsearch').val();
                var value = $('#valuesearch').val();


                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_paramcode: paramCodesearch,
                        s_description: deascriptionsearch,
                        s_value: value,
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

                            <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                            <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                            <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                            <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                            <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form cssClass="form" id="channelConfiguationadd" method="post" action="MSMobUserParam" theme="simple" >
                                    <div class="row row_1">     
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Parameter Code</label>
                                                <s:textfield name="paramCodesearch" id="paramCodesearch" maxLength="16" cssClass="form-control"  onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9_ ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9_ ]/g,''))"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Description </label>
                                                <s:textfield  name="descriptionsearch" id="descriptionsearch" maxLength="128" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Parameter Value</label>
                                                <s:textfield  name="valuesearch" id="valuesearch" maxLength="1000" cssClass="form-control"  accesskey=""/>
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
                                    <!--                                        <div class="col-sm-3 text-right">
                                                                                <div class="form-group">
                                    <s:url var="addurl" action="ViewPopupMSMobUserParam"/>
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurl}"
                                        buttonIcon="ui-icon-newwin" 
                                        disabled="#vadd"
                                        value="Add New Section"
                                        id="addButton"
                                        cssClass="form-control btn_normal"
                                        cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                        />
                                </div>
                            </div>-->
                                </div>

                            </div>
                            <sj:dialog                                     
                                id="updatedialog"                                 
                                autoOpen="false" 
                                modal="true" 
                                position="center"
                                title="Update Common Configuration"
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
                                title="Add Section"                            
                                loadingText="Loading .."                            
                                position="center"                            
                                width="900"
                                height="450"
                                dialogClass= "dialogclass"
                                />                            

                            <!-- Start delete confirm dialog box -->
                            <sj:dialog 
                                id="deletedialog" 
                                buttons="{ 
                                'OK':function() { deleteMSMobUserParam($(this).data('keyval'));$( this ).dialog( 'close' ); },
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
                                <s:url var="listurl" action="listMSMobUserParam"/>
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
                                    <sjg:gridColumn name="paramcode" index="paramcode" title="Edit" width="50" align="center"  formatter="editformatter" hidden="#vupdatelink" frozen="true"/> 
                                    <sjg:gridColumn name="paramcode" index="paramcode" title="Parameter Code"  sortable="true" frozen="true"/>
                                    <sjg:gridColumn name="description" index="description" title="Description"  sortable="true"/>
                                    <sjg:gridColumn name="value" index="value" title="Parameter Value"  sortable="true"/>

                                    <sjg:gridColumn name="lastupdateduser" index="lastupdateduser" title="Last Updated User"  sortable="true" hidden="true"/>
                                    <sjg:gridColumn name="lastupdatedtime" index="lastupdatedtime" title="Last Updated Time"  sortable="true" hidden="true"/>
                                    <sjg:gridColumn name="createdtime" index="createdtime" title="Created Time"  sortable="true" />
                                    <%--<sjg:gridColumn name="createdtime" index="createdtime" title="Created Time"  sortable="true" hidden="false"/>--%>


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