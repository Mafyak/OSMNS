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
<jsp:useBean id="user" scope="application" class="by.epam.entity.User"/>
<jsp:useBean id="employee" scope="application" class="by.epam.entity.User"/>

<fmt:bundle basename="resources.content">
    <fmt:message key="main.welcome" var="Welcome"/>
    <fmt:message key="main.search_by_SSN" var="Search_by_SSN"/>
    <fmt:message key="main.add_new_review" var="Add_New_Review"/>
    <fmt:message key="main.add" var="Add"/>
    <fmt:message key="main.your_company" var="Your_company"/>
</fmt:bundle>
<fmt:setLocale value="${locale}"/>

<html>
<head>
    <title>Main page</title>
    <link href="/jsp/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="/jsp/css/style.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../header.jsp" />
<p>
    ${Your_company} :
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
                <input name="companyName" placeholder="Company Name" required>
                <input name="niche" placeholder="Niche" required>
                <input name="location" placeholder="Location" required>
                <input name="headcount" placeholder="Head count" required>
                <input name="companyOffId" placeholder="Company Registration Id" required>
                <button name="button" value="Add">${Add}</button>
            </form>
        </div>
    </div>
</div>
</c:if>
</p>
<hr>
<p>${Search_by_SSN}:
<form id="getBySSN" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_by_ssn"
      style="display: block;">
    <input name="SSN" placeholder="SSN" required>
    <button name="button" value="Search">Search me</button>
</form>
${errorShowBySsnMessage}
</p>
<hr>
<p>${Add_New_Review}:
<div class="row">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-login">
            <div class="panel-heading">
                <div class="row">
                    <form id="add-review" method="POST"
                          action="${pageContext.request.contextPath}/Controller?command=add_review">
                        <div class="form-group">
                            <input name="fName" class="form-control" placeholder="First Name" required>
                            <input name="lName" class="form-control" placeholder="Last Name" required>
                            <input name="SSN" class="form-control" placeholder="Social Security Number" pattern="[0-9]+"
                                   title="Number only" required>
                            <input name="cName" class="form-control" placeholder="Company Name" required>
                            <input name="cId" class="form-control" placeholder="Company ID" required>
                            <input name="yEmployed" class="form-control" placeholder="Year Employed" required>
                            <input name="yFired" class="form-control" placeholder="Year Fired" required>
                        </div>
                        <div class="form-group">
                            <input name="rating1" class="form-control" placeholder="Rating1" required>
                            <input name="rating2" class="form-control" placeholder="Rating2" required>
                            <input name="rating3" class="form-control" placeholder="Rating3" required>
                            <input name="rating4" class="form-control" placeholder="Rating4" required>
                            <input name="rating5" class="form-control" placeholder="Rating5" required>
                        </div>
                        <div class="form-group">
                            <input name="hireAgain" class="form-control" placeholder="Hire again (Yes or No)"
                                   required>
                        </div>
                        <input type="submit" name="command"
                               class="form-control btn btn-register" value="${Add}">
                    </form>
                    ${reviewAddResult}
                </div>
            </div>
        </div>
    </div>
</div>
</p>


<hr>
<p>
    Here is a list of services I need to add to hr page:<br/>
    2. Show all reviews<br/>
    3. Edit review<br/>
    4. Search by name and dob<br/>
    5. Hire again field in search by SSN doesn't work properly<br/>
</p>
</body>
</html>