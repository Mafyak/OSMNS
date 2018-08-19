<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:bundle basename="resources.content">
    <fmt:message key="err.errorPage" var="errorPage"/>
    <fmt:message key="cmn.servletName" var="servletName"/>
    <fmt:message key="cmn.requestFrom" var="requestFrom"/>
    <fmt:message key="cmn.isFailed" var="isFailed"/>
    <fmt:message key="cmn.statusCode" var="statusCode"/>
    <fmt:message key="cmn.exception" var="exception"/>
    <fmt:message key="cmn.details" var="details"/>
    <fmt:message key="cmn.goBack" var="goBack"/>
</fmt:bundle>

<html>
<head>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
    <title>${errorPage}</title>
</head>
<body>
${requestFrom} ${pageContext.errorData.requestURI} ${isFailed} <br/>
${servletName}: ${pageContext.errorData.servletName} <br/>
${statusCode}: ${pageContext.errorData.statusCode} <br/>
${exception}: ${pageContext.errorData.throwable} <br/>
${details}: ${pageContext.errorData.throwable.printStackTrace()} <br/>
<button onclick="goBack()">${goBack}</button>
</body>
</html>
