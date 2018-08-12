<%--
  Created by IntelliJ IDEA.
  User: WorkBase
  Date: 7/13/2018
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="resources.content">
    <fmt:message key="adm.search_delete_hr" var="Search_and_del_hr"/>
    <fmt:message key="adm.results_for" var="results"/>
    <fmt:message key="adm.show_all_revs" var="show_all_revs"/>
    <fmt:message key="adm.show_unconf_revs" var="show_unconf_revs"/>
    <fmt:message key="adm.show_collisions" var="show_collisions"/>
    <fmt:message key="cmn.fName" var="fName"/>
    <fmt:message key="cmn.lName" var="lName"/>
    <fmt:message key="cmn.search" var="search"/>
</fmt:bundle>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#hideHrSearch").click(function () {
                $("#contToHideHrSearch").hide();
            });
        });
    </script>

    <title>Title</title>
</head>
<body>

<jsp:include page="../pageParts/header.jsp"/>
<p>${Search_and_del_hr}:
<form id="getHrByName" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_hr_by_name"
      style="display: block;">
    <input type="text" class="form-control" name="fName" placeholder="${fName}">
    <input type="text" class="form-control" name="lName" placeholder="${lName}">
    <input type="text" class="form-control" name="hrIdToSearch" placeholder="HR id" pattern="\d{1,}"
           title="Only digits">
    <button type="submit" class="btn" name="button" value="Search">${search}</button>
</form>
</p>
<p>
    <c:if test="${not empty hrList}">
    ${results}:
<table id="table" style="width:50%" border="1px">
    <tr>
        <th>HR id</th>
        <th>First Name</th>
        <th>Middle Name</th>
        <th>Last Name</th>
        <th>Current Company</th>
        <th>Remove</th>
    </tr>
    <c:forEach items="${hrList}" var="hr">
        <tr>
            <td>${hr.id}</td>
            <td>${hr.fName}</td>
            <td>${hr.mName}</td>
            <td>${hr.lName}</td>
            <td>${hr.company.name}</td>
            <td><a href="${pageContext.request.contextPath}/Controller?command=remove_hr&hrIdToRemove=${hr.id}">
                <input type="button" name="select" class="btn" value="Remove"/></a></td>
        </tr>
    </c:forEach>
</table>
<br/>
</c:if>
${infoSearcdDelHRMessage}<br/>
</p>
<hr/>

${show_all_revs}<br/>
<form id="showAllReviews" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_all_reviews&page=1"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowAllReviews">${show_all_revs}</button>
</form>
<c:if test="${not empty reviewsList}">
    <p id="contToHideHrSearch">
        <button class="btn" id="hideHrSearch">Hide Results</button>
        All reviews:
    <table style="width:50%" border="1px">
        <tr>
            <th>Rating id</th>
            <th>Company id</th>
            <th>HR id</th>
            <th>Employee id</th>
            <th>Year Employed</th>
            <th>Year Terminated</th>
            <th>Rating 1</th>
            <th>Rating 2</th>
            <th>Rating 3</th>
            <th>Rating 4</th>
            <th>Rating 5</th>
            <th>Hire again</th>
            <th>Confirmed</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${reviewsList}" var="review">
            <tr>
                <td>${review.ratingID}</td>
                <td>${review.idCompany}</td>
                <td>${review.idHR}</td>
                <td>${review.idEmployee}</td>
                <td>${review.yearEmployed}</td>
                <td>${review.yearTerminated}</td>
                <td>${review.rating1}</td>
                <td>${review.rating2}</td>
                <td>${review.rating3}</td>
                <td>${review.rating4}</td>
                <td>${review.rating5}</td>
                <td>${review.hireAgain}</td>
                <td><c:if test="${review.confirmed==0}">
                    Not confirmed</c:if>
                    <c:if test="${review.confirmed>0}">${review.confirmed}</c:if></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=delete_rating&ratingidtodelete=${review.ratingID}">
                        <input class="btn" type="button" name="select" value="Delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <c:forEach var="i" step="1" begin="1" end="${pages}">
        <a href="${pageContext.request.contextPath}/Controller?command=show_all_reviews&page=${i}">
            <input class="btn" type="button" name="page" value="${i}"/></a>
    </c:forEach>
    </p>
    <br/>
</c:if>
${ShowAllReviewsError}

${show_unconf_revs}<br/>
<form id="showUnconfirmedReviews" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_unconfirmed_reviews"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowUnconfirmedReviews">${show_unconf_revs}</button>
</form>
<c:if test="${not empty unconfirmedReviewsList}">
    <p>All reviews:</p>
    <table style="width:50%" border="1px">
        <tr>
            <th>Company id</th>
            <th>HR id</th>
            <th>Employee id</th>
            <th>Year Employed</th>
            <th>Year Terminated</th>
            <th>Rating 1</th>
            <th>Rating 2</th>
            <th>Rating 3</th>
            <th>Rating 4</th>
            <th>Rating 5</th>
            <th>Hire again</th>
            <th>Confirmed</th>
        </tr>
        <c:forEach items="${unconfirmedReviewsList}" var="review">
            <tr>
                <td>${review.idCompany}</td>
                <td>${review.idHR}</td>
                <td>${review.idEmployee}</td>
                <td>${review.yearEmployed}</td>
                <td>${review.yearTerminated}</td>
                <td>${review.rating1}</td>
                <td>${review.rating2}</td>
                <td>${review.rating3}</td>
                <td>${review.rating4}</td>
                <td>${review.rating5}</td>
                <td>${review.hireAgain}</td>
                <td><a href="${pageContext.request.contextPath}/Controller?command=confirm_rating&ratingidtoconfirm=${review.ratingID}&confirmerid=${user.id}">
                        <input class="btn" type="button" name="select" value="Confirm"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
${ShowUnconfReviewsError}

${show_collisions}<br/>
<form id="showCompanyNameCollisions" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_company_name_collisions"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowCompanyNameCollisions">${show_collisions}</button>
</form>
<c:if test="${not empty companyNameCollisions}">
    <p>Company list:</p>
    <table style="width:50%" border="1px">
        <tr>
            <th>Company id</th>
            <th>Company name</th>
            <th>Niche</th>
            <th>Location</th>
            <th>Headcount</th>
            <th>Company Tax Id</th>
            <th>Merge</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${companyNameCollisions}" var="company">
            <tr>
                <td>${company.id}</td>
                <td>${company.name}</td>
                <td>${company.niche}</td>
                <td>${company.location}</td>
                <td>${company.headcount}</td>
                <td>${company.companyOfficialId}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=merge_company&companyIdtoMerge=${company.id}">
                        <input class="btn" type="button" name="select" value="UseAsBase"/>
                    </a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=remove_company&companyIdtoRemove=${company.id}">
                        <input class="btn" type="button" name="select" value="Remove"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
${ShowCompanyNameCollisionssError}

Here is a list of services I need to add to admin page:<br/>
3. Show all users<br/>

</body>
</html>
