
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h1>Edit User</h1>
<form action="${pageContext.request.contextPath}/users/edit" method="post">
    <input type="hidden" name="id" value="${user.id}">
    Username: <input type="text" name="username" value="${user.username}"><br>
    Email: <input type="email" name="email" value="${user.email}"><br>
    Password: <input type="password" name="password" placeholder="Leave blank to keep current password"><br>
    <input type="submit" value="Update User">
</form>
</body>
</html>
