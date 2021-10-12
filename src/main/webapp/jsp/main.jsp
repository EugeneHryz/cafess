<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="page_content" />

<html>
<head>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body id="main-body">

<c:import url="header.jsp" />

<div class="album py-5">
    <div class="container" style="width: 950px">

        <div class="d-flex justify-content-between mb-4">
            <div>
                <span>Sort by</span>
                <select id="sortSelect" class="form-select w-auto">
                    <option value="price_ascending"
                    ${sessionScope.menuItemsSortOrder eq 'price_ascending' ? 'selected' : ''}>Price: low to high</option>
                    <option value="price_descending"
                    ${sessionScope.menuItemsSortOrder eq 'price_descending' ? 'selected' : ''}>Price: high to low</option>
                </select>
            </div>
            <div>
                <span>Category</span>
                <select id="categorySelect" class="form-select w-auto">
                    <option value="0">All</option>
                    <c:forEach items="${applicationScope.menuCategoriesList}" var="item">
                        <option value="${item.id}" ${sessionScope.menuItemsCurrentCategory.id eq item.id ? 'selected' : ''}>${item.category}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div id="itemsContainer" class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">

            <div id="totalNumberOfItems" style="display: none">${sessionScope.menuItemCount}</div>
            <div id="currentPageNumber" style="display: none">${sessionScope.menuItemsPageNumber}</div>

            <c:forEach items="${sessionScope.menuItemsSublist}" var="item" varStatus="status">
                <div class="col">
                    <div class="card">
                        <c:choose>
                            <c:when test="${not empty item.imagePath}">
                                <img src="${pageContext.request.contextPath}/files/menu_items/${item.imagePath}" class="card-img-top"
                                     alt="menu item image" height="160" style="object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/content/no_image.png" class="card-img-top"
                                     alt="no image" height="160" style="object-fit: cover;">
                            </c:otherwise>
                        </c:choose>

                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start mb-2" style="line-height: 1.2">
                                <span id="itemName" class="fs-5" style="height: 2em;">${item.name}</span>
                                <span id="itemPrice" class="fs-4">${item.price}</span>
                            </div>
                            <span id="itemCategoryId" style="display: none">${item.categoryId}</span>

                            <div class="collapse" id="collapsingText${status.count}">${item.description}</div>
                            <a class="d-block mb-2" data-bs-toggle="collapse" href="#collapsingText${status.count}"
                               aria-controls="collapsingText${status.count}" role="button" >Description</a>

                            <a href="#" class="btn btn-primary">Add to cart</a>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <form id="goToAnotherPage" action="${pageContext.request.contextPath}/controller" style="display: none">
                <input type="hidden" name="command" value="go_to_menu_page"/>
                <input id="pageNumber" type="text" name="page_number" />
            </form>

            <form method="post" id="changeSortOrder" action="${pageContext.request.contextPath}/controller" style="display: none">
                <input type="hidden" name="command" value="change_sort_order"/>
                <input id="sortOrder" type="text" name="sort_order" value="price_ascending" />
            </form>

            <form method="post" id="changeCategory" action="${pageContext.request.contextPath}/controller" style="display: none">
                <input type="hidden" name="command" value="change_current_category"/>
                <input id="categoryId" type="text" name="category_id" />
            </form>

        </div>
    </div>
</div>

<ul class="pagination mb-3"></ul>

<c:import url="footer.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>

<script>
    const totalNumberOfItems = $('#totalNumberOfItems').text();
    const sublist = $('.col');

    const totalPages = Math.ceil(totalNumberOfItems / 8);
    const numberOfVisiblePages = (totalPages > 4) ? 4 : totalPages;

    const changePageForm = $('#goToAnotherPage');
    const changeSortOrderForm = $('#changeSortOrder');
    const changeCategoryForm = $('#changeCategory');

    const sortSelect = $('#sortSelect');
    const categorySelect = $('#categorySelect')

    const pagination = $('.pagination');
    pagination.twbsPagination({
        totalPages: totalPages,
        visiblePages: numberOfVisiblePages,
        initiateStartPageClick: false,
        prev: '&laquo;',
        next: '&raquo;',

        onPageClick: function (event, page) {
            goToAnotherPage(page);
        }
    });

    const currentPage = Number($('#currentPageNumber').text());
    pagination.twbsPagination('changePage', currentPage);

    sortSelect.on('change', function () {
        const newSortOrder = sortSelect.val();
        changeSortOrderForm.find('#sortOrder').attr('value', newSortOrder);
        changeSortOrderForm.submit();
    });

    categorySelect.on('change', function() {
        const newCategoryId = categorySelect.val();
        changeCategoryForm.find('#categoryId').attr('value', newCategoryId);
        changeCategoryForm.submit();
    });

    function goToAnotherPage(page) {
        changePageForm.find('#pageNumber').attr('value', page);
        changePageForm.submit();
    }
</script>

</body>
</html>
