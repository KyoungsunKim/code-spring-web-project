<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2019-03-02
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Custom Login Page</h1>
<h2><c:out value="${error}"></c:out></h2>
<h2><c:out value="${logout}"></c:out></h2>

<form method="post" action="/login">
    <div>
        <input type="text" name="username">
    </div>
    <div>
        <input type="password" name="password">
    </div>
    <div>
        <input type="submit">
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>
