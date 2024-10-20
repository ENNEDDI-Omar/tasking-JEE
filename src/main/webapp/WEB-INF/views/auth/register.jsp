<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" class="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Tasking App</title>
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
                        style="background-image: url(${pageContext.request.contextPath}/ressources/images/tsk2.jpg)"
                ></div>
            </div>
            <div class="col-span-6 md:col-span-4 lg:col-span-3 py-12">
                <div class="max-w-lg w-full h-full">
                    <div class="bg-white dark:bg-slate-800 shadow-xl rounded-2xl p-4 md:p-12 lg:py-16">
                        <h2 class="text-indigo-900 dark:text-white text-2xl font-bold mb-3">Create an Account</h2>
                        <div class="flex items-center mb-6 md:mb-12">
                            <p class="mb-0 mr-2 opacity-50">Already have an account?</p>
                            <a href="${pageContext.request.contextPath}/login" class="text-blue-600">Log In</a>
                        </div>

                        <form action="${pageContext.request.contextPath}/register" method="post" class="">
                            <div class="mb-4">
                                <input
                                        type="text"
                                        class="w-full bg-blue-50 dark:bg-slate-700 min-h-[48px] leading-10 px-4 p-2 rounded-lg outline-none border border-transparent focus:border-blue-600"
                                        id="username"
                                        name="username"
                                        placeholder="Enter Username"
                                        required
                                />
                            </div>
                            <div class="mb-4">
                                <input
                                        type="email"
                                        class="w-full bg-blue-50 dark:bg-slate-700 min-h-[48px] leading-10 px-4 p-2 rounded-lg outline-none border border-transparent focus:border-blue-600"
                                        id="email"
                                        name="email"
                                        placeholder="Enter Email"
                                        required
                                />
                            </div>
                            <div class="mb-4">
                                <input
                                        type="password"
                                        class="w-full bg-blue-50 dark:bg-slate-700 min-h-[48px] leading-10 px-4 p-2 rounded-lg outline-none border border-transparent focus:border-blue-600"
                                        id="password"
                                        name="password"
                                        placeholder="Enter Password"
                                        required
                                />
                            </div>
                            <button type="submit" class="bg-indigo-900 text-white py-3 px-6 rounded w-full">Register</button>
                        </form>
                        <% if (request.getAttribute("error") != null) { %>
                        <div class="text-red-500 mt-4">
                            <%= request.getAttribute("error") %>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>