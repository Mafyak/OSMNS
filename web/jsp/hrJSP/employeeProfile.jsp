<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="custag" uri="CustomTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="resources.content">
    <fmt:message key="main.welcome" var="Welcome"/>
    <fmt:message key="main.search_by_SSN" var="Search_by_SSN"/>
    <fmt:message key="main.add" var="Add"/>
    <fmt:message key="main.your_company" var="Your_company"/>
</fmt:bundle>


<html>
<head>
    <title>Employee Profile page.</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../pageParts/header.jsp"/>
<br/>
<p>${Search_by_SSN}:
<form id="getBySSN" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_by_ssn"
      style="display: block;">
    <input type="text" name="SSN" class="form-control" placeholder="SSN" required>
    <button type="submit" name="button" class="btn" value="Search">Search me</button>
</form>
<hr/>

<c:if test="${not empty employee.fName}">
    <p>Result for: ${employee.lName}, ${employee.fName}</p>
    <table class="table" style="width:50%" border="1px">
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
            <th>HR trust rate</th>
        </tr>
        <c:forEach items="${employee.history}" var="review">
            <tr>
                <td>${review.company}</td>
                <td>${review.yearEmployed}</td>
                <td>${review.yearTerminated}</td>
                <td>${review.rating1}</td>
                <td>${review.rating2}</td>
                <td>${review.rating3}</td>
                <td>${review.rating4}</td>
                <td>${review.rating5}</td>
                <td>${review.hireAgain}</td>
                <td><custag:trust-rate-tag hrID="${review.idHR}"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
${noDataPerSSN}
</body>
</html>
