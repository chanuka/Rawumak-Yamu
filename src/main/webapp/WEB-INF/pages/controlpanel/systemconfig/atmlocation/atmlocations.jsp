<%-- 
    Document   : atmlocations
    Created on : Jul 11, 2016, 9:27:45 AM
    Author     : jayana_i
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
            $.subscribe('onclicksearch', function (event, data) {
                $('#message').empty();

                var satmcode = $('#satmcode').val();
                var sdescription = $('#sdescription').val();
                var saddress = $('#address').val();
                var slongitude = $('#longitude').val();
                var slatitude = $('#latitude').val();
                var sstatus = $('#sstatus').val();
                var sstatusATM = $('#sstatusATM').val();
                var sstatusCDM = $('#sstatusCDM').val();
                var sstatusAgent = $('#sstatusAgent').val();
                var sstatusBranch = $('#sstatusBranch').val();
                var sstatusShop = $('#sstatusShop').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        satmcode: satmcode,
                        sdescription: sdescription,
                        saddress: saddress,
                        status: sstatus,
                        slongitude: slongitude,
                        slatitude: slatitude,
                        sstatusATM: sstatusATM,
                        sstatusCDM: sstatusCDM,
                        sstatusAgent: sstatusAgent,
                        sstatusBranch: sstatusBranch,
                        sstatusShop: sstatusShop,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            );




            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function refreshATMlocations() {
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchATMlocations() {
                $('#message').empty();

                var satmcode = $('#satmcode').val();
                var sdescription = $('#sdescription').val();
                var saddress = $('#address').val();
                var slongitude = $('#longitude').val();
                var slatitude = $('#latitude').val();
                var sstatus = $('#sstatus').val();
                var sstatusATM = $('#sstatusATM').val();
                var sstatusCDM = $('#sstatusCDM').val();
                var sstatusAgent = $('#sstatusAgent').val();
                var sstatusBranch = $('#sstatusBranch').val();
                var sstatusShop = $('#sstatusShop').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        satmcode: satmcode,
                        sdescription: sdescription,
                        saddress: saddress,
                        status: sstatus,
                        slongitude: slongitude,
                        slatitude: slatitude,
                        sstatusATM: sstatusATM,
                        sstatusCDM: sstatusCDM,
                        sstatusAgent: sstatusAgent,
                        sstatusBranch: sstatusBranch,
                        sstatusShop: sstatusShop,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }



            function editformatter(cellvalue, options, rowObject) {

                return "<a href='#' title='Edit' onClick='javascript:editATMLocationsInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteATMLocationsInit(&#34;" + cellvalue + "&#34;&#44;&#34;" + rowObject.atmcode + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editATMLocationsInit(keyval) {
                $("#updatedialog").data('id', keyval).dialog('open');

            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailATMLocations.action?id=" + $led.data('id'));
//                alert($led.data('id'));
            });

            function deleteATMLocationsInit(keyval, atmcode) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete location ' + atmcode + ' ?');

                return false;
            }

            function deleteATMLocations(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteATMLocations.action',
                    data: {id: keyval},
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
                $('#satmcode').val("");
                $('#sstatus').val("");
                $('#sdescription').val("");
                $('#sstatusATM').val("");
                $('#sstatusCDM').val("");
                $('#sstatusAgent').val("");
                $('#sstatusBranch').val("");
                $('#sstatusShop').val("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        satmcode: '',
                        sdescription: '',
                        status: '',
                        sstatusATM: '',
                        sstatusCDM: '',
                        sstatusAgent: '',
                        sstatusBranch: '',
                        sstatusShop: '',
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $("#latitude").val("");
                $("#longitude").val("");
                $("#address").val("");
                $('#conXL').val("");
                $("#description").val("");
                $("#atmcode").val("");
                $("#status").val("");
                $("#isAtm").attr('checked', false);
                $("#isCdm").attr('checked', false);
                $("#isBranch").attr('checked', false);
                $("#isAgent").attr('checked', false);
                $("#isShop").attr('checked', false);

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
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

                            <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                            <s:set id="vupload" var="vupload"><s:property value="vupload" default="true"/></s:set>
                            <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                            <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                            <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                            <s:set var="vsearch" ><s:property value="vsearch" default="true"/></s:set>

                                <div id="formstyle">
                                <s:form id="atmlocationform" method="post" action="ATMLocations" theme="simple" cssClass="form" >

                                    <div class="row row_1"> 

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Location Code</label>
                                                <s:textfield name="satmcode" id="satmcode" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Location Name</label>
                                                <s:textfield name="sdescription" id="sdescription" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> Status </label>
                                                <s:select  id="sstatus" list="%{statusList}"  name="sstatus" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> IS ATM </label>
                                                <s:select  id="sstatusATM" list="%{statusListATM}"  name="sstatusATM" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row row_1"> 

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> IS CDM </label>
                                                <s:select  id="sstatusCDM" list="%{statusListCDM}"  name="sstatusCDM" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> IS Branch </label>
                                                <s:select  id="sstatusBranch" list="%{statusListBranch}"  name="sstatusBranch" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> IS Agent </label>
                                                <s:select  id="sstatusAgent" list="%{statusListAgent}"  name="sstatusAgent" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label> IS Shop </label>
                                                <s:select  id="sstatusShop" list="%{statusListShop}"  name="sstatusShop" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
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
                                                onclick="searchATMlocations()"  
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
                                            <s:url var="uploadurl" action="ViewPopupcsvATMLocations"/>   
                                            <sj:submit                                                      
                                                openDialog="remotedialog"
                                                button="true"
                                                href="%{uploadurl}"
                                                disabled="#vupload"
                                                value="Upload Location"
                                                id="uploadButton"
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>
                                        <div class="form-group">
                                            <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                                            <s:url var="addurl" action="ViewPopupATMLocations"/>
                                            <sj:submit 
                                                openDialog="remoteadddialog"
                                                button="true"
                                                href="%{addurl}"
                                                disabled="#vadd"
                                                value="Add New Location"
                                                id="addButton"   
                                                cssClass="form-control btn_normal"
                                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                />
                                        </div>

                                    </div>
                                </div>




                                <sj:dialog 
                                    id="deletedialog" 
                                    buttons="{ 
                                    'OK':function() { deleteATMLocations($(this).data('keyval'));$( this ).dialog( 'close' ); },
                                    'Cancel':function() { $( this ).dialog( 'close' );} 
                                    }" 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Delete Location"                            
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
                                <sj:dialog                                     
                                    id="updatedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    position="center"
                                    title="Update Location"
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
                                    title="Upload Location"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="650"
                                    height="350"
                                    dialogClass= "dialogclass"
                                    />

                                <sj:dialog                                     
                                    id="remoteadddialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Add Location"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="900"
                                    height="450"
                                    dialogClass= "dialogclass"
                                    />

                            </div>

                            <div id="tablediv">
                                <s:url var="listurl" action="ListATMLocations"/>

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
                                    <%--<sjg:gridColumn name="id" index="u.id" title="ID"  sortable="true"/>--%>
                                    <sjg:gridColumn name="id" index="u.id" title="Edit" width="40" align="center" formatter="editformatter" hidden="#vupdatelink" frozen="true"/>
                                    <sjg:gridColumn name="id" index="u.id" title="Delete" width="50" align="center" formatter="deleteformatter" hidden="#vdelete" frozen="true"/>  
                                    <sjg:gridColumn name="atmcode" index="u.atmCode" title="Location Code"  sortable="true" frozen="true"/>
                                    <sjg:gridColumn name="description" index="u.description" title="Location Name"  sortable="true" />
                                    <sjg:gridColumn name="address" index="u.address" title="Address"  sortable="true" />
                                    <sjg:gridColumn name="latitude" index="u.latitude" title="Latitude"  sortable="true" width="90"/>
                                    <sjg:gridColumn name="longitude" index="u.longitude" title="Longitude"  sortable="true" width="90"/>
                                    <sjg:gridColumn name="status" index="u.status.description" title="Status"  sortable="true" width="60"/>
                                    <sjg:gridColumn name="isAtm" index="u.statusByIsAtm.description" title="Is ATM"  sortable="true" width="60"/>
                                    <sjg:gridColumn name="isCdm" index="u.statusByIsCdm.description" title="Is CDM"  sortable="true" width="60"/>
                                    <sjg:gridColumn name="isBranch" index="u.statusByIsBranch.description" title="Is Branch"  sortable="true" width="60"/>
                                    <sjg:gridColumn name="isAgent" index="u.statusByIsAgent.description" title="Is Agent"  sortable="true" width="50"/>
                                    <sjg:gridColumn name="isShop" index="u.statusByIsShop.description" title="Is Shop"  sortable="true" width="50"/>
                                    <sjg:gridColumn name="createdtime" index="u.createTime" title="Created Time"  sortable="true"/>

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


