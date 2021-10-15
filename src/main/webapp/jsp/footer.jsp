<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet" />
    <script src="https://kit.fontawesome.com/8d5a2ad3e4.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css">
</head>
<body id="footer" style="justify-self: flex-end">

<footer class="text-center text-lg-start text-muted align-self-stretch" style="background-color: var(--cafe-white)">
    <section class="d-flex justify-content-center justify-content-lg-between p-4">
        <form action="${pageContext.request.contextPath}/controller" class="d-flex mb-0">
            <input type="hidden" name="command" value="change_locale" />
            <select class="form-select" name="locale" aria-label="select locale" onchange="this.form.submit()">
                <option value="ru_RU" ${currentLocale eq 'ru_RU' ? 'selected' : ''}>Русский</option>
                <option value="en_US" ${currentLocale eq 'en_US' ? 'selected' : ''}>English</option>
            </select>
        </form>

        <section class="d-inline-flex align-items-center">
            <a href="https://www.facebook.com/" class="me-4 text-reset">
                <i class="fab fa-facebook-f fa-lg"></i>
            </a>
            <a href="https://www.instagram.com/" class="me-4 text-reset">
                <i class="fab fa-instagram fa-lg"></i>
            </a>
            <a href="https://twitter.com/" class="me-4 text-reset">
                <i class="fab fa-twitter fa-lg"></i>
            </a>
            <p class="me-4 my-auto">
                <i class="fas fa-envelope fa-lg me-2"></i>
                cafess.shop@gmail.com
            </p>
            <p class="my-auto">
                <i class="fas fa-phone fa-lg me-2"></i>
                + 375 22 331-78-68
            </p>
        </section>
    </section>

    <hr class="mx-4 my-0"/>
    <div class="text-center p-4">
        © 2021 Copyright
    </div>
</footer>

</body>
</html>



