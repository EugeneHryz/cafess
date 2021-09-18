<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="@{not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

<c:import url="header.jsp"/>

<div class="form-signin">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="log_in"/>
        <h1 class="h3 mb-3"><fmt:message key="login.welcomeMessage"/></h1>

        <div class="form-floating">
            <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
            <label for="floatingInput"><fmt:message key="login.label.email"/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
            <label for="floatingPassword"><fmt:message key="login.label.password"/></label>
        </div>

        ${invalidLoginOrPassword}

        <button class="w-100 mt-2 btn btn-lg btn-primary" type="submit"><fmt:message
                key="login.button.submit"/></button>
    </form>

    <form action="controller">
        <input type="hidden" name="command" value="go_to_signup_page"/>
        <fmt:message key="login.label.signup"/>
        <button class="w-100 btn-dark btn-lg btn-primary" type="submit"><fmt:message
                key="login.button.signup"/></button>
    </form>
</div>

<c:import url="footer.jsp"/>

<%--    add footer--%>

</div>

</body>
</html>
