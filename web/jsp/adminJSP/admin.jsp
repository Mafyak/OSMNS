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
    <title>Title</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../css/style.css" rel="stylesheet"/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

<jsp:include page="../header.jsp"/>
<br/>
<br/>
Here is a list of services I need to add to admin page:<br/>
<p>Search and delete HR by name:
<form id="getHrByName" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_hr_by_name"
      style="display: block;">
    <input name="fName" placeholder="First Name" required>
    <input name="lName" placeholder="Last Name" required>
    <button name="button" value="Search">Search</button>
</form>
</p>
<c:if test="${not empty hrList}">
    <p>Results for:</p>
    <table style="width:50%" border="1px">
        <tr>
            <th>HR id</th>
            <th>First Name</th>
            <th>Middle Name</th>
            <th>Last Name</th>
            <th>Current Company</th>
        </tr>
        <c:forEach items="${hrList}" var="hr">
            <tr>
                <td>${hr.id}</td>
                <td>${hr.fName}</td>
                <td>${hr.mName}</td>
                <td>${hr.lName}</td>
                <td>${hr.company}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
<c:if test="${empty hrList}"><p>Can't find a HR with these first and last names.<br/>${infoMessage}</p></c:if>

Show all reviews<br/>
<form id="showAllReviews" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_all_reviews"
      style="display: block;">
    <button name="button" value="ShowAllReviews">Show all reviews</button>
</form>
<c:if test="${not empty reviewsList}">
    <p>All reviews:</p>
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
                        <input type="button" name="select" value="Confirm"/></a></c:if>
                    <c:if test="${review.confirmed>0}">${review.confirmed}</c:if></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=delete_rating&ratingidtodelete=${review.ratingID}">
                        <input type="button" name="select" value="Delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
<c:if test="${empty reviewsList}"><p>Can't get reviews Please contact a developer.<br/>${infoMessage}</p></c:if>

Show unconfirmed reviews<br/>
<form id="showUnconfirmedReviews" method="POST"
      action="${pageContext.request.contextPath}/Controller?command=show_unconfirmed_reviews"
      style="display: block;">
    <button name="button" value="ShowUnconfirmedReviews">Show all reviews</button>
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
<c:if test="${empty unconfirmedReviewsList}"><p>Can't get reviews Please contact a developer.<br/>${infoMessage}
</p></c:if>

3. Show all users<br/>

</body>
</html>
