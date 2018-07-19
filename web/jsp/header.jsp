<%--
  Created by IntelliJ IDEA.
  User: WorkBase
  Date: 7/16/2018
  Time: 8:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="custag" uri="CustomTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resources.content">
    <fmt:message key="common.welcome_to" var="Welcome_to"/>
    <fmt:message key="main.logout" var="Logout"/>
</fmt:bundle>
<html>
<p>
        ${Welcome_to}<custag:welcome-message userType="${user.type}"/>, ${user.fName}<br/>
        <a href="${pageContext.request.contextPath}/Controller?command=logout">${Logout}</a><br/>
</p>
</html>