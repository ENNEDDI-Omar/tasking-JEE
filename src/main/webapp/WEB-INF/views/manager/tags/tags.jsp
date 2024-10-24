<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Gestion des Tags</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
  <div class="flex justify-between items-center mb-6">
    <h1 class="text-3xl font-bold">Gestion des Tags</h1>
    <a href="${pageContext.request.contextPath}/manager/tags/create"
       class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
      Nouveau Tag
    </a>
  </div>

  <div class="bg-white rounded-lg shadow overflow-hidden">
    <table class="min-w-full">
      <thead class="bg-gray-50">
      <tr>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nom</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Couleur</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
      </tr>
      </thead>
      <tbody class="bg-white divide-y divide-gray-200">
      <c:forEach var="tag" items="${tags}">
        <tr>
          <td class="px-6 py-4 whitespace-nowrap">${tag.name}</td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="flex items-center">
              <div class="w-4 h-4 mr-2 rounded" style="background-color: ${tag.color}"></div>
                ${tag.color}
            </div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <a href="${pageContext.request.contextPath}/manager/tags/edit/${tag.id}"
               class="text-indigo-600 hover:text-indigo-900 mr-3">Modifier</a>
            <form action="${pageContext.request.contextPath}/manager/tags/delete/${tag.id}"
                  method="post" class="inline">
              <button type="submit"
                      class="text-red-600 hover:text-red-900"
                      onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce tag ?')">
                Supprimer
              </button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>