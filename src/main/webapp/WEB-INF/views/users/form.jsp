<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>User Form</title>
</head>
<body>
<h1>${user == null ? "Add New User" : "Edit User"}</h1>
<form action="${pageContext.request.contextPath}/users/${user == null ? 'add' : 'edit'}" method="post">
  <input type="hidden" name="id" value="${user.id}">
  Username: <input type="text" name="username" value="${user.username}"><br>
  Password: <input type="password" name="password"><br>
  Email: <input type="email" name="email" value="${user.email}"><br>
  <input type="submit" value="${user == null ? 'Add User' : 'Update User'}">
</form>
</body>
</html>