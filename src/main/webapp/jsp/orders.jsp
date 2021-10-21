<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/colors.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/orders.css" rel="stylesheet"/>

</head>
<body id="orders-body" style="background: #EFEFEF;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    min-height: 100vh">

<c:import url="header.jsp"/>

<div class="mt-5 mb-3" style="width: 38%">

    <c:forEach items="${requestScope.ordersSublist}" var="item" varStatus="status">
        <div class="card mb-4" style="border-radius: 16px;">
            <div class="card-header d-flex justify-content-between position-relative" style="border-top-left-radius: 16px; border-top-right-radius: 16px; line-height: 1em;">
                <div>
                    <h3 class="fs-3"><fmt:formatDate value="${item.date}" type="both"/></h3>
                    <span class="fs-6 text-muted">Need to be ready at <fmt:formatDate value="${item.pickUpTime}" type="time" timeStyle="short"/></span>
                </div>
                <a id="openReview${status.count}" class="grey-text position-absolute top-0 end-0 m-2">
                    <i class="far fa-star fa-2x"></i>
                </a>
            </div>
            <div class="card-body">
                <div class="collapse" id="collapsingText${status.count}">
                    <c:forEach items="${item.menuItems}" var="entry" varStatus="loopStatus">
                        ${entry.key.name} x${entry.value}
                        <c:if test="${loopStatus.count < item.menuItems.size()}">, </c:if>
                    </c:forEach>
                </div>

                <a id="expand-text" class="d-block fs-6 grey-text" data-bs-toggle="collapse" href="#collapsingText${status.count}" role="button">Description</a>
                <hr class="border-light my-2"/>

                <div class="d-flex justify-content-between fs-4">
                    <span class="fw-light">User Id:</span>
                    <span class="fw-normal">${item.userId}</span>
                </div>
                <hr class="border-light my-2"/>

                <div class="d-flex justify-content-between fs-4">
                    <span class="fw-light">Total:</span>
                    <span class="fw-normal"><fmt:formatNumber value="${item.totalPrice}" maxFractionDigits="2" minFractionDigits="2"/></span>
                </div>
                <hr class="border-light my-2"/>

                <div class="d-flex justify-content-between fs-4">
                    <span class="fw-light">Status:</span>
                    <select id="status${item.id}" class="form-select w-auto">
                        <option value="cooking" ${item.orderStatus.name() == 'COOKING' ? 'selected' : ''}>Cooking</option>
                        <option value="ready" ${item.orderStatus.name() == 'READY' ? 'selected' : ''}>Ready</option>
                        <option value="picked_up" ${item.orderStatus.name() == 'PICKED_UP' ? 'selected' : ''}>Picked up</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="modal fade" id="reviewModal${status.count}" tabindex="-1" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Review</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                            <p>Rating:</p>
                            <div class="d-flex">
                                <div class="rating mb-2">
                                    <i class="far fa-star fa-2x ${item.review.rating == 5 ? 'star' : ''}"></i>
                                    <i class="far fa-star fa-2x ${item.review.rating == 4 ? 'star' : ''}"></i>
                                    <i class="far fa-star fa-2x ${item.review.rating == 3 ? 'star' : ''}"></i>
                                    <i class="far fa-star fa-2x ${item.review.rating == 2 ? 'star' : ''}"></i>
                                    <i class="far fa-star fa-2x ${item.review.rating == 1 ? 'star' : ''}"></i>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="commentText" class="col-form-label">Comment:</label>
                                <textarea name="comment" id="commentText" rows="4" class="form-control" readonly>${item.review.comment}</textarea>
                            </div>
                        <c:if test="${not empty item.review}">
                            <span>Last edited <fmt:formatDate value="${item.review.date}" type="both"/></span>
                        </c:if>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="me-auto">Cafess</strong>
            <button id="closeToast" type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body"></div>
    </div>
</div>

<form id="goToAnotherPage" action="${pageContext.request.contextPath}/controller" style="display: none">
    <input type="hidden" name="command" value="go_to_order_page"/>
    <input id="pageNumber" type="text" name="page"/>
</form>

<form id="changeOrderStatus" method="post" action="${pageContext.request.contextPath}/controller" style="display: none">
    <input type="hidden" name="command" value="change_order_status"/>
    <input id="orderId" type="text" name="order_id"/>
    <input id="orderStatus" type="text" name="order_status"/>
</form>

<ul class="pagination mb-4"></ul>

<c:import url="footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>

<script>
    $(document).ready(function () {
        const totalOrders = ${requestScope.orderCount};
        const ordersPerPage = 4;

        const totalPages = Math.ceil(totalOrders / ordersPerPage);
        const visiblePages = (totalPages > 4) ? 4 : totalPages;

        const goToPageForm = $('#goToAnotherPage');

        const pagination = $('.pagination');
        pagination.twbsPagination({
            totalPages: totalPages,
            visiblePages: visiblePages,
            initiateStartPageClick: false,
            prev: '&laquo;',
            next: '&raquo;',

            onPageClick: function (event, page) {
                goToAnotherPage(page);
            }
        });

        const currentPage = ${requestScope.ordersPageNumber};
        pagination.twbsPagination('changePage', currentPage);

        function goToAnotherPage(page) {
            goToPageForm.find('#pageNumber').attr('value', page);
            goToPageForm.submit();
        }

        $('.card-header').find('a').click(function () {
            const id = $(this).attr('id').slice(10);

            $('#reviewModal' + id).modal('show');
        });

        $('select').on('change', function () {
            const orderId = $(this).attr('id').slice(6);

            const changeOrderStatus = $('#changeOrderStatus');
            changeOrderStatus.find('#orderId').attr('value', orderId);
            changeOrderStatus.find('#orderStatus').attr('value', $(this).val());

            changeOrderStatus.submit();
        });
    });
</script>

</body>
</html>

