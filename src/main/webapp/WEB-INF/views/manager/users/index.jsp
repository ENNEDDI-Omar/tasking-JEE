<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users Management</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<%@ include file="../../common/header.jsp" %>

<div class="container mx-auto p-6">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold">Users Management</h1>
        <a href="${pageContext.request.contextPath}/manager/users/create"
           class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Create New User
        </a>
    </div>

    <div class="bg-white rounded-lg shadow-md p-6">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Username</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <c:forEach var="user" items="${users}">
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap">${user.username}</td>
                    <td class="px-6 py-4 whitespace-nowrap">${user.email}</td>
                    <td class="px-6 py-4 whitespace-nowrap">${user.role.name}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <a href="${pageContext.request.contextPath}/manager/users/edit/${user.id}"
                           class="text-indigo-600 hover:text-indigo-900 mr-3">Edit</a>
                        <button onclick="deleteUser(${user.id})"
                                class="text-red-600 hover:text-red-900">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function deleteUser(id) {
        if (confirm('Are you sure you want to delete this user?')) {
            fetch('${pageContext.request.contextPath}/manager/users/delete/' + id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    window.location.reload();
                }
            });
        }
    }
</script>
</body>
</html>