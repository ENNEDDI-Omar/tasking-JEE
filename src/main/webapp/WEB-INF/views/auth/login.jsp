<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<form action="j_security_check" method="post">
    Username: <input type="text" name="j_username" required><br>
    Password: <input type="password" name="j_password" required><br>
    <input type="submit" value="Login">
</form>
<p>Not registered? <a href="${pageContext.request.contextPath}/auth/register.jsp">Register here</a></p>
</body>
</html>