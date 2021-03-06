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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>

</head>
<body>

<div id="header" style="background-color: var(--cafe-primary)">

    <nav class="navbar navbar-expand-md navbar-dark navbar-sl">
        <div class="container-fluid justify-content-end">

            <a class="navbar-brand fs-2 fw-normal"
               href="${pageContext.request.contextPath}/controller?command=go_to_menu_page&page=1">Cafess</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon d-flex"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/controller?command=go_to_menu_page&page=1"><fmt:message
                                key="header.text.home"/></a>
                    </li>
                </ul>
            </div>

            <a id="shoppingCart" href="${pageContext.request.contextPath}/controller?command=go_to_checkout_page"
               class="position-relative ${not empty sessionScope.shoppingCart ? '' : "visually-hidden"} me-2"
               style="text-decoration: none">
                <img src="${pageContext.request.contextPath}/content/shopping_bag.svg" alt="shopping bag"
                     class="position-relative"
                     height="40" width="40">
                <span id="shoppingCartSize" class="position-absolute translate-middle badge rounded-pill bg-danger"
                      style="top: 15%; left: 90%">${sessionScope.shoppingCartSize}
                </span>
                </img>
            </a>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="#userProfileMenu" class="d-block link-dark ms-3" role="button" data-bs-toggle="offcanvas"
                       aria-expanded="false" aria-controls="userProfileMenu">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user.profileImagePath}">
                                <img src="${pageContext.request.contextPath}/files/users/${sessionScope.user.profileImagePath}"
                                     alt="profile image" class="rounded-circle" width="46" height="46"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/content/no_profile_picture.png"
                                     alt="no profile picture" class="rounded-circle" height="46" width="46"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="button button-outline-secondary me-2" href="${pageContext.request.contextPath}/controller?command=go_to_login_page">
                        <fmt:message key="header.action.login"/>
                    </a>
                    <a class="button button-secondary" href="${pageContext.request.contextPath}/controller?command=go_to_signup_page">
                        <fmt:message key="header.action.signup"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</div>

<div class="offcanvas offcanvas-end" style="width: 16%" id="userProfileMenu" aria-labelledby="userProfileMenuOffcanvas">
    <div class="offcanvas-header">
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <ul class="navbar-nav fs-4 fw-light">

            <li><a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page"
                   class="nav-link px-2 link-dark"><fmt:message key="header.text.profile"/></a></li>

            <c:if test="${sessionScope.role eq 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/controller?command=go_to_admin_dashboard_page"
                       class="nav-link px-2 link-dark"><fmt:message key="header.text.dashboard"/></a>
                </li>
                <li><a href="${pageContext.request.contextPath}/controller?command=go_to_orders_page&page=1"
                       class="nav-link px-2 link-dark"><fmt:message key="header.text.orders"/></a>
                </li>
            </c:if>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=go_to_order_history_page&page=1"
                   class="nav-link px-2 link-dark">
                    <fmt:message key="header.text.orderhistory"/>
                </a>
            </li>
            <hr/>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=log_out" class="nav-link px-2 link-dark">
                    <span class="text-danger"><fmt:message key="header.action.logout"/></span>
                </a>
            </li>
        </ul>
    </div>
</div>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.js"></script>

</body>
</html>


