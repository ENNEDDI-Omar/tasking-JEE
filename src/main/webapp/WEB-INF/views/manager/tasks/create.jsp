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
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Création des Tâches</h1>

    <!-- Formulaire pour ajouter une nouvelle tâche -->
    <form action="${pageContext.request.contextPath}/manager/tasks/create" method="post" class="mb-8 bg-white p-6 rounded shadow">
        <div class="space-y-4">
            <div>
                <label class="block text-sm font-medium text-gray-700">Titre</label>
                <input type="text" name="title" required
                       class="mt-1 block w-full p-2 border rounded">
            </div>

            <input type="hidden" name="creationDate" value="${LocalDateTime.now()}">

            <div>
                <label class="block text-sm font-medium text-gray-700">Description</label>
                <textarea name="description" required
                          class="mt-1 block w-full p-2 border rounded"></textarea>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700">Date d'échéance</label>
                <input type="datetime-local"
                       name="dueDate"
                       required
                       min="${LocalDateTime.now()}"
                       max="${LocalDateTime.now().plusDays(3)}"
                       class="mt-1 block w-full p-2 border rounded">
                <p class="text-sm text-gray-500 mt-1">
                    La date d'échéance doit être dans les 3 prochains jours
                </p>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700">Assigné à</label>
                <select name="assignedUserId" required class="mt-1 block w-full p-2 border rounded">
                    <option value="">Sélectionnez un utilisateur</option>
                    <c:if test="${not empty users}">
                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}">${user.username}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700">Tags (sélectionnez au moins 2)</label>
                <select name="tags" multiple required class="mt-1 block w-full p-2 border rounded" size="4">
                    <c:forEach var="tag" items="${tags}">
                        <option value="${tag.id}" style="background-color: ${tag.color}20; padding: 4px;">
                                ${tag.name}
                        </option>
                    </c:forEach>
                </select>
                <p class="text-sm text-gray-500 mt-1">
                    Maintenez Ctrl (Windows) ou Cmd (Mac) pour sélectionner plusieurs tags
                </p>
                <c:if test="${not empty error}">
                    <p class="text-red-500 text-sm mt-1">${error}</p>
                </c:if>
            </div>

            <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Créer la tâche
            </button>
            <a href="${pageContext.request.contextPath}/manager/tasks"
               class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">
                Retour à la liste
            </a>
        </div>
    </form>
</div>
</body>
</html>