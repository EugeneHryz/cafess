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
    <title><fmt:message key="title.orderHistory"/></title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/order_history.css" rel="stylesheet"/>
</head>
<body id="order-history-body" style="background: var(--cafe-background);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    min-height: 100vh">

<c:import url="../header.jsp"/>

<div class="mt-5 mb-3" style="width: 38%">

    <c:forEach items="${requestScope.ordersSublist}" var="item" varStatus="status">
        <div class="card mb-4" style="border-radius: 16px;">
            <div class="card-header d-flex justify-content-between position-relative" style="border-top-left-radius: 16px; border-top-right-radius: 16px; line-height: 1em;">
                <div>
                    <h3 class="fs-3"><fmt:formatDate value="${item.date}" type="both"/></h3>
                    <span class="fs-6 text-muted"><fmt:message key="orderHistory.text.readyAt"/> <fmt:formatDate value="${item.pickUpTime}" type="time" timeStyle="short"/></span>
                </div>
                <a id="openReview${status.count}" class="star-text position-absolute top-0 end-0 m-2">
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

                <a id="expand-text" class="d-block fs-6 grey-text" data-bs-toggle="collapse" href="#collapsingText${status.count}" role="button">
                    <fmt:message key="dashboard.label.description"/></a>
                <hr class="border-light my-2"/>

                <div class="d-flex justify-content-between fs-4">
                    <span class="fw-light"><fmt:message key="orderHistory.text.total"/>:</span>
                    <span class="fw-normal"><fmt:formatNumber value="${item.totalPrice}" maxFractionDigits="2" minFractionDigits="2"/></span>
                </div>
                <hr class="border-light my-2"/>

                <div class="d-flex justify-content-between fs-4">
                    <span class="fw-light"><fmt:message key="orderHistory.text.status"/>:</span>
                    <span id="orderStatus${status.count}" title="${item.orderStatus}" class="fw-normal"><fmt:message key="orderHistory.text.${fn:toLowerCase(item.orderStatus.name())}"/></span>
                </div>
            </div>
        </div>
        <div class="modal fade" id="reviewModal${status.count}" tabindex="-1" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="orderHistory.text.review"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="saveReviewForm${status.count}" method="post" action="${pageContext.request.contextPath}/controller" novalidate>
                            <input type="hidden" name="command" value="save_review"/>
                            <input type="hidden" name="order_id" value="${item.id}"/>
                            <p><fmt:message key="orderHistory.text.rating"/>:</p>
                            <div class="d-flex">
                                <div class="rating">
                                    <input name="rating" id="review${status.count}star5" value="5" type="radio" ${item.review.rating == 5 ? 'checked' : ''}/>
                                    <label class="far fa-star fa-2x" for="review${status.count}star5"></label>

                                    <input name="rating" id="review${status.count}star4" value="4" type="radio" ${item.review.rating == 4 ? 'checked' : ''}/>
                                    <label class="far fa-star fa-2x" for="review${status.count}star4"></label>

                                    <input name="rating" id="review${status.count}star3" value="3" type="radio" ${item.review.rating == 3 ? 'checked' : ''}/>
                                    <label class="far fa-star fa-2x" for="review${status.count}star3"></label>

                                    <input name="rating" id="review${status.count}star2" value="2" type="radio" ${item.review.rating == 2 ? 'checked' : ''}/>
                                    <label class="far fa-star fa-2x" for="review${status.count}star2"></label>

                                    <input name="rating" id="review${status.count}star1" value="1" type="radio" ${item.review.rating == 1 ? 'checked' : ''}/>
                                    <label class="far fa-star fa-2x" for="review${status.count}star1"></label>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="commentText" class="col-form-label"><fmt:message key="orderHistory.text.comment"/>:</label>
                                <textarea name="comment" id="commentText" rows="4" class="form-control" required maxlength="150">${item.review.comment}</textarea>
                            </div>
                        </form>
                        <c:if test="${not empty item.review}">
                            <span><fmt:message key="orderHistory.text.lastEdited"/> <fmt:formatDate value="${item.review.date}" type="both"/></span>
                        </c:if>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="button button-outline-primary" data-bs-dismiss="modal">
                            <fmt:message key="orderHistory.action.close"/></button>
                        <button type="submit" form="saveReviewForm${status.count}" class="button button-primary">
                            <fmt:message key="orderHistory.action.save"/></button>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${requestScope.orderCount == 0}">
    <p class="display-6"><fmt:message key="orderHistory.text.noOrders"/></p>
</c:if>

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
    <input type="hidden" name="command" value="go_to_order_history_page"/>
    <input id="pageNumber" type="text" name="page"/>
</form>

<ul class="pagination mb-4"></ul>

<c:import url="../footer.jsp"/>

<fmt:message key="orderHistory.error.valueMissing" var="valueMissing"/>
<fmt:message key="orderHistory.error.commentPatternMismatch" var="commentPatternMismatch"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>

<script>
    $(document).ready(function () {
        const totalOrders = ${requestScope.orderCount};
        const ordersPerPage = 4;

        if (totalOrders > 0) {
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
                firstClass: 'visually-hidden',
                lastClass: 'visually-hidden',
                anchorClass: 'page-button',
                pageClass: 'item-page',
                prevClass: 'item-page prev',
                nextClass: 'item-page next',

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

                if ($('#orderStatus' + id).attr('title') === 'PICKED_UP') {
                    $('#reviewModal' + id).modal('show');
                }
            });

            if ("${requestScope.saveReviewMessage}") {
                const toast = $('#liveToast');

                toast.find('.toast-body').text("${requestScope.saveReviewMessage}");
                toast.toast('show');
            }
        }

        const commentInputs = document.getElementsByTagName('textarea');

        let popover = new bootstrap.Popover(commentInputs[0], { trigger: 'manual' });

        for (let item of commentInputs) {
            item.addEventListener('input', function () {
                checkCommentValidity(item);
            });
            item.addEventListener('focusin', function () {
                checkCommentValidity(item);
            });

            const form = $(item).parents('form')[0];
            $(form).on('submit', function (event) {
                const valid = item.validity.valid && validateTextArea(item);
                if (!valid) {
                    showCommentError(item);
                    event.preventDefault();
                }
            });
            $(form).on('focusout', function () {
                popover.hide();
            });
        }

        function checkCommentValidity(comment) {
            const valid = comment.validity.valid && validateTextArea(comment);
            if (valid) {
                popover.hide();
            } else {
                showCommentError(comment);
            }
        }

        function validateTextArea(textArea) {
            const pattern = new RegExp("^[^<>]{5,}$");
            let isValid = false;
            $.each($(textArea).val().split("\n"), function () {
                isValid = pattern.test(this);
                if (!isValid) {
                    return isValid;
                }
            });
            return isValid;
        }

        function showCommentError(comment) {
            if (comment.validity.valueMissing) {
                $(comment).attr('data-bs-content', "${valueMissing}");
            } else if (!validateTextArea(comment)) {
                $(comment).attr('data-bs-content', "${commentPatternMismatch}");
            }
            popover = createPopover(comment);
            popover.show();
        }

        function createPopover(input) {
            popover.dispose();
            return new bootstrap.Popover(input, { trigger: 'manual' });
        }
    });
</script>

</body>
</html>
