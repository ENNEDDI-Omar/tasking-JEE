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

    <section class="min-h-80 relative flex flex-1 shrink-0 items-center justify-center overflow-hidden rounded-lg bg-gray-100 py-16 shadow-lg md:py-20 xl:py-48">
        <!-- image - start -->
        <img src="${pageContext.request.contextPath}/ressources/images/tsk1.jpg" loading="lazy" alt="Photo by Fakurian Design"
             class="absolute inset-0 h-full w-full object-cover object-center" />
        <!-- image - end -->
        <!-- overlay - start -->
        <div class="absolute inset-0  mix-blend-multiply"></div>
        <!-- overlay - end -->
        <!-- text start -->
        <div class="relative flex flex-col items-center p-4 sm:max-w-xl">
            <p class="mb-4 text-center text-lg text-gray-200 sm:text-xl md:mb-8">Task Management Made Easy</p>
            <h1 class="mb-8 text-center text-4xl font-bold text-white sm:text-5xl md:mb-12 md:text-6xl">
                Tasking
            </h1>
            <div class="flex w-full flex-col gap-2.5 sm:flex-row sm:justify-center">
                <a href="${pageContext.request.contextPath}/servlet/auth/LoginServlet"
                   class="inline-block rounded-lg bg-red-600 px-8 py-3 text-center text-sm font-semibold text-white outline-none ring-indigo-300 transition duration-100 hover:bg-green-600 focus-visible:ring active:text-gray-700 md:text-base">Login</a>
                <a href="${pageContext.request.contextPath}/servlet/auth/RegisterServlet"
                   class="inline-block rounded-lg bg-green-600 px-8 py-3 text-center text-sm font-semibold text-white outline-none ring-indigo-300 transition duration-100 hover:bg-red-600 focus-visible:ring active:bg-indigo-700 md:text-base">Start
                    now</a>
            </div>
        </div>
        <!-- text end -->
    </section>
</main>

<!-- Votre fichier JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>