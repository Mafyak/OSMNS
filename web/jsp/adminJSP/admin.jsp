<%--
  Created by IntelliJ IDEA.
  User: WorkBase
  Date: 7/13/2018
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<p>Search and delete HR by name OR id:
<form id="getHrByName" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_hr_by_name"
      style="display: block;">
    <input type="text" class="form-control" name="fName" placeholder="First Name">
    <input type="text" class="form-control" name="lName" placeholder="Last Name">
    <input type="text" class="form-control" name="hrIdToSearch" placeholder="HR id" pattern="\d{1,}"
           title="Only digits">
    <button type="submit" class="btn" name="button" value="Search">Search</button>
</form>
</p>
<p>
    <c:if test="${not empty hrList}">
    Results for:
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

Show all reviews<br/>
<form id="showAllReviews" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_all_reviews&page=1"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowAllReviews">Show all reviews</button>
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
                    <a href="${pageContext.request.contextPath}/Controller?command=confirm_rating&ratingidtoconfirm=${review.ratingID}&confirmerid=${user.id}">
                        <input class="btn" type="button" name="select" value="Confirm"/></a></c:if>
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

Show unconfirmed reviews<br/>
<form id="showUnconfirmedReviews" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_unconfirmed_reviews"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowUnconfirmedReviews">Show Unconfirmed reviews</button>
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
                <td>${review.confirmed}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
${ShowUnconfReviewsError}

Show company name collisions<br/>
<form id="showCompanyNameCollisions" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_company_name_collisions"
      style="display: block;">
    <button type="submit" class="btn" name="button" value="ShowCompanyNameCollisions">Show company collisions</button>
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
                <td>${company.companyInnerId}</td>
                <td>${company.name}</td>
                <td>${company.niche}</td>
                <td>${company.location}</td>
                <td>${company.headcount}</td>
                <td>${company.companyOfficialId}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=merge_company&companyIdtoMerge=${company.companyInnerId}">
                        <input class="btn" type="button" name="select" value="UseAsBase"/>
                    </a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=remove_company&companyIdtoRemove=${company.companyInnerId}">
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
