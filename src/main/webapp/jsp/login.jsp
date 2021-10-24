<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <title><fmt:message key="title.login"/></title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="login-body" style="background-attachment: fixed; background-size: cover; background-image: url('${pageContext.request.contextPath}/content/background.jpg');">

<div class="form-login" style="margin-top: 6%">
    <form id="loginForm" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
        <input type="hidden" name="command" value="log_in"/>
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.welcomeMessage"/></h1>

        <div class="form-floating">
            <input id="floatingEmail" type="email" class="form-control invalid-input" name="email"
                   data-bs-toggle="popover" placeholder=" " required pattern="^(?=.{3,30}$)[\w.]+@[\w.]+$">
            <label for="floatingEmail"><fmt:message key="login.label.email"/></label>
        </div>

        <div class="form-floating">
            <input id="floatingPassword" type="password" class="form-control invalid-input" name="password"
                   data-bs-toggle="popover" placeholder=" " required minlength="8" maxlength="40">
            <label for="floatingPassword"><fmt:message key="login.label.password"/></label>
        </div>
        <p class="text-danger">
            ${invalidLoginOrPassword}
        </p>
        <button class="w-100 mt-3 button button-primary button-lg" type="submit" id="loginButton"><fmt:message key="login.action.submit"/></button>
    </form>

    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="go_to_signup_page"/>
        <fmt:message key="login.label.signup"/>
        <button class="w-100 button button-primary-dark button-lg" type="submit"><fmt:message key="login.action.signup"/></button>
    </form>
</div>

<c:import url="footer.jsp"/>

<fmt:message key="login.error.valueMissing" var="valueMissing"/>
<fmt:message key="login.error.emailPatternMismatch" var="emailPatternMismatch"/>
<fmt:message key="login.error.passwordTooShort" var="passwordTooShort"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.min.js" type='text/javascript'></script>

<script>
    $(document).ready(function () {
        const emailInput = document.getElementById('floatingEmail');
        const passwordInput = document.getElementById('floatingPassword');

        const loginForm = document.getElementById('loginForm');

        let popover = new bootstrap.Popover(emailInput, { trigger: 'manual' });

        emailInput.addEventListener('input', function () {
            checkEmailValidity();
        });
        emailInput.addEventListener('focusin', function () {
            checkEmailValidity();
        });

        passwordInput.addEventListener('input', function () {
            checkPasswordValidity();
        });
        passwordInput.addEventListener('focusin', function () {
            checkPasswordValidity();
        })

        function checkEmailValidity() {
            if (emailInput.validity.valid) {
                popover.hide();
            } else {
                showEmailError();
            }
        }

        function checkPasswordValidity() {
            if (passwordInput.validity.valid) {
                popover.hide();
            } else {
                showPasswordError();
            }
        }

        function showEmailError() {
            if (emailInput.validity.valueMissing) {
                emailInput.setAttribute('data-bs-content', "${valueMissing}");
            } else if (emailInput.validity.patternMismatch) {
                emailInput.setAttribute('data-bs-content', "${emailPatternMismatch}");
            }
            popover = createPopover(emailInput);
            popover.show();
        }

        function showPasswordError() {
            if (passwordInput.validity.valueMissing) {
                passwordInput.setAttribute('data-bs-content', "${valueMissing}");
            } else if (passwordInput.validity.tooShort) {
                passwordInput.setAttribute('data-bs-content', "${passwordTooShort}");
            }
            popover = createPopover(passwordInput);
            popover.show();
        }

        function createPopover(input) {
            popover.dispose();
            return new bootstrap.Popover(input, { trigger: 'manual' });
        }

        loginForm.addEventListener('submit', function (event) {
            if (!emailInput.validity.valid) {
                popover = createPopover(emailInput);
                showEmailError();
            } else if (!passwordInput.validity.valid) {
                popover = createPopover(passwordInput);
                showPasswordError();
            }

            if (!emailInput.validity.valid || !passwordInput.validity.valid) {
                event.preventDefault();
            }
        });
        loginForm.addEventListener('focusout', function () {
            popover.hide();
        });
    });
</script>

</body>
</html>
