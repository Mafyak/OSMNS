<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" scope="application" class="by.epam.entity.User"/>
<jsp:useBean id="employee" scope="application" class="by.epam.entity.User"/>

<fmt:bundle basename="resources.content">
    <fmt:message key="main.welcome" var="Welcome"/>
    <fmt:message key="main.search_by_SSN" var="Search_by_SSN"/>
    <fmt:message key="main.add_new_review" var="Add_New_Review"/>
    <fmt:message key="main.Add_new_employee" var="Add_new_employee"/>
    <fmt:message key="main.search" var="Search_simple"/>
    <fmt:message key="main.your_company" var="Your_company"/>
    <fmt:message key="rev.rating1" var="rating1"/>
    <fmt:message key="rev.rating2" var="rating2"/>
    <fmt:message key="rev.rating3" var="rating3"/>
    <fmt:message key="rev.rating4" var="rating4"/>
    <fmt:message key="rev.rating5" var="rating5"/>
    <fmt:message key="rev.rate_employee" var="rate_employee"/>
    <fmt:message key="hr.lookup_emp" var="lookup_emp"/>
    <fmt:message key="hr.lookup_emp" var="lookup_emp"/>
    <fmt:message key="hr.hire_again" var="hire_again"/>
    <fmt:message key="cmn.fName" var="empFName"/>
    <fmt:message key="cmn.lName" var="empLName"/>
    <fmt:message key="cmn.mName" var="empMName"/>
    <fmt:message key="cmn.ssn" var="ssn"/>
</fmt:bundle>
<fmt:setLocale value="${locale}"/>


<html>
<head>
    <title>Main page</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../pageParts/header.jsp"/>

<p>${Add_new_employee}:
<div class="panel panel-login">
    <div class="panel-heading">
        <form id="add-employee" method="POST"
              action="${pageContext.request.contextPath}/Controller?command=add_employee">
            <div class="row">
                <div class="form-group">
                        <input name="empFName" class="form-control" placeholder="${empFName}" required>
                        <input name="empMName" class="form-control" placeholder="${empMName}" required>
                        <input name="empLName" class="form-control" placeholder="${empLName}" required>
                        <input name="empSSN" class="form-control" placeholder="${ssn}" required>
                    <input type="submit" name="command" class="btn" value="${Add_new_employee}">
                </div>
                ${infoAddingEmpl}
            </div>
        </form>

    </div>
</div>
</p>




<p>${Add_New_Review}:
<div class="panel panel-login">
    <div class="panel-heading">



        <form id="find-employee" method="POST"
                             action="${pageContext.request.contextPath}/Controller?command=find_employee">
        <div class="row">
            <div class="form-group">
                ${lookup_emp}:
                <input name="SSN" class="form-control" placeholder="${ssn}" pattern="[0-9]+"
                       title="Number only" required>
                <input type="submit" name="command" class="btn" value="${Search_simple}">
            </div>
        </div>
    </form>

        <c:if test="${emptyDataFlag}">
            <div class="col-md-8 col-md-offset-4">
                <table class="table" style="width:50%" border="1px">
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>SSN</th>
                    </tr>
                    <tr>
                        <td>${employee.fName}</td>
                        <td>${employee.lName}</td>
                        <td>${employee.SSN}</td>
                    </tr>
                </table>
            </div>
        </c:if>
        <hr/>
        <br/>
        <div class="row">
            <form id="add-review" method="POST"
                  action="${pageContext.request.contextPath}/Controller?command=add_review">
                <div class="form-group">
                    <input name="yEmployed" class="form-control" placeholder="Year Employed" required>
                    <input name="yFired" class="form-control" placeholder="Year Fired" required>
                </div>
                ${rate_employee}:
                <div class="form-group">
                    <input name="rating1" class="form-control" placeholder="${rating1}" required>
                    <input name="rating2" class="form-control" placeholder="${rating2}" required>
                    <input name="rating3" class="form-control" placeholder="${rating3}" required>
                    <input name="rating4" class="form-control" placeholder="${rating4}" required>
                    <input name="rating5" class="form-control" placeholder="${rating5}" required>
                </div>
                <div class="form-group">
                    <input name="hireAgain" class="form-control" placeholder="${hire_again}? (Yes or No)" required>
                    <input type="submit" name="command" class="btn" value="${Add_New_Review}">
                </div>
                <div class="form-group">
                    ${reviewAddResult}
                </div>
            </form>
        </div>
    </div>
</div>
<br/>
<hr/>
<br/>
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