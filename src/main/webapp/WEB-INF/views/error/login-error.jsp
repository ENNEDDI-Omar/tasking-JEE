<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" class="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Error - Tasking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ressources/css/output.css">
</head>
<body class="bg-white dark:bg-[#0b1727] text-slate-800 dark:text-white">
<section class="ezy__httpcodes14 light py-[180px] md:py-[300px] relative z-[1]">
    <div class="container px-4 mx-auto">
        <div class="grid grid-cols-12 gap-6 px-4 lg:px-12">
            <div class="col-span-12 lg:col-span-5">
                <img
                        src="${pageContext.request.contextPath}/resources/images/login-error.jpg"
                        alt="Login Error"
                        class="rounded-2xl max-w-full h-auto"
                />
            </div>
            <div class="col-span-12 lg:col-span-6 lg:col-start-7 text-center lg:text-start">
                <div class="flex flex-col justify-center h-full">
                    <h1 class="font-bold text-3xl leading-none md:text-[45px] mb-4">Oops! Login Failed</h1>
                    <p class="text-xl opacity-80 mb-6">Invalid username or password. Please try again.</p>
                    <div class="flex justify-center lg:justify-start">
                        <a href="${pageContext.request.contextPath}/login" class="bg-blue-600 text-white hover:bg-opacity-90 py-3 px-4 rounded">
                            Try Again
                        </a>
                        <a href="${pageContext.request.contextPath}/" class="py-3 px-6 bg-transparent text-blue-600 border border-blue-600 hover:bg-blue-600 hover:text-white rounded ml-2">
                            Return to Homepage
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>