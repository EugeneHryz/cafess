<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>
    <title><fmt:message key="title.signup"/></title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="signup-body" style="background-attachment: fixed; background-size: cover; background-image: url('${pageContext.request.contextPath}/content/background.jpg');">

<div class="form-signup mt-5">
    <form id="signupForm" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
        <input type="hidden" name="command" value="sign_up" />

        <h1 class="h3 mb-3 fw-normal"><fmt:message key="signup.welcomeMessage"/></h1>
        <div class="form-floating">
            <input id="floatingName" type="text" class="form-control first-input" name="name" placeholder=" "
                   required pattern="^(\p{L}){3,20}$"/>
            <label for="floatingName"><fmt:message key="signup.label.name"/></label>
            <span id="invalidNameInput"></span>
        </div>
        <div class="form-floating">
            <input id="floatingSurname" type="text" class="form-control other-input" name="surname" placeholder=" "
                   pattern="^(\p{L}){3,20}$"/>
            <label for="floatingSurname"><fmt:message key="signup.label.surname"/></label>
        </div>
        <div class="form-floating">
            <input id="floatingEmail" type="email" class="form-control other-input" name="email" placeholder=" "
                   required pattern="^(?=.{3,30}$)[\w.]+@[\w.]+$"/>
            <label for="floatingEmail"><fmt:message key="signup.label.email"/></label>
        </div>
        <div class="form-floating">
            <input id="floatingPassword" type="password" class="form-control other-input" name="password" placeholder=" "
                   required minlength="8" maxlength="40"/>
            <label for="floatingPassword"><fmt:message key="signup.label.password"/></label>
        </div>
        <div class="form-floating">
            <input id="floatingRepeatPassword" type="password" class="form-control last-input" name="password" placeholder=" "
                   required minlength="8" maxlength="40"/>
            <label for="floatingRepeatPassword"><fmt:message key="signup.label.repeatPassword"/></label>
        </div>
        <p class="text-danger">
            ${loginAlreadyExists}
            ${invalidSignUpData}
        </p>
        <button class="w-100 mt-2 button button-primary button-lg" type="submit">
            <fmt:message key="signup.action.signup"/>
        </button>
    </form>
</div>

<c:import url="footer.jsp" />

<fmt:message key="signup.error.valueMissing" var="valueMissing" />
<fmt:message key="signup.error.namePatternMismatch" var="namePatternMismatch" />
<fmt:message key="signup.error.surnamePatternMismatch" var="surnamePatternMismatch" />
<fmt:message key="signup.error.emailPatternMismatch" var="emailPatternMismatch" />
<fmt:message key="signup.error.passwordTooShort" var="passwordTooShort" />
<fmt:message key="signup.error.passwordMismatch" var="passwordMismatch" />

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.min.js" type='text/javascript'></script>

<script>
    const nameInput = document.getElementById('floatingName');
    const surnameInput = document.getElementById('floatingSurname');
    const emailInput = document.getElementById('floatingEmail');
    const passwordInput = document.getElementById('floatingPassword');
    const repeatPasswordInput = document.getElementById('floatingRepeatPassword');

    const signupDataForm = document.getElementById('signupForm');

    let popover = new bootstrap.Popover(nameInput, { trigger: 'manual' });

    nameInput.addEventListener('input', function () {
        checkNameValidity();
    });
    nameInput.addEventListener('focusin', function () {
        checkNameValidity();
    });

    surnameInput.addEventListener('input', function () {
        checkSurnameValidity();
    });
    surnameInput.addEventListener('focusin', function () {
        checkSurnameValidity();
    });

    emailInput.addEventListener('input', function () {
        checkEmailValidity();
    });
    emailInput.addEventListener('focusin', function () {
        checkEmailValidity();
    });

    passwordInput.addEventListener('input', function () {
        checkPasswordValidity(passwordInput);
    });
    passwordInput.addEventListener('focusin', function () {
        checkPasswordValidity(passwordInput);
    });

    repeatPasswordInput.addEventListener('input', function () {
        checkRepeatedPasswordValidity();
    });
    repeatPasswordInput.addEventListener('focusin', function () {
        checkRepeatedPasswordValidity();
    });

    function checkNameValidity() {
        if (nameInput.validity.valid) {
            popover.hide();
        } else {
            showNameError();
        }
    }

    function checkSurnameValidity() {
        if (surnameInput.validity.valid) {
            popover.hide();
        } else {
            showSurnameError();
        }
    }

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

    function checkRepeatedPasswordValidity() {
        if (repeatPasswordInput.value === passwordInput.value) {
            popover.hide();
        } else {
            showRepeatPasswordError();
        }
    }

    function showNameError() {
        if (nameInput.validity.valueMissing) {
            nameInput.setAttribute('data-bs-content', "${valueMissing}");
        } else if (nameInput.validity.patternMismatch) {
            nameInput.setAttribute('data-bs-content', "${namePatternMismatch}");
        }
        popover = createPopover(nameInput);
        popover.show();
    }

    function showSurnameError() {
        if (surnameInput.validity.patternMismatch) {
            surnameInput.setAttribute('data-bs-content', "${surnamePatternMismatch}");
            popover = createPopover(surnameInput);
            popover.show();
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

    function showRepeatPasswordError() {
        repeatPasswordInput.setAttribute('data-bs-content', "${passwordMismatch}");
        popover = createPopover(repeatPasswordInput);
        popover.show();
    }

    function createPopover(input) {
        popover.dispose();
        return new bootstrap.Popover(input, { trigger: 'manual' });
    }

    signupDataForm.addEventListener('submit', function (event) {
        if (!nameInput.validity.valid) {
            showNameError();
        } else if (!surnameInput.validity.valid) {
            showSurnameError();
        } else if (!emailInput.validity.valid) {
            showEmailError();
        } else if (!passwordInput.validity.valid) {
            showPasswordError();
        } else if (passwordInput.value !== repeatPasswordInput.value) {
            showRepeatPasswordError();
        }

        if (!nameInput.validity.valid || !surnameInput.validity.valid || !emailInput.validity.valid
            || !passwordInput.validity.valid || passwordInput.value !== repeatPasswordInput.value) {
            event.preventDefault();
        }
    });
    signupDataForm.addEventListener('focusout', function () {
        popover.hide();
    });
</script>

</body>
</html>
