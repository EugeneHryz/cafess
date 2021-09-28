<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body id="login-body">

<c:import url="header.jsp"/>

<div class="form-login">
    <form action="controller" method="post" id="loginForm" novalidate>
        <input type="hidden" name="command" value="log_in"/>
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.welcomeMessage"/></h1>

        <div class="form-floating">
            <input type="email" class="form-control invalid-input" name="email" id="floatingEmail" placeholder=" " required pattern="^[\w.]+@[\w.]+$">
            <label for="floatingEmail"><fmt:message key="login.label.email"/></label>
            <span id="invalidEmailInput"></span>
        </div>

        <div class="form-floating">
            <input type="password" class="form-control invalid-input" name="password" id="floatingPassword" placeholder=" " required minlength="8">
            <label for="floatingPassword"><fmt:message key="login.label.password"/></label>
            <span id="invalidPasswordInput"></span>
        </div>

        ${invalidLoginOrPassword}

        <button class="w-100 mt-4 btn btn-lg btn-primary button-login" type="submit" id="loginButton"><fmt:message
                key="login.button.submit"/></button>
    </form>

    <form action="controller">
        <input type="hidden" name="command" value="go_to_signup_page"/>
        <fmt:message key="login.label.signup"/>
        <button class="w-100 btn-dark btn-lg btn-primary button-signup" type="submit"><fmt:message
                key="login.button.signup"/></button>
    </form>
</div>

<fmt:message key="login.error.emailPatternMismatch" var="emailPatternMismatch"/>
<fmt:message key="login.error.emailMissing" var="emailMissing"/>

<fmt:message key="login.error.passwordTooShort" var="passwordTooShort"/>
<fmt:message key="login.error.passwordMissing" var="passwordMissing"/>

<c:import url="footer.jsp"/>

<script>
    const email = document.getElementById('floatingEmail');
    const password = document.getElementById('floatingPassword');

    const form = document.getElementById('loginForm');

    const emailError = document.getElementById('invalidEmailInput');
    const passwordError = document.getElementById('invalidPasswordInput');

    email.addEventListener('input', function () {

        if (email.validity.valid) {
            emailError.textContent = ''
        } else {
            showEmailError();
        }
    });

    password.addEventListener('input', function () {
        if (password.validity.valid) {
            passwordError.textContent = '';
        } else {
            showPasswordError();
        }
    });

    form.addEventListener('submit', function (event) {
        if (!email.validity.valid) {
            showEmailError();
        }
        if (!password.validity.valid) {
            showPasswordError();
        }
        if (!email.validity.valid || !password.validity.valid) {
            event.preventDefault();
        }
    });

    function showEmailError() {
        if (email.validity.valueMissing) {
            emailError.textContent = "${emailMissing}";
        } else if (email.validity.patternMismatch) {
            emailError.textContent = "${emailPatternMismatch}";
        }
    }

    function showPasswordError() {
        if (password.validity.tooShort) {
            passwordError.textContent = "${passwordTooShort}"
        } else if (password.validity.valueMissing) {
            passwordError.textContent = "${passwordMissing}"
        }
    }
</script>

</body>
</html>
