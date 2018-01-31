<%-- 
    Document   : msgcsvupload
    Created on : Sep 13, 2016, 4:39:44 PM
    Author     : jayana_i
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
    <s:if test="hasActionMessages()">
        <script type="text/javascript">
//            resetFieldData();
            resetCSV();
        </script>
        <!--<script type="text/javascript">resetCSV();</script>-->
        <s:actionmessage theme="jquery"/>
    </s:if>
    <s:if test="hasActionErrors()">
        <script type="text/javascript">resetCSV();</script>
        <s:actionerror theme="jquery"/>
    </s:if>

