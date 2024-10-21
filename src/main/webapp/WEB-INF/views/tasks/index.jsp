<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Tâches</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Mes Tâches</h1>

    <!-- Formulaire pour ajouter une nouvelle tâche -->
    <form action="${pageContext.request.contextPath}/tasks/add" method="post" class="mb-8">
        <div class="flex gap-2">
            <input type="text" name="taskName" placeholder="Nouvelle tâche" required
                   class="flex-grow p-2 border rounded">
            <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Ajouter
            </button>
        </div>
    </form>

    <!-- Liste des tâches -->
    <ul class="space-y-4">
        <c:forEach var="task" items="${tasks}">
            <li class="bg-white p-4 rounded shadow flex justify-between items-center">
                <span class="${task.completed ? 'line-through text-gray-500' : ''}">${task.name}</span>
                <div>
                    <form action="${pageContext.request.contextPath}/tasks/toggle" method="post" class="inline">
                        <input type="hidden" name="taskId" value="${task.id}">
                        <button type="submit" class="text-sm bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600">
                                ${task.completed ? 'Annuler' : 'Terminer'}
                        </button>
                    </form>
                    <a href="${pageContext.request.contextPath}/tasks/edit?id=${task.id}"
                       class="text-sm bg-yellow-500 text-white px-2 py-1 rounded hover:bg-yellow-600">
                        Modifier
                    </a>
                    <form action="${pageContext.request.contextPath}/tasks/delete" method="post" class="inline">
                        <input type="hidden" name="taskId" value="${task.id}">
                        <button type="submit" class="text-sm bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600">
                            Supprimer
                        </button>
                    </form>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>