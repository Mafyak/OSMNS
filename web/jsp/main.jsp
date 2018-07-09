<%--
  Created by IntelliJ IDEA.
  User: WorkBase
  Date: 6/30/2018
  Time: 3:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="/jsp/css/style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main page</title>
</head>
<body>


<p>Welcome, ${user.fName}!</p>
<p>Your current company:
    <c:if test="${not empty user.company}">
        ${user.company}
    </c:if>
    <c:if test="${empty user.company}">
            <a class="button" href="#popup1">Add company</a>
        <div id="popup1" class="overlay">
            <div class="popup">
                <a class="close" href="#">&times;</a>
                <div class="content">
                    Space for company add command.
                </div>
            </div>
        </div>
</c:if>
</p>
<a href="${pageContext.request.contextPath}/Controller?command=logout">Logout</a>
<hr>
<p>Search by SSN:</p>
<hr>
<p>Search by name and dob:</p>
<hr>
<p>List of employees added by you:</p>
<p> LIST </p>
<hr>
<p>List of employees added by you:</p>
<p> LIST </p>

</body>
</html>
