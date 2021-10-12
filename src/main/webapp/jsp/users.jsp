<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet" />

    <link href="${pageContext.request.contextPath}/css/users.css" rel="stylesheet" />
</head>
<body id="users-body">

<table class="table table-hover align-middle">
    <thead>
    <tr>
        <th scope="col">Email</th>
        <th scope="col">Name</th>
        <th scope="col">Status</th>
        <th scope="col">Test</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${sessionScope.usersList}" var="user">
            <tr>
                <c:choose>
                    <c:when test="${not empty user.email}">
                        <td>${user.email}</td>
                    </c:when>
                    <c:otherwise>
                        <td class="text-muted">No email</td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty user.name}">
                        <td>${user.name}</td>
                    </c:when>
                    <c:otherwise>
                        <td class="text-muted">No name</td>
                    </c:otherwise>
                </c:choose>
                <td>${user.status.name().toLowerCase()}</td>
                <td style="height: 50px"></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<ul class="pagination" style="margin-left: 38%"></ul>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>

<script>

    const pagination = $('.pagination');

    pagination.twbsPagination({
        totalPages: 8,
        visiblePages: 4,
        initiateStartPageClick: false,
        prev: '&laquo;',
        next: '&raquo;',
        firstClass: 'visually-hidden',
        lastClass: 'visually-hidden',

        onPageClick: function (event, page) {

        }
    });
</script>
</body>
</html>
