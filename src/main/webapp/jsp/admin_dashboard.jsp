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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colors.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_dashboard.css" />

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type='text/javascript'></script>

    <style>
        tr td:nth-child(6) {
            text-align: right;
        }
        tr th:first-child, tr td:first-child {
            padding-left: 12px;
            text-overflow: ellipsis;
        }
        tr th:last-child, tr td:last-child {
            padding-right: 12px;
        }
    </style>
</head>

<body id="admin-dashboard-body" style="background: var(--cafe-background);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    min-height: 100vh">

<c:import url="header.jsp"/>

<div class="container light-style mb-5">
    <h4 class="display-6 fs-3 my-2">
        Dashboard
    </h4>

    <div class="d-flex">
        <div class="nav nav-tabs w-100" role="tablist">
            <button id="addMenuItemButton" class="nav-link active" data-bs-toggle="tab" data-bs-target="#add-menu-item"
                    type="button" role="tab" aria-selected="true" aria-controls="add-menu-item">
                Add menu item
            </button>
            <button id="manageUsersButton" class="nav-link" data-bs-toggle="tab" data-bs-target="#manage-users"
                    type="button" role="tab" aria-controls="manage-users">
                Manage users
            </button>
        </div>
    </div>

    <div class="card border-top-0 rounded-0">

        <div class="row no-gutters row-bordered row-border-light">

            <div class="tab-content">
                <div class="tab-pane fade active show" id="add-menu-item" role="tabpanel">

                    <div class="row">
                        <div class="col-3">
                            <div class="card-body media d-flex flex-column align-items-start justify-content-between">
                                <img id="menuItemImage" src="${pageContext.request.contextPath}/content/no_image.png"
                                     alt="menu item image" class="rounded-circle" width="166" height="166" style="object-fit: cover;"/>

                                <form id="menuItemDataForm" method="post" action="${pageContext.request.contextPath}/controller"
                                      enctype="multipart/form-data" class="w-100">

                                    <input type="hidden" name="command" value="add_menu_item"/>
                                    <label class="btn btn-outline-primary mt-3 w-100">
                                        Select new image
                                        <input id="fileUpload" type="file" name="file" accept="image/*"
                                               class="account-settings-fileinput">
                                    </label>
                                </form>
                                <div class="text-light small mt-1">Allowed JPG, GIF or PNG. Max size of 2MB</div>
                            </div>
                        </div>
                        <div class="col-9">
                            <div class="card-body w-75">
                                <div class="form-group mb-2">
                                    <label class="form-label">Menu item name</label>
                                    <input type="text" form="menuItemDataForm" name="menu_item_name" class="form-control" value=""/>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label">Price</label>
                                    <input type="number" form="menuItemDataForm" name="price" step="0.1" class="form-control"/>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label">Category</label>
                                    <select form="menuItemDataForm" class="form-select" name="category">
                                        <c:forEach items="${applicationScope.menuCategoriesList}" var="item">
                                            <option value="${item.id}">${item.category}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group mb-2">
                                    <label class="form-label">Description</label>
                                    <textarea form="menuItemDataForm" name="description" class="form-control" rows="4" style="resize: none"></textarea>
                                </div>

                                ${menuItemAdded}
                                ${menuItemNotAdded}
                            </div>
                        </div>
                    </div>

                    <div class="ms-3 mb-3">
                        <button type="submit" form="menuItemDataForm" class="btn btn-primary">Save changes</button>
                    </div>
                </div>

                <div class="tab-pane fade" id="manage-users" role="tabpanel">
                    <div class="d-flex flex-column justify-content-between align-items-center">
                        <table class="table table-hover table-borderless align-middle" style="table-layout: fixed">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 19%;">Email</th>
                                <th scope="col" style="width: 19%">Name</th>
                                <th scope="col" style="width: 19%">Surname</th>
                                <th scope="col" style="width: 19%">Status</th>
                                <th scope="col" style="width: 12%">Role</th>
                                <th scope="col" style="width: 12%"></th>
                            </tr>
                            </thead>

                            <tbody id="tableBody">
                            </tbody>
                        </table>

                        <ul class="pagination"></ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<c:import url="footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>

<script>
    const fileInput = document.getElementById('fileUpload');

    const imagePreview = document.getElementById('menuItemImage');

    fileInput.addEventListener('change', function (event) {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const fileReader = new FileReader();

            fileReader.onload = function (event) {
                imagePreview.src = event.target.result;
            }
            fileReader.readAsDataURL(selectedFile);
        }
    });


    const URL = "${pageContext.request.contextPath}/ajax";
    const USERS_PER_PAGE = 4;
    const MAX_VISIBLE_PAGES = 4;

    loadNumberOfUsers();

    function loadPage(page) {
        const data = {
            command: 'go_to_user_page',
            page_number: page
        };
        $.getJSON(URL, data, function (responseData) {
            const tableBody = $('#tableBody');

            tableBody.fadeOut(120, function () {
                // clear previous table body contents
                tableBody.empty();

                $.each(responseData, function (index, user) {
                    // creating button for each user row
                    const toggleBanButton = $("<button>")
                        .addClass('btn btn-danger btn-sm')
                        .attr('id', 'toggleBanButton' + index)
                        .css('width', '75%');

                    if (user.status === 'BANNED') {
                        toggleBanButton.text('Unban');
                    } else {
                        toggleBanButton.text('Ban');
                    }

                    // setting click event on button to ban/unban users
                    toggleBanButton.on('click', function () {
                        const requestData = {
                            user_id: user.id
                        };
                        requestData.command = (tableBody.find('#userStatus' + index).text() === 'banned') ? 'unban_user' : 'ban_user';

                        $.post(URL, requestData).done(function (response) {
                            // update user status
                            tableBody.find('#userStatus' + index).text(response.status.toLowerCase());

                            // update button text
                            if (response.status === 'BANNED') {
                                toggleBanButton.text('Unban');
                            } else {
                                toggleBanButton.text('Ban');
                            }
                        });
                    });

                    // adding new row to the table with user data
                    $("<tr>").appendTo(tableBody)
                        .append($("<td>").attr('id', 'userEmail' + index).text(user.email))
                        .append($("<td>").attr('id', 'userName' + index).text(user.name))
                        .append($("<td>").attr('id', 'userSurname' + index).text(user.surname))
                        .append($("<td>").attr('id', 'userStatus' + index).text(user.status.toLowerCase()))
                        .append($("<td>").attr('id', 'userRole' + index).text(user.role.toLowerCase()))
                        .append($("<td>").html(toggleBanButton));
                });

                tableBody.fadeIn(120);
            });
        })
    }

    function loadNumberOfUsers() {
        const data = {
            command: 'get_user_count'
        }
        $.getJSON(URL, data, function (responseData) {

            const totalPages = Math.ceil(responseData / USERS_PER_PAGE);
            const visiblePages = (totalPages < MAX_VISIBLE_PAGES) ? totalPages : MAX_VISIBLE_PAGES;

            initPagination(totalPages, visiblePages);
        });
    }

    function initPagination(totalPages, visiblePages) {

        $('.pagination').twbsPagination({
            totalPages: totalPages,
            visiblePages: visiblePages,
            prev: '&laquo;',
            next: '&raquo;',
            firstClass: 'visually-hidden',
            lastClass: 'visually-hidden',

            onPageClick: function (event, page) {
                loadPage(page);
            }
        });
    }
</script>
</body>
</html>
