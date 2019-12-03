<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Airlines Boomerang</title>

    <style type="text/css">
        .error {
            color: red;
        }
    </style>
</head>

<body>
<h1>Welcome to Airlines 'Boomerang' page</h1>
<form method="post" action="/login">
    <p>Login
    <input type="text" name="login" value="${param['login']}">
    </p>
    <p>Password
    <input type="password" name="password">
    </p>
    <input type="submit" value="Login">

    <c:if test="${not empty param['login']}">
    <p class="error">
        Login or password is incorrect!
    </p>
    </c:if>
</body>
</html>
