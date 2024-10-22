<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<%@ include file="../common/header.jsp" %>

<div class="container mx-auto p-6">
    <div class="bg-white rounded-lg shadow-md p-6 max-w-2xl mx-auto">
        <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Profile</h1>
        </div>
        <div class="space-y-4">
            <div class="flex border-b py-2">
                <span class="font-semibold w-1/3">Username:</span>
                <span class="text-gray-700">${user.username}</span>
            </div>
            <div class="flex border-b py-2">
                <span class="font-semibold w-1/3">Email:</span>
                <span class="text-gray-700">${user.email}</span>
            </div>
            <div class="flex border-b py-2">
                <span class="font-semibold w-1/3">Role:</span>
                <span class="text-gray-700">${user.role.name}</span>
            </div>
            <div class="flex border-b py-2">
                <span class="font-semibold w-1/3">Replace Tokens:</span>
                <span class="text-gray-700">${user.replaceTokens}</span>
            </div>
            <div class="flex border-b py-2">
                <span class="font-semibold w-1/3">Delete Tokens:</span>
                <span class="text-gray-700">${user.deleteTokens}</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>