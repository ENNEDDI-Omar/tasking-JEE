<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Tasks</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<%@ include file="../common/header.jsp" %>

<div class="container mx-auto p-6">
    <div class="bg-white rounded-lg shadow-md p-6">
        <div class="flex justify-between items-center mb-4">
            <h1 class="text-2xl font-bold text-gray-800">My Tasks</h1>
        </div>
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Due Date</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="task" items="${tasks}">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap">${task.title}</td>
                        <td class="px-6 py-4 whitespace-nowrap">${task.dueDate}</td>
                        <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                        ${task.status == 'DONE' ? 'bg-green-100 text-green-800' :
                                          task.status == 'IN_PROGRESS' ? 'bg-yellow-100 text-yellow-800' :
                                          'bg-gray-100 text-gray-800'}">
                                            ${task.status}
                                    </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <c:if test="${task.status != 'DONE'}">
                                <form action="${pageContext.request.contextPath}/task/complete" method="post" class="inline">
                                    <input type="hidden" name="taskId" value="${task.id}">
                                    <button type="submit"
                                            class="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded text-sm">
                                        Complete
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>