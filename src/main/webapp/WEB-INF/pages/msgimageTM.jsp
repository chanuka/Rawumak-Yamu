<%-- 
    Document   : msgimageTM
    Created on : Sep 26, 2016, 3:20:33 PM
    Author     : jayana_i
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
    <s:if test="hasActionMessages()">
        <script type="text/javascript">resetFieldData();</script>
        <s:actionmessage theme="jquery" id="successMsg"/>
    </s:if>
    <s:if test="hasActionErrors()">
        <script type="text/javascript">cancelDataTM();</script>
        <s:actionerror theme="jquery"/>
    </s:if>
