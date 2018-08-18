<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" scope="application" class="by.epam.entity.User"/>

<fmt:bundle basename="resources.content">

    <fmt:message key="company" var="cСompany"/>
    <fmt:message key="cmn.fName" var="cmnFName"/>
    <fmt:message key="cmn.lName" var="cmnLName"/>
    <fmt:message key="cmn.login" var="Login"/>
    <fmt:message key="cmn.password" var="Password"/>
    <fmt:message key="comp.loc" var="cLoc"/>
    <fmt:message key="comp.taxID" var="cTaxId"/>
    <fmt:message key="cmn.update" var="update"/>
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
<form id="update_set" action="${pageContext.request.contextPath}/Controller?command=update_info" method="POST"
      style="display: block;">
    <div class="row">
        <div class="col-md-4 col-md-offset-3">
            <table class="table" style="text-align: center; border: 1px solid #ddd;">
                <tbody>
                <tr>
                    <th>${cmnFName}</th>
                    <td><input type="text" name="fName" class="form-control" value="${user.fName}" required></td>
                </tr>
                <tr>
                    <th>${cmnLName}</th>
                    <td><input type="text" name="lName" class="form-control" value="${user.lName}" required></td>
                </tr>
                <tr>
                    <th>${Login}</th>
                    <td><input type="text" name="email" class="form-control" value="${user.email}" required></td>
                </tr>
                <tr>
                    <th>${Password}</th>
                    <td><input type="password" name="pass" class="form-control" value="${user.pass}" disabled></td>
                </tr>
                <tr>
                    <th>${Password}</th>
                    <td><input type="password" name="pass2" class="form-control" value="${user.pass}" disabled></td>
                </tr>
                <tr>
                    <th>${cСompany}</th>
                    <td><input type="text" name="cName" class="form-control" value="${user.company.name}" disabled>
                    </td>
                </tr>
                <tr>
                    <th>${cLoc}</th>
                    <td><input type="text" name="cLocation" class="form-control" value="${user.company.location}"
                               disabled></td>
                </tr>
                <tr>
                    <th>${cTaxId}</th>
                    <td><input type="text" name="cOffId" class="form-control" pattern="\d+" title="Digits only"
                               value="${user.company.companyOfficialId}" required>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <button type="submit" name="command" id="register-submit" class="btn"
                                value="Update">${update}</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</form>
${errorMessage}
</body>
</html>
