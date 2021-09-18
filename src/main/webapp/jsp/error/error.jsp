<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Error occurred</h1>

<c:out value="${sessionScope.exception}" />

</body>
</html>
