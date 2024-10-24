<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" class="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Tasking App</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-white dark:bg-[#0b1727] text-zinc-900 dark:text-white">
<section class="ezy__signup10 light py-14 md:py-24">
    <div class="container px-4 mx-auto">
        <div class="grid grid-cols-6 gap-6 h-full">
            <div class="col-span-6 md:col-span-2 lg:col-span-3">
                <div
                        class="bg-cover bg-center bg-no-repeat min-h-[150px] rounded-xl hidden md:block w-full md:w-[200%] lg:w-[150%] h-full"
                        style="background-image: url(${pageContext.request.contextPath}/ressources/images/tsk13.jpg)"
                ></div>
            </div>
            <div class="col-span-6 md:col-span-4 lg:col-span-3 py-12">
                <div class="max-w-lg w-full h-full">
                    <div class="bg-white dark:bg-slate-800 shadow-xl rounded-2xl p-4 md:p-12 lg:py-16">
                        <h2 class="text-indigo-900 dark:text-white text-2xl font-bold mb-3">Welcome to Tasking App</h2>
                        <div class="flex items-center mb-6 md:mb-12">
                            <p class="mb-0 mr-2 opacity-50">Don't have an account?</p>
                            <a href="${pageContext.request.contextPath}/register" class="text-blue-600">Create Account</a>
                        </div>
                        <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="text-red-500 mb-4">
                            <%= request.getAttribute("errorMessage") %>
                        </div>
                        <% } %>

                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="mb-4">
                                <input type="text"
                                       name="username"
                                       class="w-full bg-blue-50 dark:bg-slate-700 px-4 py-3 rounded-lg"
                                       placeholder="Username"
                                       required>
                            </div>
                            <div class="mb-6">
                                <input type="password"
                                       name="password"
                                       class="w-full bg-blue-50 dark:bg-slate-700 px-4 py-3 rounded-lg"
                                       placeholder="Password"
                                       required>
                            </div>
                            <button type="submit"
                                    class="w-full bg-indigo-900 text-white py-3 rounded-lg">
                                Login
                            </button>
                        </form>
                        <button class="hover:text-blue-600 py-2 px-4 rounded-lg w-full">Forgot your password?</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>