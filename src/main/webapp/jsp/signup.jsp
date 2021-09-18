<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="@{not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="page_content" />
<html>
<head>
    <title>Sign up</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="sign_up" />

    <fmt:message key="signup.welcomeMessage"/><br/><br/>
    <label for="name"><fmt:message key="signup.label.name"/></label>
    <input id="name" type="text" name="name"/><br/><br/>

    <label for="surname"><fmt:message key="signup.label.surname"/></label>
    <input id="surname" type="text" name="surname"/><br/><br/>

    <label for="email"><fmt:message key="signup.label.email"/></label>
    <input id="email" type="email" name="email"/><br/><br/>

    <label for="password"><fmt:message key="signup.label.password"/></label>
    <input id="password" type="password" name="password"/><br/><br/>

<%--There's no need to put password twice in the request--%>
    <label for="password"><fmt:message key="signup.label.repeatPassword"/></label>
    <input id="repeatPassword" type="password"/><br/>
    <br/>
    ${loginAlreadyExists}
    ${invalidSignUpData}
    <br/>
    <fmt:message key="signup.button.signup" var="buttonValue"/>
    <input type="submit" value="${buttonValue}"/>

</form>

</body>
</html>
