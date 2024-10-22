<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<%@ include file="../common/header.jsp" %>

<div class="container mx-auto p-6">
    <div class="grid grid-cols-1 gap-6">
        <!-- Users Section -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-xl font-bold text-gray-800">Users</h2>
            </div>
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Username</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
                    </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td class="px-6 py-4 whitespace-nowrap">${user.username}</td>
                            <td class="px-6 py-4 whitespace-nowrap">${user.email}</td>
                            <td class="px-6 py-4 whitespace-nowrap">${user.role.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Tasks Section -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-xl font-bold text-gray-800">Tasks</h2>
            </div>
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Assigned To</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Due Date</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                    <c:forEach var="task" items="${tasks}">
                        <tr>
                            <td class="px-6 py-4 whitespace-nowrap">${task.title}</td>
                            <td class="px-6 py-4 whitespace-nowrap">${task.assignedUser.username}</td>
                            <td class="px-6 py-4 whitespace-nowrap">${task.dueDate}</td>
                            <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                            ${task.status == 'DONE' ? 'bg-green-100 text-green-800' :
                                              task.status == 'IN_PROGRESS' ? 'bg-yellow-100 text-yellow-800' :
                                              'bg-gray-100 text-gray-800'}">
                                                ${task.status}
                                        </span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Tags Section -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-xl font-bold text-gray-800">Tags</h2>
            </div>
            <div class="flex flex-wrap gap-2">
                <c:forEach var="tag" items="${tags}">
                        <span class="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">
                                ${tag.name}
                        </span>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>