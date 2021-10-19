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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">

</head>
<body id="checkout-body" style="background: var(--cafe-background);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;">

<c:import url="header.jsp"/>

<div class="card my-5" style="width: 42%">

    <ul class="list-group list-group-flush">
        <c:forEach items="${sessionScope.shoppingCart}" var="item">
            <li class="list-group-item position-relative">
                <div class="d-flex justify-content-between">
                    <div>
                        <h3 class="fs-3 fw-light">${item.key.name}</h3>
                        <span class="fs-5 fw-light">x${item.value}</span>
                    </div>
                    <span class="fs-3 me-4"><fmt:formatNumber value="${item.key.price * item.value}" maxFractionDigits="2" minFractionDigits="2"/></span>
                </div>
                <div class="visually-hidden" id="item${item.key.id}"></div>
                <button type="button" class="remove-button btn-close position-absolute end-0 top-0" aria-label="Close"></button>
            </li>
        </c:forEach>
    </ul>

    <div class="card-body" style="background-color: #f5d5d5;">
        <div class="d-flex justify-content-between fs-3 mb-3">
            <span class="fw-light">Total:</span>
            <span id="orderTotal" class="me-3"><fmt:formatNumber value="${sessionScope.orderTotal}" maxFractionDigits="2" minFractionDigits="2"/></span>
        </div>
        <hr class="border-light my-3"/>

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="place_order"/>
            <div class="form-group mb-3">
                <label class="form-label">When you want your order to be ready:</label>
                <select class="form-select w-auto" name="pickup_time">
                    <c:forEach items="${requestScope.pickupTimeList}" var="time" varStatus="status">
                        <option value="${time}">${time}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="float: right">
                <button type="submit" class="btn btn-primary">Place order</button>
            </div>
        </form>
    </div>

</div>

<c:import url="footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>

<script>
    $('.remove-button').click(function () {
        const item = $(this).parent('li');

        removeListItem(item);
    });

    const URL = "${pageContext.request.contextPath}/ajax";

    function removeListItem(item) {
        item.animate({opacity: '0'}, 150, function () {
            item.animate({height: '0px'}, 150, function () {

                const itemId = item.find('.visually-hidden').attr('id').slice(4);
                removeListItemFromSession(itemId);

                item.remove();
            });
        });
    }

    function removeListItemFromSession(itemId) {
        const requestData = {
            command: 'remove_item_from_cart',
            item_id: itemId
        }

        $.post(URL, requestData).done(function (response) {

            $('#orderTotal').text(Number(response.order_total).toFixed(2));
            $('#shoppingCartSize').text(response.shopping_cart_size);
        });
    }
</script>
</body>
</html>
