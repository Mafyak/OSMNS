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
    <fmt:message key="cmn.title" var="title"/>
    <fmt:message key="cmn.digitsOnly" var="digitsOnly"/>
    <fmt:message key="company" var="cСompany"/>
    <fmt:message key="cmn.fName" var="cmnFName"/>
    <fmt:message key="cmn.lName" var="cmnLName"/>
    <fmt:message key="cmn.mName" var="empMName"/>
    <fmt:message key="cmn.hrid" var="hrIdLang"/>
    <fmt:message key="cmn.remove" var="remove"/>
    <fmt:message key="rev.rating1" var="rating1"/>
    <fmt:message key="rev.rating2" var="rating2"/>
    <fmt:message key="rev.rating3" var="rating3"/>
    <fmt:message key="rev.rating4" var="rating4"/>
    <fmt:message key="rev.rating5" var="rating5"/>
    <fmt:message key="rev.yearFired" var="yearFired"/>
    <fmt:message key="rev.yearEmpld" var="yearEmpld"/>
    <fmt:message key="hr.hire_again" var="hire_again"/>
    <fmt:message key="cmn.hideResults" var="hideResults"/>
    <fmt:message key="cmn.allReviews" var="allReviews"/>
    <fmt:message key="cmn.ratingID" var="ratingID"/>
    <fmt:message key="cmn.companyID" var="companyID"/>
    <fmt:message key="cmn.employeeID" var="employeeID"/>
    <fmt:message key="cmn.confirmed" var="confirmed"/>
    <fmt:message key="cmn.not.confirmed" var="notConfirmed"/>
    <fmt:message key="comp.loc" var="cLoc"/>
    <fmt:message key="comp.taxID" var="cTaxId"/>
    <fmt:message key="adm.companyList" var="companyList"/>
    <fmt:message key="comp.niche" var="niche"/>
    <fmt:message key="comp.headcount" var="headcount"/>
    <fmt:message key="cmn.merge" var="merge"/>
    <fmt:message key="cmn.useAsBase" var="useAsBase"/>
    <fmt:message key="cmn.confirm" var="confirm"/>
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

    <title>${title}</title>
</head>
<body>

<jsp:include page="../pageParts/header.jsp"/>
<p>${Search_and_del_hr}:
<form id="getHrByName" method="POST" action="${pageContext.request.contextPath}/Controller?command=show_hr_by_name"
      style="display: block;">
    <input type="text" class="form-control" name="fName" placeholder="${fName}">
    <input type="text" class="form-control" name="lName" placeholder="${lName}">
    <input type="text" class="form-control" name="hrIdToSearch" placeholder="${hrIdLang}" pattern="\d{1,}"
           title="${digitsOnly}">
    <button type="submit" class="btn" name="button" value="Search">${search}</button>
</form>
</p>
<p>
    <c:if test="${not empty hrList}">
    ${results}:
<table id="table" style="width:50%" border="1px">
    <tr>
        <th>${hrIdLang}</th>
        <th>${cmnFName}</th>
        <th>${empMName}</th>
        <th>${cmnLName}</th>
        <th>${cСompany}</th>
        <th>${remove}</th>
    </tr>
    <c:forEach items="${hrList}" var="hr">
        <tr>
            <td>${hr.id}</td>
            <td>${hr.fName}</td>
            <td>${hr.mName}</td>
            <td>${hr.lName}</td>
            <td>${hr.company.name}</td>
            <td><a href="${pageContext.request.contextPath}/Controller?command=remove_hr&hrIdToRemove=${hr.id}">
                <input type="button" name="select" class="btn" value="${remove}"/></a></td>
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
        <button class="btn" id="hideHrSearch">${hideResults}</button>
        ${allReviews}:
    <table style="width:50%" border="1px">
        <tr>
            <th>${ratingID}</th>
            <th>${companyID}</th>
            <th>${hrIdLang}</th>
            <th>${employeeID}</th>
            <th>${yearEmpld}</th>
            <th>${yearFired}</th>
            <th>${rating1}</th>
            <th>${rating2}</th>
            <th>${rating3}</th>
            <th>${rating4}</th>
            <th>${rating5}</th>
            <th>${hire_again}</th>
            <th>${confirmed}</th>
            <th>${remove}</th>
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
                    ${notConfirmed}</c:if>
                    <c:if test="${review.confirmed>0}">${review.confirmed}</c:if></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=delete_rating&ratingidtodelete=${review.ratingID}">
                        <input class="btn" type="button" name="select" value="${remove}"/></a></td>
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
    <p>${allReviews}:</p>
    <table style="width:50%" border="1px">
        <tr>
            <th>${companyID}</th>
            <th>${hrIdLang}</th>
            <th>${employeeID}</th>
            <th>${yearEmpld}</th>
            <th>${yearFired}</th>
            <th>${rating1}</th>
            <th>${rating2}</th>
            <th>${rating3}</th>
            <th>${rating4}</th>
            <th>${rating5}</th>
            <th>${hire_again}</th>
            <th>${confirmed}</th>
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
                        <input class="btn" type="button" name="select" value="${confirm}"/></a></td>
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
    <p>${companyList}:</p>
    <table style="width:50%" border="1px">
        <tr>
            <th>${companyID}</th>
            <th>${cСompany}</th>
            <th>${niche}</th>
            <th>${cLoc}</th>
            <th>${headcount}</th>
            <th>${cTaxId}</th>
            <th>${merge}</th>
            <th>${remove}</th>
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
                        <input class="btn" type="button" name="select" value="${useAsBase}"/>
                    </a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=remove_company&companyIdtoRemove=${company.id}">
                        <input class="btn" type="button" name="select" value="${remove}"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:if>
${ShowCompanyNameCollisionssError}
</body>
</html>
