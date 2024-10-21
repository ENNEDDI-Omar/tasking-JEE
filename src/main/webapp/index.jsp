<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tasking - Home</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ressources/css/output.css">
</head>
<body class="bg-white dark:bg-gray-800">
<div class="container mx-auto px-6">
    <header class="z-30 flex items-center w-full h-24 sm:h-32">
        <div class="container flex items-center justify-between mx-auto">
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

    <main class="relative overflow-hidden bg-gray-100 rounded-lg shadow-lg">
        <section class="relative flex flex-col items-center justify-center  py-16 md:py-20 xl:py-48">
            <img src="${pageContext.request.contextPath}/ressources/images/tsk1.jpg" loading="lazy" alt="Photo by Fakurian Design"
                 class="absolute inset-0 object-cover w-full h-full" />
            <div class="absolute inset-0 bg-black opacity-30"></div>
            <div class="relative z-10 flex flex-col items-center p-4 text-center">
                <p class="mb-4 text-lg text-gray-200 sm:text-xl md:mb-8">Task Management Made Easy</p>
                <h1 class="mb-8 text-4xl font-bold text-white sm:text-5xl md:mb-12 md:text-6xl">
                    Tasking
                </h1>
                <div class="flex flex-col gap-2.5 sm:flex-row sm:justify-center">
                    <a href="${pageContext.request.contextPath}/login"
                       class="inline-block px-8 py-3 text-sm font-semibold text-center text-white transition duration-100 rounded-lg outline-none bg-gray-800 ring-indigo-300 hover:bg-indigo-500 focus-visible:ring active:text-gray-700 md:text-base">Login</a>
                    <a href="${pageContext.request.contextPath}/register"
                       class="inline-block px-8 py-3 text-sm font-semibold text-center text-white transition duration-100 rounded-lg outline-none bg-gray-800 ring-indigo-300 hover:bg-indigo-500 focus-visible:ring active:bg-indigo-700 md:text-base">Start now</a>
                </div>
            </div>
        </section>
    </main>
</div>

<!-- Votre fichier JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>