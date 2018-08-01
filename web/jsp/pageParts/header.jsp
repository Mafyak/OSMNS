<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="custag" uri="CustomTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resources.content">
    <fmt:message key="common.welcome_to" var="Welcome_to"/>
    <fmt:message key="main.logout" var="Logout"/>
    <fmt:message key="main.add" var="Add"/>
    <fmt:message key="cmn.settings" var="mySettings"/>
</fmt:bundle>
<html>
<div class="col-md-12">
    <div class="panel panel-login">
        <div class="panel-heading">
            <div class="row">
                <c:if test="${user.type eq 'HR'}">
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/jsp/hrJSP/main.jsp">Main Page</a>
                    </div>
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/jsp/hrJSP/employeeProfile.jsp">Search by SSN</a>
                    </div>
                    <div class="col-xs-2 mr-sm-2">
                        <a href="${pageContext.request.contextPath}/jsp/common/mySettings.jsp">${mySettings}</a>
                    </div>
                </c:if>

                <c:if test="${user.type eq 'ADMIN'}">
                    <div class="col-xs-2">
                        <a href="${pageContext.request.contextPath}/jsp/adminJSP/admin.jsp">Main Page</a>
                    </div>
                </c:if>


                <div class="col-xs-2 mr-sm-2">
                    <a href="${pageContext.request.contextPath}/Controller?command=logout">${Logout}</a>
                </div>
                <div class="col-xs-2">
                    <a href="${pageContext.request.contextPath}/Controller?command=change_language&lang=en"><img id="change_lang_en"
                                                                                                                 src="${pageContext.request.contextPath}/jsp/img/empty_pix.gif"></a>
                    <a href="${pageContext.request.contextPath}/Controller?command=change_language&lang=ru"><img id="change_lang_ru"
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
        <a href="#popup1"><button type="submit" class="btn">Add company</button></a>

        <div id="popup1" class="overlay">
            <div class="popup">
                <a class="close" href="#">&times;</a>
                <div class="content">
                    <form id="addMyCompany" method="POST"
                          action="${pageContext.request.contextPath}/Controller?command=add_my_company"
                          style="display: block;">
                        <input name="companyName" class="form-control" placeholder="Company Name" required>
                        <input name="niche" class="form-control" placeholder="Niche" required>
                        <input name="location" class="form-control"placeholder="Location" required>
                        <input name="headcount" class="form-control"placeholder="Head count" required>
                        <input name="companyOffId" class="form-control" placeholder="Company Registration Id" required>
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