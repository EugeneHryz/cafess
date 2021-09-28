<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body id="main-body">

<c:import url="header.jsp" />

<h1><fmt:message key="main.welcomeMessage" /></h1>

<c:import url="footer.jsp" />

</body>
</html>
