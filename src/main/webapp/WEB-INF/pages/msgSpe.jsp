<%-- 
    Document   : msgSpe
    Created on : Jun 3, 2014, 10:39:19 AM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>

<s:if test="hasActionMessages()">
    <script type="text/javascript">resetFieldData();
//    jyn();
    </script>
    <s:actionmessage theme="jquery"/>
</s:if>
    
<s:if test="hasActionErrors()">
    <script type="text/javascript"> 
        var code = '<s:property value="actionErrors"/>';        
    </script>
    <s:set var="foo"><s:property value="actionErrors"/></s:set>
    <s:if test="#foo.indexOf('$') == -1">
        <s:actionerror theme="jquery"/>
    </s:if>
    <s:else>
        <script type="text/javascript">    
            var code = code.replace(/^\[\\$|\]$/g, "");
            duplicatedadd(code);
        </script>
    </s:else>
</s:if>


