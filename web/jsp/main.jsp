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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources.content"/>
<fmt:setLocale value="${locale}"/>
<jsp:useBean id="user" scope="application" class="by.epam.entity.User"/>
<jsp:useBean id="employee" scope="application" class="by.epam.entity.User"/>

<html>
<head>
    <title>Main page</title>
</head>
<body>

<p>Welcome, ${user.fName}!</p>
<p>
    <fmt:message key="company"/> :
    <c:if test="${not empty user.company}">
        ${user.company}
    </c:if>

    <c:if test="${empty user.company}">
    <a class="button" href="#popup1">Add company</a>
<div id="popup1" class="overlay">
    <div class="popup">
        <a class="close" href="#">&times;</a>
        <div class="content">
            <form id="addMyCompany" method="POST"
                  action="${pageContext.request.contextPath}/Controller?command=add_my_company"
                  style="display: block;">
                <input type="text" name="companyName" placeholder="Company Name" required>
                <input type="text" name="niche" placeholder="Niche" required>
                <input type="text" name="location" placeholder="Location" required>
                <input type="text" name="headcount" placeholder="Head count" required>
                <button type="submit" name="button" value="Add">Add</button>
            </form>
        </div>
    </div>
</div>
</c:if>
</p>
<a href="${pageContext.request.contextPath}/Controller?command=logout">Logout</a>
<hr>
<p>Search by SSN:</p>
<form id="getBySSN" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_by_ssn"
      style="display: block;">
    <input type="text" name="SSN" placeholder="SSN" required>
    <button type="submit" name="button" value="Search">Search me</button>
</form>
<c:if test="${not empty employee.fName}">
    <p>Result for: ${employee.lName}, ${employee.fName}</p>

<table style="width:50%" border="1px">
    <tr>
        <th>Company Name</th>
        <th>Year Employed</th>
        <th>Year Terminated</th>
        <th>Rating 1</th>
        <th>Rating 2</th>
        <th>Rating 3</th>
        <th>Rating 4</th>
        <th>Rating 5</th>
        <th>Hire again</th>
    </tr>
    <c:forEach items="${employee.history}" var="review">
        <tr>
            <td>${review.idCompany}</td>
            <td>${review.yearEmployed}</td>
            <td>${review.yearTerminated}</td>
            <td>${review.rating1}</td>
            <td>${review.rating2}</td>
            <td>${review.rating3}</td>
            <td>${review.rating4}</td>
            <td>${review.rating5}</td>
            <td>${review.hireAgain}</td>
        </tr>
    </c:forEach>

</table>


</c:if>
<c:if test="${empty employee.fName}"><p>Can't find an employee with such ssn.</p></c:if>
<hr>
Here is a list of services I need to add to hr page:<br/>
1. Delete user by name<br/>
2. Edit review<br/>
3. Show all reviews<br/>
4. Search by name and dob<br/>
5. Hire again field in search by SSN doesn't work properly<br/>
</body>
</html>