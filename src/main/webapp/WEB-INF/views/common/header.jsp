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
                        <li>
                            <a href="${pageContext.request.contextPath}/manager/dashboard"
                               class="hover:text-gray-300">Dashboard</a>
                        </li>
                        <li class="relative group">
                            <a href="${pageContext.request.contextPath}/manager/users"
                               class="hover:text-gray-300">Users</a>
                            <div class="absolute hidden group-hover:block bg-white text-black p-2 rounded shadow-lg">
                                <a href="${pageContext.request.contextPath}/manager/users/create"
                                   class="block px-4 py-2 hover:bg-gray-100">Create Users</a>
                            </div>
                        </li>
                        <li class="relative group">
                            <a href="${pageContext.request.contextPath}/manager/tasks"
                               class="hover:text-gray-300">Tasks</a>
                            <div class="absolute hidden group-hover:block bg-white text-black p-2 rounded shadow-lg">
                                <a href="${pageContext.request.contextPath}/manager/tasks/create"
                                   class="block px-4 py-2 hover:bg-gray-100">Create Tasks</a>
                            </div>
                        </li>
                        <li class="relative group">
                            <a href="${pageContext.request.contextPath}/manager/tags"
                               class="hover:text-gray-300">Tags</a>

                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/user/tasks" class="hover:text-gray-300">My Tasks</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="${pageContext.request.contextPath}/user/profile" class="hover:text-gray-300">Profile</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/logout" method="post" class="inline">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button type="submit" class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded">
                            Logout
                        </button>
                    </form>
                </li>
            </ul>
        </nav>
    </div>
</header>