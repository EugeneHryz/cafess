<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <title><fmt:message key="title.order"/></title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>

    <script src="https://kit.fontawesome.com/8d5a2ad3e4.js" crossorigin="anonymous"></script>
</head>
<body style="min-height: 100vh; background-color: var(--cafe-background); display: flex; flex-direction: column; justify-content: center; align-items: center">

<p class="display-6"><fmt:message key="checkout.text.orderFail"/></p>
<i class="fas fa-times-circle fa-7x" style="color: #e85555"></i>

<a class="button button-primary" style="text-decoration: none; margin-top: 5%" href="${pageContext.request.contextPath}/controller?command=go_to_menu_page&page=1">
    <fmt:message key="orderFail.text.goToMainPage"/>
</a>

</body>
</html>
