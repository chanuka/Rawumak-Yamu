<%-- 
    Document   : msgTermsAdd
    Created on : Jan 20, 2017, 12:36:19 PM
    Author     : jayana_i
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>

<s:if test="hasActionMessages()">
    <script type="text/javascript">resetFieldData();
    clearQuill();
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


