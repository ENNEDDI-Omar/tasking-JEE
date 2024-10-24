<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
<div class="bg-white p-8 rounded-lg shadow-md">
    <h1 class="text-2xl font-bold text-red-600 mb-4">Error</h1>
    <p class="text-gray-600">${error}</p>
    <a href="${pageContext.request.contextPath}/"
       class="mt-4 inline-block bg-blue-500 text-white px-4 py-2 rounded">
        Return Home
    </a>
</div>
</body>
</html>