<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="bg-gray-800 text-white p-4">
    <div class="container mx-auto flex justify-between items-center">
        <div class="flex items-center">
            <h1 class="text-xl font-bold">Tasking App</h1>
        </div>
        <nav>
            <ul class="flex space-x-4">
                <c:choose>
                    <c:when test="${user.role.name == 'MANAGER'}">
                        <li><a href="${pageContext.request.contextPath}/manager/dashboard" class="hover:text-gray-300">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/tasks" class="hover:text-gray-300">Tasks</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/user/tasks" class="hover:text-gray-300">My Tasks</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="${pageContext.request.contextPath}/user/profile" class="hover:text-gray-300">Profile</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/logout" method="post" class="inline">
                        <button type="submit" class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded">
                            Logout
                        </button>
                    </form>
                </li>
            </ul>
        </nav>
    </div>
</header>