<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<h1>Error occurred</h1>

${sessionScope.exception}

</body>
</html>