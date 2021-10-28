<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <title><fmt:message key="title.profile"/></title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile_settings.css"/>

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type='text/javascript'></script>
</head>

<body id="profile-settings-body" style="background: var(--cafe-background);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    min-height: 100vh;">

<c:import url="../header.jsp"/>

<div class="d-block light-style mb-5">
    <h4 class="display-6 fs-3 my-2">
        <fmt:message key="title.profile"/>
    </h4>

    <div class="card overflow-hidden">
        <div class="row">
            <div class="col-3 d-flex align-items-start">
                <div class="nav flex-column nav-pills align-items-stretch" role="tablist" aria-orientation="vertical">
                    <button class="navigation-link active" data-bs-toggle="pill" data-bs-target="#account-general"
                            type="button" role="tab" aria-selected="true"><fmt:message key="profile.text.general"/>
                    </button>
                    <button class="navigation-link" data-bs-toggle="pill" data-bs-target="#account-change-password"
                            type="button" role="tab" aria-selected="false"><fmt:message key="profile.text.changePassword"/>
                    </button>
                    <button class="navigation-link" data-bs-toggle="pill" data-bs-target="#account-balance"
                            type="button" role="tab" aria-selected="false"><fmt:message key="profile.text.balance"/>
                    </button>
                </div>
            </div>

            <div class="col-9">
                <div class="tab-content">
                    <div class="tab-pane fade active show" id="account-general" role="tabpanel">

                        <form method="post" action="${pageContext.request.contextPath}/controller"
                              enctype="multipart/form-data" class="mb-1">
                            <input type="hidden" name="command" value="update_profile_picture"/>
                            <div class="card-body media d-flex align-items-center">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.profileImagePath}">
                                        <img id="profileImage"
                                             src="${pageContext.request.contextPath}/files/users/${sessionScope.user.profileImagePath}"
                                             alt="profile image" class="rounded-circle" width="100" height="100"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img id="profileImage"
                                             src="${pageContext.request.contextPath}/content/no_profile_picture.png"
                                             alt="no profile picture" class="rounded-circle" height="100" width="100"/>
                                    </c:otherwise>
                                </c:choose>

                                <div class="media-body ms-4">
                                    <label class="button button-outline-primary me-1">
                                        <fmt:message key="profile.action.selectNewImage"/>
                                        <input id="fileUpload" type="file" name="file" accept="image/*"
                                               class="account-settings-fileinput">
                                    </label>
                                    <button type="submit" class="button button-primary"><fmt:message key="profile.action.save"/></button>

                                    <div class="text-muted small mt-1" id="text-light">
                                        <fmt:message key="profile.text.allowImage"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <hr class="border-light m-0" />

                        <div class="card-body" style="width: 70%">
                            <form id="userDataForm" method="post" action="${pageContext.request.contextPath}/controller" novalidate>
                                <input type="hidden" name="command" value="edit_user_profile"/>
                                <div class="form-group mb-2">
                                    <label class="form-label"><fmt:message key="profile.label.name"/></label>
                                    <input id="nameInput" type="text" name="name" class="form-control" value="${sessionScope.user.name}"
                                           data-bs-toggle="popover" required pattern="^(\p{L}){3,20}$"/>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label"><fmt:message key="profile.label.surname"/></label>
                                    <input id="surnameInput" type="text" name="surname" class="form-control" value="${sessionScope.user.surname}"
                                            data-bs-toggle="popover" pattern="^(\p{L}){3,20}$"/>
                                </div>
                                <div class="form-group mb-4">
                                    <label class="form-label"><fmt:message key="profile.label.email"/></label>
                                    <input id="emailInput" type="email" name="email" class="form-control" value="${sessionScope.user.email}"
                                            data-bs-toggle="popover" required pattern="^(?=.{3,30}$)[\w.]+@[\w.]+$"/>
                                </div>
                                <p class="text-danger">
                                    ${editProfileFail}
                                    ${changePasswordFail}
                                </p>
                                <p class="text-success">
                                    ${changePasswordSuccess}
                                </p>
                                <button type="submit" form="userDataForm" class="button button-primary-dark"
                                        style="color: var(--cafe-secondary)"><fmt:message key="profile.action.saveChanges"/></button>
                            </form>
                        </div>

                    </div>

                    <div class="tab-pane fade" id="account-change-password" role="tabpanel">
                        <div class="card-body" style="width: 70%">
                            <form id="changePasswordForm" method="post" action="${pageContext.request.contextPath}/controller" class="mb-0" novalidate>
                                <input type="hidden" name="command" value="change_password"/>
                                <div class="form-group mb-2">
                                    <label class="form-label"><fmt:message key="profile.label.currentPassword"/></label>
                                    <input id="currentPasswordInput" type="password" name="old_password" class="form-control"
                                           data-bs-toggle="popover" data-bs-placement="right" required minlength="8" maxlength="40"/>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label"><fmt:message key="profile.label.newPassword"/></label>
                                    <input id="passwordInput" type="password" name="new_password" class="form-control"
                                           data-bs-toggle="popover" data-bs-placement="right" required minlength="8" maxlength="40"/>
                                </div>
                                <div class="form-group mb-4">
                                    <label class="form-label"><fmt:message key="profile.label.repeatPassword"/></label>
                                    <input id="repeatPasswordInput" type="password" class="form-control"
                                           data-bs-toggle="popover" data-bs-placement="right" required minlength="8" maxlength="40"/>
                                </div>
                                <button type="submit" class="button button-primary" style="color: var(--cafe-secondary)"><fmt:message key="profile.action.change"/></button>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="account-balance" role="tabpanel">
                        <div class="card-body" style="margin-bottom: 18%">
                            <div class="d-flex justify-content-between display-6">
                                <span><fmt:message key="profile.text.balance"/>:</span>
                                <span><fmt:formatNumber value="${sessionScope.user.balance}" maxFractionDigits="2" minFractionDigits="2"/></span>
                            </div>
                            <hr class="border-light my-4" />
                            <form id="topUpBalanceForm" method="post" action="${pageContext.request.contextPath}/controller" novalidate>
                                <input type="hidden" name="command" value="top_up_balance"/>
                                <div class="form-group w-50">
                                    <label class="form-label"><fmt:message key="profile.action.topUpBalance"/></label>
                                    <div class="d-flex text-nowrap">
                                        <input id="topUpBalance" type="number" name="top_up_amount" class="form-control me-2"
                                               data-bs-toggle="popover" data-bs-placement="bottom" min="1" max="1000" step="0.1" required/>
                                        <button type="submit" class="button button-primary" style="color: var(--cafe-secondary)"><fmt:message key="profile.action.topUp"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../footer.jsp"/>

<fmt:message key="signup.error.valueMissing" var="valueMissing"/>
<fmt:message key="signup.error.namePatternMismatch" var="namePatternMismatch"/>
<fmt:message key="signup.error.surnamePatternMismatch" var="surnamePatternMismatch"/>
<fmt:message key="signup.error.emailPatternMismatch" var="emailPatternMismatch"/>
<fmt:message key="signup.error.passwordTooShort" var="passwordTooShort"/>
<fmt:message key="signup.error.passwordMismatch" var="passwordMismatch"/>
<fmt:message key="profile.error.rangeOverflow" var="amountRangeOverflow"/>
<fmt:message key="profile.error.rangeUnderflow" var="amountRangeUnderflow"/>


<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>

<script>
    const fileInput = document.getElementById('fileUpload');
    const imagePreview = document.getElementById('profileImage');

    fileInput.addEventListener('change', function (event) {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            const fileReader = new FileReader();

            fileReader.onload = function (event) {
                imagePreview.src = event.target.result;
            }
            fileReader.readAsDataURL(selectedFile);
        }
    });

    const nameInput = document.getElementById('nameInput');
    const surnameInput = document.getElementById('surnameInput');
    const emailInput = document.getElementById('emailInput');
    const currentPasswordInput = document.getElementById('currentPasswordInput');
    const passwordInput = document.getElementById('passwordInput');
    const repeatPasswordInput = document.getElementById('repeatPasswordInput');
    const topUpInput = document.getElementById('topUpBalance');

    const userDataForm = document.getElementById('userDataForm');
    const changePasswordForm = document.getElementById('changePasswordForm');
    const topUpBalanceForm = document.getElementById('topUpBalanceForm');

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

    function createPopover(input) {
        popover.dispose();
        return new bootstrap.Popover(input, { trigger: 'manual' });
    }

    userDataForm.addEventListener('submit', function (event) {
        if (!nameInput.validity.valid) {
            showNameError();
        } else if (!surnameInput.validity.valid) {
            showSurnameError();
        } else if (!emailInput.validity.valid) {
            showEmailError();
        }
        if (!nameInput.validity.valid || !surnameInput.validity.valid || !emailInput.validity.valid) {
            event.preventDefault();
        }
    });
    userDataForm.addEventListener('focusout', function () {
        popover.hide();
    });

    currentPasswordInput.addEventListener('input', function() {
        checkPasswordValidity(currentPasswordInput);
    });
    currentPasswordInput.addEventListener('focusin', function () {
        checkPasswordValidity(currentPasswordInput);
    })

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

    function checkPasswordValidity(input) {
        if (input.validity.valid) {
            popover.hide();
        } else {
            showPasswordError(input);
        }
    }

    function checkRepeatedPasswordValidity() {
        if (repeatPasswordInput.value === passwordInput.value) {
            popover.hide();
        } else {
            showRepeatPasswordError();
        }
    }

    function showPasswordError(input) {
        if (input.validity.valueMissing) {
            input.setAttribute('data-bs-content', "${valueMissing}");
        } else if (input.validity.tooShort) {
            input.setAttribute('data-bs-content', "${passwordTooShort}");
        }
        popover = createPopover(input);
        popover.show();
    }

    function showRepeatPasswordError() {
        repeatPasswordInput.setAttribute('data-bs-content', "${passwordMismatch}");
        popover = createPopover(repeatPasswordInput);
        popover.show();
    }

    changePasswordForm.addEventListener('submit', function (event) {
        if (!currentPasswordInput.validity.valid) {
            showPasswordError(currentPasswordInput);
        } else if (!passwordInput.validity.valid) {
            showPasswordError(passwordInput);
        } else if (repeatPasswordInput.value !== passwordInput.value) {
            showRepeatPasswordError();
        }

        if (!currentPasswordInput.validity.valid || !passwordInput.validity.valid
            || repeatPasswordInput.value !== passwordInput.value) {
            event.preventDefault();
        }
    });
    changePasswordForm.addEventListener('focusout', function () {
        popover.hide();
    });

    topUpInput.addEventListener('input', function () {
        checkAmountValidity();
    });
    topUpInput.addEventListener('focusin', function () {
        checkAmountValidity();
    });

    function checkAmountValidity() {
        if (topUpInput.validity.valid) {
            popover.hide();
        } else {
            showTopUpError();
        }
    }

    function showTopUpError() {
        if (topUpInput.validity.valueMissing) {
            topUpInput.setAttribute('data-bs-content', "${valueMissing}");
        } else if (topUpInput.validity.rangeOverflow) {
            topUpInput.setAttribute('data-bs-content', "${amountRangeOverflow}");
        } else if (topUpInput.validity.rangeUnderflow) {
            topUpInput.setAttribute('data-bs-content', "${amountRangeUnderflow}");
        }
        popover = createPopover(topUpInput);
        popover.show();
    }

    topUpBalanceForm.addEventListener('submit', function (event) {
        if (!topUpInput.validity.valid) {
            popover = createPopover(topUpInput);
            showTopUpError();

            event.preventDefault();
        }
    });
    topUpBalanceForm.addEventListener('focusout', function () {
        popover.hide();
    });
</script>

</body>
</html>
