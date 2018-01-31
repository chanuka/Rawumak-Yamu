<%-- 
    Document   : terms
    Created on : Nov 8, 2016, 11:07:34 AM
    Author     : jayana_i
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>

<html>
    <head>
        <style>
            .mce-content-body ul{
                list-style-position: inside;
            }
        </style>
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">

            function onlooad() {
                $('#updateButton').button("disable");
                $('#deleteButton').button("disable");
                $('#status').attr('disabled', true);
            }



            function deleteTermInit() {
                $('#divmsg').empty();
                var termsid = $('#termsid').val();
                var versionno = $('#versionno').val();
                var status = $('#status').val();
                $("#deletedialog").data({'term': termsid, 'version': versionno, 'status': status}).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete terms and conditions : ' + versionno + ' ?');
                return false;
            }

            function deleteTerm(termsid, versionno, status) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteTerms',
                    data: {termsid: termsid,
                        versionno: versionno,
                        status: status
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var msg = data.message;
                        if (msg == null) {
                            resetAllData();
                            document.getElementById("divmsg").innerHTML = "<div class='ui-widget actionMessage'><div class='ui-state-highlight ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>Terms and conditions deleted successfully</span></p></div></div></div>";
                            $('html, body').animate({scrollTop: "0px"}, 'fast');
                        } else {
                            resetAllData();
                            document.getElementById("divmsg").innerHTML = "<div class='ui-widget actionError'><div class='ui-state-error ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>" + msg + "</span></p></div></div></div>";
                            $('html, body').animate({scrollTop: "0px"}, 'fast');
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }


                });
//                alert(termsid + " " + versionno + " " + status);
//                resetAllData();
//                document.getElementById("divmsg").innerHTML = "<div class='ui-widget actionMessage'><div class='ui-state-highlight ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>Terms and conditions deleted successfully</span></p></div></div></div>";

            }

            function editTerms(keyval) {

                $('#versionno').empty();
//                $('#status').empty();
                $('#versionno').append(
                        $('<option></option>').val(0).html("--Select Version Number--")
                        );

                var termsid = $('#termsid').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindTerms',
                    data: {termsid: termsid},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        }
                        else {
                            $.each(data.versionMap, function (index, item) {
                            });

//                            if (data.description) {
//                                $('#description').val(data.description);
//                                quill.root.innerHTML=document.getElementById("description_hidden").value;
//                                quill.root.innerHTML=data.description;
//                            } else {
//                                $('#description').val("");
                            tinyMCE.get('descriptionupmethod').setContent('', {format: 'html'});
//                            quill.setText("");
//                            }

                            $.each(data.versionMap, function (index, item) {
                                $('#versionno').append(
                                        $('<option></option>').val(item.key).html(item.value)
                                        );
                            });
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function loadVersions(keyval) {
                var termsid = $('#termsid').val();
                var versionno = $('#versionno').val();
//                alert(versionno);
//                if (keyval == '0') {
//                    $('#updateButton').button("disable");
//                    $('#deleteButton').button("disable");
//                } else {
//                    $('#updateButton').button("enable");
//                    $('#deleteButton').button("enable");
//                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadVersionTerms',
                    data: {termsid: termsid,
                        versionno: versionno
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
//                            $('#updateButton').button(".disabled");
                        }
                        else {
//                            $('#description').val(data.description);
                            if (data.description) {
                                $('#descriptionupmethod').val(data.description);
//                                quill.root.innerHTML = document.getElementById("description_hidden").value;
                                var content = document.getElementById("description_hidden").value;
                                tinyMCE.get('descriptionupmethod').setContent(content, {format: 'html'});
//                                quill.root.innerHTML = data.description;
                                var contents = data.description;
                                tinyMCE.get('descriptionupmethod').setContent(contents, {format: 'html'});
                            } else {
                                $('#descriptionupmethod').val("");
//                                quill.setText("");
                                tinyMCE.get('descriptionupmethod').setContent('', {format: 'html'});
                            }


//                            var fieldNameElement = document.getElementById("description");
//                            fieldNameElement.innerHTML=data.description;



                            $('#status').val(data.status);
                            if (data.status == 'DEACT') {
                                $('#status').attr('disabled', false);
                            } else if (data.status == 'ACT') {
                                $('#status').attr('disabled', true);
                                $('#statusAct').val(data.statusAct);
                                $('#deleteButton').button("disable");

                            } else {
                                $('#status').attr('disabled', true);
                            }
                            $('#statusAct').val(data.statusAct);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetAllData() {
//                var content = document.getElementById("description_hidden").value;
//                var c = tinyMCE.activeEditor.getContent({format: 'html'});
//                alert(c);
                $("#termsid").val("");
                $("#versionno").empty();
                $('#versionno').append(
                        $('<option></option>').val(0).html("--Select Version Number--")
                        );
                $("#versionno").attr('disabled', true);
                $("#descriptionupmethod").val("");
                $("#status").attr('disabled', true);
                $("#status").val("");
                $('#updateButton').button("disable");
                $('#deleteButton').button("disable");
                $("#divmsg").empty();
//                alert(quill.root.innerHTML);
//                quill.setText("");
//                tinyMCE.activeEditor.setContent('', {format: 'html'});
                tinyMCE.get('descriptionupmethod').setContent('', {format: 'html'});
            }

            function resetFieldData() {
                $("#termsid").val("");
                $("#descriptionupmethod").val("");
                tinyMCE.get('descriptionupmethod').setContent('', {format: 'html'});
//                tinyMCE.activeEditor.setContent('', {format: 'html'});
//                quill.setText("");
                $("#versionno").attr('disabled', true);
                $("#versionno").empty();
                $('#versionno').append(
                        $('<option></option>').val(0).html("--Select Version Number--")
                        );
                $("#status").attr('disabled', true);
                $("#status").val("");
                $('#updateButton').button("disable");
                $('#deleteButton').button("disable");
                $('#termsidadd').val("");
                $('#descriptionaddmethod').val("");
                $('#versionnoadd').val("");
                $('#statusadd').val("");
            }


            function enableVersionno(keyval) {

                if (keyval === "") {
                    $("#versionno").val("");
                    $("#versionno").attr('disabled', true);
                    $("#status").attr('disabled', true);
                    editTerms(null);

                } else if (keyval === 'TEEN') {
                    $("#versionno").attr('disabled', false);
//                    $("#status").attr('disabled', false);

                    editTerms(keyval);
                } else if (keyval === 'ADULT') {
                    $("#versionno").attr('disabled', false);
//                    $("#status").attr('disabled', false);
                    editTerms(keyval);
                }
            }

            function changeVersion(val) {
                var isGenerate = <s:property value="vupdatelink"/>;
                var isdelete = <s:property value="vdelete"/>;

//                alert(isGenerate + " " + isdelete + val);
                if (!isGenerate) {
                    $('#updateButton').button("enable");
                } else {
                    $('#updateButton').button("disable");
                }

                if (!isdelete) {
                    $('#deleteButton').button("enable");
                } else {
                    $('#deleteButton').button("disable");
                }

                loadVersions(val);
            }
            function resetAllDataAdd() {
                var startStatus = '<s:property value="vadd" />'
                $('#termsidadd').val("");
                $('#descriptionaddmethod').val("");
                tinyMCE.get('descriptionaddmethod').setContent('', {format: 'html'});
//                quilladd.setText("");
//                tinyMCE.activeEditor.setContent('', {format: 'html'});
//                tinyMCE.activeEditor.setContent('', {format: 'html'});
                $('#versionnoadd').val("");
                $('#statusadd').val("");
                $('#amessage').text("");
//                if (a == true && u == true) {
//                    $('#addButton').button("disable");
//                    $('#updateButton').button("disable");
//                } else if (a == true && u == false && startStatus == 'false') {
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
//                } else if (a == true && u == false && startStatus == 'true') {
//                    $('#addButton').button("disable");
//                    $('#updateButton').button("disable");
//                } else if (a == false && u == true) {
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
//                }
            }

            function clearQuill() {

//                quilladd.setText("");
//                tinyMCE.activeEditor.setContent('', {format: 'html'});
                tinyMCE.get('descriptionaddmethod').setContent('', {format: 'html'});
            }



            // Open dialog and add tinymce to it
            $(document).ready(function () {
                $('#addButton').click(function () {
                    $("#dialogAdd").dialog({
                        width: 902,
                        height: 520,
                        modal: true,
                        close: function () {
                            tinyMCE.get('descriptionaddmethod').setContent('', {format: 'html'});
                        }
                    });

                    tinymce.init({
                        selector: '#descriptionaddmethod',
                        height: 175,
                        toolbar_items_size: 'small',
                        menubar: false,
//                        forced_root_block: '',
                        theme: 'modern',
                        plugins: ["advlist  link image lists charmap print preview hr anchor pagebreak spellchecker",
                            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                            "table contextmenu directionality emoticons template textcolor fullpage textcolor colorpicker textpattern"
                        ],
                        formats: {},
                        image_advtab: true,
                        toolbar1: 'undo redo | fontselect fontsizeselect | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | preview | forecolor | codesample | fullscreen',
                        content_css: [
//                            '//www.tinymce.com/css/codepen.min.css',
                            'resouces/css/tiny.css'
//                           
                        ]
                    });
//                    
                });
            });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // with sorting , no confilcts with plugin

            function resetaddfields() {
                $('#termsidadd').val("");
                $('#descriptionaddmethod').val("");
                tinyMCE.get('descriptionaddmethod').setContent('', {format: 'html'});
                $('#versionnoadd').val("");
                $('#statusadd').val("");
//                $('#amessage').text("");
            }

            // terms id onchange             
            function emptydiv() {
                $('#amessage').text("");
            }

            function submitformnormal() {
                var termsidadd = $("#termsidadd").val();
                var versionnoadd = $("#versionnoadd").val();
                var statusadd = $("#statusadd").val();
                var descriptionadd_hidden = tinyMCE.get('descriptionaddmethod').getContent({format: 'html'});

                $.ajax({
                    url: '${pageContext.request.contextPath}/AddnewTerms',
                    data: {termsidadd: termsidadd,
                        versionnoadd: versionnoadd,
                        statusadd: statusadd,
                        descriptionadd: descriptionadd_hidden
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var msg = data.message;
                        if (msg == null) {
                            document.getElementById("amessage").innerHTML = "<div class='ui-widget actionMessage'><div class='ui-state-highlight ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>Terms and conditions added successfully</span></p></div></div></div>";
                            resetaddfields();
                        } else {
                            document.getElementById("amessage").innerHTML = "<div class='ui-widget actionError'><div class='ui-state-error ui-corner-all' style='padding: 0.3em 0.7em; margin-top: 20px;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: 0.3em;'></span><span>" + msg + "</span></p></div></div></div>";
                        }
                    },
                    error: function () {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }


                });
            }


        </script>
        <title></title>
    </head>
    <body onload="onlooad()">




        <%--<s:hidden name="statusAct" id="statusAct"></s:hidden>--%>
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
                            <s:set id="vupdatelink" var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                            <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                            <s:set id="vdelete" var="vdelete"><s:property value="vdelete" default="true"/></s:set>

                                <div id="formstyle">

                                <s:form id="terms" method="post" action="Terms" theme="simple">

                                    <div class="row row_1">
                                        <div class="col-sm-4">
                                            <div class="form-group form-inline">
                                                <span style="color: red">*</span><label>Terms & Conditions Type</label>
                                                <s:select cssClass="form-control" id="termsid" name="termsid" 
                                                          list="%{TermtypeList}"  accesskey="" 
                                                          headerValue="--Select Terms & Conditions Type--"  headerKey=""  
                                                          listKey="key" listValue="value"
                                                          onchange="enableVersionno(this.value)"      />  
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group form-inline" >
                                                <span style="color: red">*</span><label>Version Number</label>
                                                <s:select cssClass="form-control" id="versionno" name="versionno" 
                                                          list="versionMap"  
                                                          headerValue="--Select Version Number--"  headerKey=""  
                                                          listKey="key" listValue="value" disabled="true"
                                                          onchange="changeVersion(this.value)"      />  
                                            </div>

                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">    
                                                <span style="color: red">*</span><label>Status </label>
                                                <s:select  id="status" list="%{statusList}" 
                                                           headerValue="--Select Status--" headerKey="" name="status" 
                                                           listKey="statuscode" listValue="description" 
                                                           cssClass="form-control"  /> 
                                            </div>
                                        </div>    
                                    </div>
                                    <div class="row row_1">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>Description</label> 
                                                <div class="standalone-container">

                                                    <div  id="descriptionupmethod"  style=" height: 175px;"> </div>
                                                    <input type="hidden" name="description" id="description_hidden" />
                                                </div>

                                                <script type="text/javascript">
//                                                   
                                                    function set2() {
//                                                        document.getElementById("description_hidden").value = tinyMCE.activeEditor.getContent({format: 'html'});
                                                        document.getElementById("description_hidden").value = tinyMCE.get('descriptionupmethod').getContent({format: 'html'});

//                                                        console.log(document.getElementById("description_hidden").value);

                                                    }
                                                    function set() {
                                                        document.getElementById("descriptionadd_hidden").value = tinyMCE.get('descriptionaddmethod').getContent({format: 'html'});

//                                                        console.log(document.getElementById("descriptionadd_hidden").value);
                                                    }

                                                    function hideNetbeansGlassPane()
                                                    {
                                                        if (document.getElementById('netbeans_glasspane'))
                                                        {
                                                            document.getElementById('netbeans_glasspane').remove();
                                                            console.log("ss");
                                                        }
                                                    }
                                                    setTimeout(hideNetbeansGlassPane, 500);
//                                                   
                                                    tinymce.init({
                                                        selector: '#descriptionupmethod',
                                                        height: 250,
                                                        toolbar_items_size: 'small',
                                                        menubar: false,
//                                                        forced_root_block: '',
                                                        theme: 'modern',
                                                        plugins: ["advlist lists charmap print preview hr anchor pagebreak spellchecker",
                                                            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                                                            "table contextmenu directionality emoticons template textcolor fullpage textcolor colorpicker textpattern"
                                                        ],
                                                        formats: {},
                                                        toolbar1: 'undo redo | fontselect fontsizeselect | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | preview | forecolor | codesample | fullscreen',
                                                        content_css: [
//                                                            '//www.tinymce.com/css/codepen.min.css',
                                                            'resouces/css/tiny.css'

                                                        ]
                                                    });


                                                </script>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="row row_1">
                                        <div class="col-sm-4">
                                            <div class="form-group form-inline">
                                                <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row row_1"></div>
                                    <div class="row row_1 form-inline">
                                        <!--<div class="col-sm-2"></div>-->
                                        <div class="col-sm-8">
                                            <div class="form-group">
                                                <sj:submit button="true" 
                                                           value="Reset" 
                                                           name="reset" 
                                                           onClick="resetAllData()"
                                                           cssClass="form-control btn_normal"
                                                           cssStyle="border-radius: 12px;" />

                                            </div>
                                            <div class="form-group">
                                                <s:url var="updateurl" action="UpdateTerms"/>

                                                <sj:submit button="true" 
                                                           onclick="set2()"
                                                           href="%{updateurl}" 
                                                           disabled="#vupdatelink"
                                                           value="Update" 
                                                           targets="divmsg"   
                                                           id="updateButton"
                                                           cssClass="form-control btn_normal"
                                                           cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                           />

                                            </div>
                                            <div class="form-group">
                                                <sj:submit button="true" 
                                                           value="Delete" 
                                                           targets="divmsg"
                                                           disabled="#vdelete"
                                                           id="deleteButton"
                                                           onclick="deleteTermInit(this)"
                                                           cssClass="form-control btn_normal"
                                                           cssStyle="border-radius: 12px;background-color:#969595;color:white;" />

                                            </div>
                                        </div>
                                        <div class="col-sm-4 text-right">
                                            <div class="form-group">
                                                <%--<s:url var="addurl" action="ViewPopupTerms"/>--%>
                                                <!--<button value="Add New Terms"  disabled="#vadd" id="addButton" Class="form-control btn_normal" Style="border-radius: 12px;background-color:#969595;color:white;"></button>-->
                                                <sj:submit                                                      
                                                    button="true"
                                                    disabled="#vadd"
                                                    value="Add New Terms"
                                                    id="addButton"
                                                    onclick="resetAllDataAdd()"
                                                    cssClass="form-control btn_normal"
                                                    cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                                    />
                                            </div>
                                        </div>        
                                    </div>


                                </s:form>
                            </div>
                            <!-- Start delete confirm dialog box -->
                            <sj:dialog 
                                id="deletedialog" 
                                buttons="{ 
                                'OK':function() { deleteTerm($(this).data('term'),$(this).data('version'),$(this).data('status'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Delete Terms"                            
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
                    </div>
                </div>
            </div>
        </div>                            
        <!-- end: MAIN CONTAINER -->
        <!-- start: FOOTER -->
        <jsp:include page="/footer.jsp"/>
        <!-- end: FOOTER -->


        <!--////////////////////////////////////////////////-->

        <div id="dialogAdd" title="Add Terms" style="display: none; overflow: hidden">
            <s:div id="amessage">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>

            <s:form cssClass="form" id="termsadd" method="post" action="#" theme="simple" >
                <div class="row row_popup">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Terms & Conditions Type</label>
                            <s:select cssClass="form-control" id="termsidadd" name="termsidadd" 
                                      list="%{TermtypeList}"  accesskey="" 
                                      headerValue="--Select Terms & Conditions Type--"  headerKey=""  
                                      listKey="key" listValue="value"
                                      onchange="enableVersionno(this.value);"      />  
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <span style="color: red">*</span><label >Version Number </label>
                            <s:textfield name="versionnoadd" id="versionnoadd" cssClass="form-control"  maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 .]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 .]/g,''))"/> 
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">    
                            <span style="color: red">*</span><label>Status </label>
                            <s:select  id="statusadd" list="%{statusList}" 
                                       headerValue="--Select Status--" headerKey="" name="statusadd" 
                                       listKey="statuscode" listValue="description" disabled="false"  
                                       cssClass="form-control"  /> 
                        </div>
                    </div>     
                </div>
                <div class="row row_popup">
                    <div class="col-sm-12">

                        <div class="form-group">
                            <span style="color: red">*</span><label>Description</label> 
                            <div class="standalone-container">
                                <div  id="descriptionaddmethod" style=" height: 175px;"> </div>
                                <input type="hidden" name="descriptionadd" id="descriptionadd_hidden" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row row_popup form-inline">
                    <div class="col-sm-9">
                        <div class="form-group">
                            <span class="mandatoryfield">Mandatory fields are marked with *</span>
                        </div>
                    </div>
                    <div class="col-sm-3 text-right">
                        <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                            <sj:submit 
                                button="true" 
                                value="Reset"
                                id="canclebtn"
                                onClick="resetAllDataAdd()"
                                cssClass="btn btn-default btn-sm"
                                cssStyle="border-radius: 12px;"
                                />                          
                        </div>
                        <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                            <s:url action="AddTerms" var="inserturl"/>
                            <sj:submit
                                button="true"
                                value="Add"
                                onclick="set()"
                                href="%{inserturl}"
                                targets="amessage"
                                id="addbtn"
                                cssClass="btn btn-sm active" 
                                cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                />   

                            <!--                            <input 
                                                            type="button" 
                                                            onclick="submitformnormal()" 
                                                            value="Add Normal" 
                                                            class="btn btn-sm active"
                                                            style="border-radius: 12px;background-color:#969595;color:white;"
                                                            />-->
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
        <!--////////////////////////////////////////////////////-->


    </body>
</html>

