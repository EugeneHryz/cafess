<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session" />
    <c:set var="currentLocale" value="${sessionScope.locale}" scope="page" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>

<div id="header" ${not empty sessionScope.user ? "style='background-color: var(--cafe-primary)'" : ""}>

    <nav class="navbar navbar-expand-md navbar-dark navbar-sl">
        <div class="container-fluid justify-content-end py-2">

            <c:if test="${not empty sessionScope.user}">
                <a class="navbar-brand fs-2 fw-normal" href="#">Cafess</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Home</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Dropdown
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="d-flex mb-0">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-light me-4" type="submit">Search</button>
                    </form>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/controller" class="d-flex mb-0">
                <input type="hidden" name="command" value="change_locale" />
                <select class="form-select" name="locale" aria-label="select locale" onchange="this.form.submit()">
                    <option value="ru_RU" ${currentLocale eq 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="en_US" ${currentLocale eq 'en_US' ? 'selected' : ''}>English</option>
                </select>
            </form>

            <c:if test="${not empty sessionScope.user}">
                <a href="#userProfileMenu" class="d-block link-dark ms-3" role="button" data-bs-toggle="offcanvas" aria-expanded="false" aria-controls="userProfileMenu">
                    <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="user" width="36" height="36" class="rounded-circle">
                </a>
            </c:if>

        </div>
    </nav>
</div>

<div class="offcanvas offcanvas-end" style="width: 15%" id="userProfileMenu" aria-labelledby="userProfileMenuOffcanvas">
    <div class="offcanvas-header">
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <ul class="navbar-nav fs-4 fw-light">
            <li><a href="#" class="nav-link px-2 link-dark">Profile</a></li>
            <li><a href="#" class="nav-link px-2 link-dark">Order history</a></li>
            <hr/>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=log_out" class="nav-link px-2 link-dark">
                    <span class="text-danger">Log out</span>
                </a>
            </li>
        </ul>
    </div>
</div>


<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.min.js">
</script>

</body>
</html>


