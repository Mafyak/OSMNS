<%--
  Created by IntelliJ IDEA.
  User: WorkBase
  Date: 6/30/2018
  Time: 3:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name or type: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Exception: ${pageContext.errorData.throwable} <br/>
Details: ${pageContext.errorData.throwable.printStackTrace()} <br/>
Go back to <a href="http://80.211.75.20:8080/OSMNS/">login page</a>
</body>
</html>
