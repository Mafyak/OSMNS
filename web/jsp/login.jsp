<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resources.content"/>
<fmt:setLocale value="${locale}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/jsp/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="/jsp/css/style.css" rel="stylesheet">
    <script src="/jsp/js/bootstrap.min.js"></script>
    <script src="/jsp/js/jquery-1.11.1.min.js"></script>
    <script src="/jsp/js/login-page.js"></script>
    <title>Login</title>
</head>
<body>
<div class="container-fluid bg">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-5">
                            <a href="#" class="active" id="login-form-link"><fmt:message key="login"/></a>
                        </div>
                        <div class="col-xs-5">
                            <a href="#" id="register-form-link"><fmt:message key="register"/></a>
                        </div>
                        <div class="col-xs-2">
                            <a href="/Controller?command=change_language&lang=en"><img id="change_lang_en" src="../img/empty_pix.gif"></a>
                            <a href="/Controller?command=change_language&lang=ru"><img id="change_lang_ru" src="../img/empty_pix.gif"></a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" method="POST" action="Controller" style="display: block;">
                                <div class="form-group">
                                    <input type="text" name="login" id="username" tabindex="1" class="form-control" placeholder="Username" value="" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required>
                                </div>
                                <div class="form-group">
                                    ${infoMessage}
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="command" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Login">
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <form id="register-form" action="Controller" method="POST" style="display: none;">
                                <div class="form-group">
                                    <input type="text" name="regLogin" id="regLogin" tabindex="1" class="form-control" placeholder="Email Address" value="" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="regPassword" id="regPassword" tabindex="1" class="form-control"
                                           placeholder="Password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                           title="Must contain at least one number, one uppercase, lowercase letter and at least 8 or more characters"
                                           required>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="fName" id="first-name" tabindex="2" class="form-control" placeholder="First Name" required>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="lName" id="last-name" tabindex="2" class="form-control" placeholder="Last Name" required>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="command" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>
