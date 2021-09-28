<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>
    <title>Sign up</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
</head>
<body id="signup-body">

<c:import url="header.jsp" />

<div class="form-signup">
    <form action="${pageContext.request.contextPath}/controller" method="post" id="signupForm" novalidate>
        <input type="hidden" name="command" value="sign_up" />

        <h1 class="h3 mb-3 fw-normal"><fmt:message key="signup.welcomeMessage"/></h1>
        <div class="form-floating">
            <input id="floatingName" type="text" class="form-control first-input" name="name" placeholder=" " required pattern="^(\p{L}){3,}$"/>
            <label for="floatingName"><fmt:message key="signup.label.name"/></label>
            <span id="invalidNameInput"></span>
        </div>
        <div class="form-floating">
            <input id="floatingSurname" type="text" class="form-control other-input" name="surname" placeholder=" " pattern="^(\p{L}){3,}$"/>
            <label for="floatingSurname"><fmt:message key="signup.label.surname"/></label>
            <span id="invalidSurnameInput"></span>
        </div>
        <div class="form-floating">
            <input id="floatingEmail" type="email" class="form-control other-input" name="email" placeholder=" " required pattern="^[\w.]+@[\w.]+$"/>
            <label for="floatingEmail"><fmt:message key="signup.label.email"/></label>
            <span id="invalidEmailInput"></span>
        </div>
        <div class="form-floating">
            <input id="floatingPassword" type="password" class="form-control other-input" name="password" placeholder=" " required minlength="8"/>
            <label for="floatingPassword"><fmt:message key="signup.label.password"/></label>
            <span id="invalidPasswordInput"></span>
        </div>
        <%--There's no need to put password twice in the request--%>
        <div class="form-floating">
            <input id="floatingRepeatPassword" type="password" class="form-control last-input" name="password" placeholder=" " required minlength="8"/>
            <label for="floatingRepeatPassword"><fmt:message key="signup.label.repeatPassword"/></label>
            <span id="invalidRepeatedPasswordInput"></span>
        </div>

        ${loginAlreadyExists}
        ${invalidSignUpData}

        <button class="w-100 mt-2 btn btn-lg btn-primary button-signup" type="submit">
            <fmt:message key="signup.button.signup"/>
        </button>
    </form>
</div>

<fmt:message key="signup.error.valueMissing" var="valueMissing" />
<fmt:message key="signup.error.namePatternMismatch" var="namePatternMismatch" />
<fmt:message key="signup.error.surnamePatternMismatch" var="surnamePatternMismatch" />
<fmt:message key="signup.error.emailPatternMismatch" var="emailPatternMismatch" />
<fmt:message key="signup.error.passwordTooShort" var="passwordTooShort" />
<fmt:message key="signup.error.passwordMismatch" var="passwordMismatch" />

<c:import url="footer.jsp" />

<script>
    const name = document.getElementById('floatingName');
    const surname = document.getElementById('floatingSurname');
    const email = document.getElementById('floatingEmail');
    const password = document.getElementById('floatingPassword');
    const repeatedPassword = document.getElementById('floatingRepeatPassword');

    const form = document.getElementById('signupForm');

    const nameError = document.getElementById('invalidNameInput');
    const surnameError = document.getElementById('invalidSurnameInput');
    const emailError = document.getElementById('invalidEmailInput');
    const passwordError = document.getElementById('invalidPasswordInput');
    const repeatedPasswordError = document.getElementById('invalidRepeatedPasswordInput');

    name.addEventListener('input', function () {
        if (name.validity.valid) {
            nameError.textContent = '';
        } else {
            showNameError();
        }
    });

    surname.addEventListener('input', function() {
        if (surname.validity.valid) {
            surnameError.textContent = '';
        } else {
            showSurnameError();
        }
    });

    email.addEventListener('input', function() {
        if (email.validity.valid) {
            emailError.textContent = '';
        } else {
            showEmailError();
        }
    });

    password.addEventListener('input', function() {
       if (password.validity.valid) {
           passwordError.textContent = '';
       } else {
           showPasswordError();
       }
    });

    repeatedPassword.addEventListener('input', function() {
        if (repeatedPassword.validity.valid && repeatedPassword.value === password.value) {
            repeatedPasswordError.textContent = '';
        } else {
            showRepeatedPasswordError();
        }
    });

    form.addEventListener('submit', function (event) {
        if (!name.validity.valid) {
            showNameError();
        }
        if (!surname.validity.valid) {
            showSurnameError();
        }
        if (!email.validity.valid) {
            showEmailError();
        }
        if (!password.validity.valid) {
            showPasswordError();
        }
        if (!repeatedPassword.validity.valid || repeatedPassword.value !== password.value) {
            showPasswordError()
        }
        if (!name.validity.valid || !surname.validity.valid || !email.validity.valid ||
            (!repeatedPassword.validity.valid || repeatedPassword.value !== password.value)) {

            event.preventDefault();
        }
    });

    function showNameError() {
        if (name.validity.valueMissing) {
            nameError.textContent = "${valueMissing}";
        } else if (name.validity.patternMismatch) {
            nameError.textContent = "${namePatternMismatch}";
        }
    }

    function showSurnameError() {
        if (surname.validity.patternMismatch) {
            surnameError.textContent = "${surnamePatternMismatch}";
        }
    }

    function showEmailError() {
        if (email.validity.valueMissing) {
            emailError.textContent = "${valueMissing}";
        } else if (email.validity.patternMismatch) {
            emailError.textContent = "${emailPatternMismatch}";
        }
    }

    function showPasswordError() {
        if (password.validity.valueMissing) {
            passwordError.textContent = "${valueMissing}";
        } else if (password.validity.tooShort) {
            passwordError.textContent = "${passwordTooShort}";
        }
    }

    function showRepeatedPasswordError() {
        if (repeatedPassword.validity.valueMissing) {
            repeatedPasswordError.textContent = "${valueMissing}";
        } else if (repeatedPassword.value !== password.value) {
            repeatedPasswordError.textContent = "${passwordMismatch}";
        }
    }
</script>

</body>
</html>
