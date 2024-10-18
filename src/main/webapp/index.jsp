<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tasking - Home</title>
    <!-- Tailwind CSS via CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<main class="relative h-screen overflow-hidden bg-white dark:bg-gray-800">
    <header class="z-30 flex items-center w-full h-24 sm:h-32">
        <div class="container flex items-center justify-between px-6 mx-auto">
            <div class="text-3xl font-black text-gray-800 uppercase dark:text-white">
                Tasking
            </div>
            <div class="flex items-center">
                <nav class="items-center hidden text-lg text-gray-800 uppercase font-sen dark:text-white lg:flex">
                    <a href="${pageContext.request.contextPath}/" class="flex px-6 py-2 text-indigo-500 border-b-2 border-indigo-500">
                        Home
                    </a>
                    <a href="${pageContext.request.contextPath}/servlet/TaskServlet" class="flex px-6 py-2 hover:text-indigo-500">
                        Tasks
                    </a>
                    <a href="${pageContext.request.contextPath}/servlet/auth/LoginServlet" class="flex px-6 py-2 hover:text-indigo-500">
                        Login
                    </a>
                    <a href="${pageContext.request.contextPath}/servlet/auth/RegisterServlet" class="flex px-6 py-2 hover:text-indigo-500">
                        Register
                    </a>
                </nav>
                <button class="flex flex-col ml-4 lg:hidden">
                    <span class="w-6 h-1 mb-1 bg-gray-800 dark:bg-white"></span>
                    <span class="w-6 h-1 mb-1 bg-gray-800 dark:bg-white"></span>
                    <span class="w-6 h-1 mb-1 bg-gray-800 dark:bg-white"></span>
                </button>
            </div>
        </div>
    </header>
    <div class="relative z-20 flex items-center bg-white dark:bg-gray-800">
        <div class="container relative flex flex-col items-center justify-between px-6 py-8 mx-auto">
            <div class="flex flex-col">
                <h1 class="w-full text-4xl font-light text-center text-gray-800 uppercase sm:text-5xl dark:text-white">
                    Task Management Made Easy
                </h1>
                <h2 class="w-full max-w-2xl py-8 mx-auto text-xl font-light text-center text-gray-500 dark:text-white">
                    Tasking helps you organize, track, and manage your tasks efficiently. Stay productive and never miss a deadline again.
                </h2>
                <div class="flex items-center justify-center mt-4">
                    <a href="${pageContext.request.contextPath}/register" class="px-4 py-2 mr-4 text-white uppercase bg-gray-800 border-2 border-transparent text-md hover:bg-gray-900">
                        Get started
                    </a>
                    <a href="${pageContext.request.contextPath}/login" class="px-4 py-2 text-gray-800 uppercase bg-transparent border-2 border-gray-800 dark:text-white hover:bg-gray-800 hover:text-white text-md">
                        Login
                    </a>
                </div>
            </div>
            <div class="relative block w-full mx-auto mt-6 md:mt-0">
                <img src="${pageContext.request.contextPath}/ressources/images/tsk1.jpg" alt="Task Management" class="max-w-xs m-auto md:max-w-2xl"/>
            </div>
        </div>
    </div>
</main>

<!-- Votre fichier JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>