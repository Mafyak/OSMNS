<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="custag" uri="CustomTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resources.content">
    <fmt:message key="common.welcome_to" var="Welcome_to"/>
    <fmt:message key="main.logout" var="Logout"/>
    <fmt:message key="main.add" var="Add"/>
    <fmt:message key="header.main_page" var="main_page"/>
    <fmt:message key="hr.search_by_ssn" var="search_by_ssn"/>
    <fmt:message key="hr.add_company" var="add_comp"/>
    <fmt:message key="cmn.settings" var="mySettings"/>
</fmt:bundle>
<html>
<div class="col-md-12">
    <div class="panel panel-login">
        <div class="panel-heading">
            <div class="row">
                <c:if test="${user.type eq 'HR'}">
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/Controller?command=goTo&page=hr_main_page">${main_page}</a>
                    </div>
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/Controller?command=goTo&page=path.page.emplProfile">${search_by_ssn}</a>
                    </div>
                    <div class="col-xs-2 mr-sm-2">
                        <a href="${pageContext.request.contextPath}/Controller?command=goTo&page=settings_page">${mySettings}</a>
                    </div>
                </c:if>

                <c:if test="${user.type eq 'ADMIN'}">
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/Controller?command=goTo&page=admin_page">${main_page}</a>
                    </div>
                </c:if>


                <div class="col-xs-2 mr-sm-2">
                    <a href="${pageContext.request.contextPath}/Controller?command=logout">${Logout}</a>
                </div>
                <div class="col-xs-2">
                    <a href="${pageContext.request.contextPath}/Controller?command=change_language&lang=en"><img
                            id="change_lang_en"
                            src="${pageContext.request.contextPath}/jsp/img/empty_pix.gif"></a>
                    <a href="${pageContext.request.contextPath}/Controller?command=change_language&lang=ru"><img
                            id="change_lang_ru"
                            src="${pageContext.request.contextPath}/jsp/img/empty_pix.gif"></a>
                </div>
            </div>
        </div>
    </div>
</div>
${Welcome_to}<custag:welcome-message userType="${user.type}"/>, ${user.fName}<br/>
<c:if test="${user.type eq 'HR'}">
    <c:if test="${not empty user.company}">
        ${user.company.name}
    </c:if>
    <c:if test="${empty user.company.name}">
        <a href="#popup1">
            <button type="submit" class="btn">${add_comp}</button>
        </a>

        <div id="popup1" class="overlay">
            <div class="popup">
                <a class="close" href="#">&times;</a>
                <div class="content">
                    <form id="addMyCompany" method="POST"
                          action="${pageContext.request.contextPath}/Controller?command=add_my_company"
                          style="display: block;">
                        <input name="companyName" class="form-control" placeholder="Company Name" required>
                        <input name="niche" class="form-control" placeholder="Niche" required>
                        <input name="location" class="form-control" placeholder="Location" required>
                        <input name="headcount" class="form-control" placeholder="Head count" required>
                        <input name="companyOffId" class="form-control" placeholder="Company Registration Id"
                               pattern="\d{8}" title="Must be 8 digits long" required>
                        <button name="button" class="btn" value="Add">${Add}</button>
                    </form>
                        ${addMyCompanyError}
                </div>
            </div>
        </div>
    </c:if>
</c:if>
<hr/>
</html>