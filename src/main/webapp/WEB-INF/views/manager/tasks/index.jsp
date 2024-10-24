<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Tâches</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<!-- Ajouter en haut de votre liste de tâches -->
<c:if test="${not empty sessionScope.successMessage}">
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">${sessionScope.successMessage}</span>
    </div>
    <% session.removeAttribute("successMessage"); %>
</c:if>
<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold">Liste des Tâches</h1>
        <a href="${pageContext.request.contextPath}/manager/tasks/create"
           class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Nouvelle Tâche
        </a>
    </div>

    <!-- Liste des tâches -->
    <div class="space-y-4">
        <c:forEach var="task" items="${tasks}">
            <div class="bg-white p-4 rounded shadow flex justify-between items-center">
                <div>
                    <h3 class="font-bold">${task.title}</h3>
                    <p class="text-gray-600">${task.description}</p>
                    <p class="text-sm text-gray-500">Assigné à: ${task.assignedUser.username}</p>
                    <p class="text-sm text-gray-500">Date d'échéance: ${task.dueDate}</p>
                </div>
                <div class="space-x-2">
                    <a href="${pageContext.request.contextPath}/manager/tasks/edit/${task.id}"
                       class="inline-block bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">
                        Modifier
                    </a>
                    <form action="${pageContext.request.contextPath}/manager/tasks/delete/${task.id}"
                          method="post" class="inline-block">
                        <button type="submit"
                                class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
                            Supprimer
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>