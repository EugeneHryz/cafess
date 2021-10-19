<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile_settings.css"/>

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type='text/javascript'></script>

</head>

<body id="profile-settings-body" style="background: var(--cafe-background);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    min-height: 100vh;">

<c:import url="header.jsp"/>

<div class="d-block light-style mb-5">
    <h4 class="display-6 fs-3 my-2">
        Profile settings
    </h4>

    <div class="card overflow-hidden">
        <div class="row">

            <div class="col-3 d-flex align-items-start">
                <div class="nav flex-column nav-pills align-items-stretch" role="tablist" aria-orientation="vertical">
                    <button class="nav-link active" data-bs-toggle="pill" data-bs-target="#account-general"
                            type="button" style="border-top-left-radius: 0; border-bottom-left-radius: 0"
                            role="tab" aria-selected="true">General
                    </button>
                    <button class="nav-link" data-bs-toggle="pill" data-bs-target="#account-change-password"
                            type="button" style="border-top-left-radius: 0; border-bottom-left-radius: 0"
                            role="tab" aria-selected="false">Change password
                    </button>
                    <button class="nav-link" data-bs-toggle="pill" data-bs-target="#account-balance"
                            type="button" style="border-top-left-radius: 0; border-bottom-left-radius: 0"
                            role="tab" aria-selected="false">Balance
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

                                    <label class="btn btn-outline-primary me-1">
                                        Select new image
                                        <input id="fileUpload" type="file" name="file" accept="image/*"
                                               class="account-settings-fileinput">
                                    </label>
                                    <button type="submit" class="btn btn-primary">Save</button>

                                    <div class="text-muted small mt-1" id="text-light">Allowed JPG, GIF or PNG. Max size
                                        of 2MB
                                    </div>
                                </div>
                            </div>
                        </form>
                        <hr class="border-light m-0" />

                        <div class="card-body" style="width: 70%">
                            <form id="userDataForm" method="post"
                                  action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="edit_user_profile"/>
                                <div class="form-group mb-2">
                                    <label class="form-label">First name</label>
                                    <input type="text" name="name" class="form-control"
                                           value="${sessionScope.user.name}">
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label">Surname</label>
                                    <input type="text" name="surname" class="form-control"
                                           value="${sessionScope.user.surname}">
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control"
                                           value="${sessionScope.user.email}">
                                    <div class="alert alert-warning mt-3">
                                        Your email is not confirmed. Please check your inbox.<br>
                                        <a href="#">Resend confirmation</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <button type="submit" form="userDataForm" class="btn btn-primary ms-3 mb-3">Save changes</button>
                    </div>

                    <div class="tab-pane fade" id="account-change-password" role="tabpanel">
                        <div class="card-body">

                            <form method="post" action="${pageContext.request.contextPath}/controller" class="mb-0">
                                <input type="hidden" name="command" value="change_password"/>
                                <div class="form-group mb-2">
                                    <label class="form-label">Current password</label>
                                    <input type="password" name="old_password" class="form-control"/>
                                </div>

                                <div class="form-group mb-2">
                                    <label class="form-label">New password</label>
                                    <input type="password" name="new_password" class="form-control"/>
                                </div>

                                <div class="form-group mb-3">
                                    <label class="form-label">Repeat new password</label>
                                    <input type="password" class="form-control"/>
                                </div>
                                <button type="submit" class="btn btn-primary">Change</button>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="account-balance" role="tabpanel">
                        <div class="card-body mb-4">
                            <div class="d-flex justify-content-between display-6">
                                <span>Balance:</span>
                                <span><fmt:formatNumber value="${sessionScope.user.balance}" maxFractionDigits="2" minFractionDigits="2"/></span>
                            </div>
                            <hr class="border-light mt-4 mb-2" />
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="top_up_balance"/>
                                <div class="form-group w-50">
                                    <label class="form-label">Top up your balance</label>
                                    <div class="d-flex text-nowrap">
                                        <input type="number" name="top_up_amount" class="form-control me-2" min="0.1" max="100" step="0.1"/>
                                        <button type="submit" class="btn btn-primary">Top up</button>
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

<c:import url="footer.jsp"/>

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
</script>

</body>
</html>
