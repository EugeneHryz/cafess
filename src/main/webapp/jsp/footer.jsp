<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="@{not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="page_content" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<header class="header">Bye!</header>
