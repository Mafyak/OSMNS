<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="custag" uri="CustomTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="resources.content">
    <fmt:message key="main.welcome" var="Welcome"/>
    <fmt:message key="main.search_by_SSN" var="Search_by_SSN"/>
    <fmt:message key="main.add" var="Add"/>
    <fmt:message key="main.your_company" var="Your_company"/>
    <fmt:message key="cmn.ssn" var="ssn"/>
    <fmt:message key="cmn.search" var="search"/>
    <fmt:message key="rev.rating1" var="rating1"/>
    <fmt:message key="rev.rating2" var="rating2"/>
    <fmt:message key="rev.rating3" var="rating3"/>
    <fmt:message key="rev.rating4" var="rating4"/>
    <fmt:message key="rev.rating5" var="rating5"/>
    <fmt:message key="rev.yearFired" var="yearFired"/>
    <fmt:message key="rev.yearEmpld" var="yearEmpld"/>
    <fmt:message key="rev.rate_employee" var="rate_employee"/>
    <fmt:message key="hr.trust.rate" var="trustRate"/>
    <fmt:message key="company" var="cСompany"/>
    <fmt:message key="hr.hire_again" var="hire_again"/>
</fmt:bundle>


<html>
<head>
    <title>Employee Profile page.</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/sorttable.js"></script>
</head>
<body>
<jsp:include page="../pageParts/header.jsp"/>
<br/>
<p>${Search_by_SSN}:
<form id="getBySSN" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_by_ssn"
      style="display: block;">
    <input type="text" name="SSN" class="form-control" placeholder="${ssn}" pattern="\d+" title="Digits only" required>
    <button type="submit" name="button" class="btn"  value="Search">${search}</button>
</form>
<hr/>

<c:if test="${not empty employee.fName}">
    <p>Result for: ${employee.lName}, ${employee.fName}</p>
   <!-- <table class="table" class="sortable" style="width:50%" border="1px"> -->
        <table class="sortable" style="width:50%" border="1px">
        <tr>
            <th>${cСompany}</th>
            <th>${yearEmpld}</th>
            <th>${yearFired}</th>
            <th>${rating1}</th>
            <th>${rating2}</th>
            <th>${rating3}</th>
            <th>${rating4}</th>
            <th>${rating5}</th>
            <th>${hire_again}</th>
            <th>${trustRate}</th>
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
                <td><c:if test="${review.hireAgain eq 1}">Yes</c:if>
                    <c:if test="${review.hireAgain eq 0}">No</c:if></td>
                <td><custag:trust-rate-tag hrID="${review.idHR}"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
${noDataPerSSN}
</body>
</html>
