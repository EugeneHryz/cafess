<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="page_content"/>

<html>
<head>
    <title><fmt:message key="title.dashboard"/></title>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-grid.min.css" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_dashboard.css"/>
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
        <fmt:message key="title.dashboard"/>
    </h4>

    <div class="d-flex">
        <div class="nav nav-tabs w-100" role="tablist">
            <button id="addMenuItemButton" class="navigation-link-h active" data-bs-toggle="tab" data-bs-target="#add-menu-item"
                    type="button" role="tab" aria-selected="true" aria-controls="add-menu-item">
                <fmt:message key="dashboard.text.addMenuItem"/>
            </button>
            <button id="manageUsersButton" class="navigation-link-h" data-bs-toggle="tab" data-bs-target="#manage-users"
                    type="button" role="tab" aria-controls="manage-users">
                <fmt:message key="dashboard.text.manageUsers"/>
            </button>
            <button id="manageMenuButton" class="navigation-link-h" data-bs-toggle="tab" data-bs-target="#manage-menu"
                    type="button" role="tab" aria-controls="manage-menu">
                <fmt:message key="dashboard.text.manageMenu"/>
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
                                      enctype="multipart/form-data" class="w-100" novalidate>

                                    <input type="hidden" name="command" value="add_menu_item"/>
                                    <label class="button button-outline-primary mt-3 w-100">
                                        <fmt:message key="dashboard.text.selectImage"/>
                                        <input id="fileUpload" type="file" name="file" accept="image/*"
                                               class="account-settings-fileinput">
                                    </label>
                                </form>
                                <div class="text-light small"><fmt:message key="profile.text.allowImage"/></div>
                            </div>
                        </div>
                        <div class="col-9">
                            <div class="card-body w-75">
                                <div class="form-group mb-2">
                                    <label for="nameInput" class="form-label"><fmt:message key="dashboard.label.menuItemName"/></label>
                                    <input id="nameInput" type="text" form="menuItemDataForm" name="menu_item_name" class="form-control"
                                           data-bs-toggle="popover" required pattern="^[\p{L} ]{4,30}$"/>
                                </div>
                                <div class="form-group mb-2">
                                    <label for="priceInput" class="form-label"><fmt:message key="dashboard.label.price"/></label>
                                    <input id="priceInput" type="number" form="menuItemDataForm" name="price" step="0.1" class="form-control"
                                           required min="1" max="100"/>
                                </div>
                                <div class="form-group mb-2">
                                    <label for="categorySelect" class="form-label"><fmt:message key="dashboard.label.category"/></label>
                                    <select id="categorySelect" form="menuItemDataForm" class="form-select" name="category_id">
                                        <c:forEach items="${applicationScope.menuCategoriesList}" var="item">
                                            <option value="${item.id}"><fmt:message key="main.text.${item.category}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group mb-2">
                                    <label for="descriptionInput" class="form-label"><fmt:message key="dashboard.label.description"/></label>
                                    <textarea id="descriptionInput" form="menuItemDataForm" name="description" class="form-control"
                                              rows="4" style="resize: none" required minlength="5" maxlength="300"></textarea>
                                </div>
                                <p class="text-success">${menuItemAdded}</p>
                                <p class="text-danger">${menuItemNotAdded}</p>
                            </div>
                        </div>
                    </div>
                    <div class="ms-3 mb-3">
                        <button type="submit" form="menuItemDataForm" class="button button-primary"
                                style="color: var(--cafe-secondary)"><fmt:message key="profile.action.saveChanges"/>
                        </button>
                    </div>
                </div>

                <div class="tab-pane fade" id="manage-users" role="tabpanel">
                    <div class="d-flex flex-column justify-content-between align-items-center">
                        <table class="table table-hover align-middle mt-0" style="table-layout: fixed">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 12%">Id</th>
                                <th scope="col" style="width: 36%">Email</th>
                                <th scope="col" style="width: 16%"><fmt:message key="dashboard.text.userStatus"/></th>
                                <th scope="col" style="width: 16%"><fmt:message key="dashboard.text.userRole"/></th>
                                <th scope="col"></th>
                            </tr>
                            </thead>

                            <tbody id="usersTableBody">
                            </tbody>
                        </table>

                        <ul class="usersPagination"></ul>
                    </div>
                </div>

                <div class="tab-pane fade" id="manage-menu" role="tabpanel">
                    <div class="d-flex flex-column justify-content-between align-items-center">
                        <table class="table table-hover align-middle" style="table-layout: fixed">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 12%">Id</th>
                                <th scope="col" style="width: 36%"><fmt:message key="dashboard.text.menuItemName"/></th>
                                <th scope="col" style="width: 12%"><fmt:message key="dashboard.text.menuItemPrice"/></th>
                                <th scope="col"></th>
                            </tr>
                            </thead>

                            <tbody id="menuTableBody">
                            </tbody>
                        </table>

                        <ul class="menuPagination"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="footer.jsp"/>

<fmt:message key="dashboard.action.banUser" var="banUser"/>
<fmt:message key="dashboard.action.unbanUser" var="unbanUser"/>
<fmt:message key="dashboard.error.valueMissing" var="valueMissing"/>
<fmt:message key="dashboard.error.namePatternMismatch" var="namePatternMismatch"/>
<fmt:message key="dashboard.error.priceRangeOverflow" var="priceRangeOverflow"/>
<fmt:message key="dashboard.error.priceRangeUnderflow" var="priceRangeUnderflow"/>
<fmt:message key="dashboard.error.descriptionTooShort" var="descriptionTooShort"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/jquery.twbsPagination.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.min.js" type='text/javascript'></script>


<script>
    $(document).ready(function () {
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
        const USERS_PER_PAGE = 8;
        const MENU_ITEMS_PER_PAGE = 8;
        const MAX_VISIBLE_PAGES = 4;

        loadNumberOfUsers();

        function loadUsersPage(page) {
            const data = {
                command: 'load_user_page',
                page: page
            };
            $.getJSON(URL, data, function (responseData) {
                const tableBody = $('#usersTableBody');

                tableBody.fadeOut(120, function () {
                    // clear previous table body contents
                    tableBody.empty();

                    $.each(responseData, function (index, user) {
                        // creating button for each user row
                        const toggleBanButton = $("<button>")
                            .addClass('btn btn-outline-danger btn-sm')
                            .attr('id', 'toggleBanButton' + index)
                            .css('float', 'right')
                            .css('width', '60%');

                        if (user.status === 'BANNED') {
                            toggleBanButton.text("${unbanUser}");
                        } else {
                            toggleBanButton.text("${banUser}");
                        }
                        if (user.role === 'ADMIN') {
                            toggleBanButton.prop('disabled', true);
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
                                    toggleBanButton.text("${unbanUser}");
                                } else {
                                    toggleBanButton.text("${banUser}");
                                }
                            });
                        });

                        // adding new row to the table with user data
                        $("<tr>").appendTo(tableBody)
                            .append($("<td>").attr('id', 'userId' + index).text(user.id))
                            .append($("<td>").attr('id', 'userEmail' + index).text(user.email))
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

                initUserPagination(totalPages, visiblePages);
            });
        }

        function initUserPagination(totalPages, visiblePages) {
            $('.usersPagination').twbsPagination({
                totalPages: totalPages,
                visiblePages: visiblePages,
                prev: '&laquo;',
                next: '&raquo;',
                firstClass: 'visually-hidden',
                lastClass: 'visually-hidden',
                anchorClass: 'page-button',
                pageClass: 'item-page',
                prevClass: 'item-page prev',
                nextClass: 'item-page next',

                onPageClick: function (event, page) {
                    loadUsersPage(page);
                }
            });
        }

        loadNumberOfMenuItems();

        function loadNumberOfMenuItems() {
            const data = {
                command: 'get_menu_item_count'
            }
            $.getJSON(URL, data, function (responseData) {

                const totalPages = Math.ceil(responseData / MENU_ITEMS_PER_PAGE);
                const visiblePages = (totalPages < MAX_VISIBLE_PAGES) ? totalPages : MAX_VISIBLE_PAGES;

                initMenuPagination(totalPages, visiblePages);
            });
        }

        function initMenuPagination(totalPages, visiblePages) {
            const pagination = $('.menuPagination');
            pagination.twbsPagination('destroy');
            pagination.twbsPagination({
                totalPages: totalPages,
                visiblePages: visiblePages,
                prev: '&laquo;',
                next: '&raquo;',
                firstClass: 'visually-hidden',
                lastClass: 'visually-hidden',
                anchorClass: 'page-button',
                pageClass: 'item-page',
                prevClass: 'item-page prev',
                nextClass: 'item-page next',

                onPageClick: function (event, page) {
                    loadMenuPage(page);
                }
            });
        }

        function loadMenuPage(page) {
            const data = {
                command: 'load_menu_page',
                page: page
            };
            $.getJSON(URL, data, function (responseData) {
                const tableBody = $('#menuTableBody');

                tableBody.fadeOut(120, function () {
                    // clear previous table body contents
                    tableBody.empty();

                    $.each(responseData, function (index, menuItem) {
                        // creating button for each menu item row
                        const deleteButton = $("<button>")
                            .addClass('btn-close')
                            .attr('id', 'deleteButton' + index)
                            .css('float', 'right');

                        // setting click event on button to delete item
                        deleteButton.click(function () {
                            const requestData = {
                                command: 'delete_item',
                                item_id: menuItem.id
                            };

                            $.post(URL, requestData).done(function (response) {
                                if (response === 'menuItemDeleted') {
                                    const currentRow = $('#menuRow' + index);

                                    currentRow.animate({opacity: 0}, 150, function () {
                                        currentRow.animate({height: 0}, 150, function () {
                                            currentRow.remove();

                                            loadNumberOfMenuItems();
                                        })
                                    })
                                }
                            }).fail(function (response) {
                                console.log(response);
                            });
                        });

                        // adding new row to the table with menu item data
                        $("<tr>").attr('id', 'menuRow' + index).appendTo(tableBody)
                            .append($("<td>").text(menuItem.id))
                            .append($("<td>").text(menuItem.name))
                            .append($("<td>").text(Number(menuItem.price).toFixed(2)))
                            .append($("<td>").html(deleteButton));
                    });

                    tableBody.fadeIn(120);
                });
            })
        }

        const nameInput = document.getElementById('nameInput');
        const priceInput = document.getElementById('priceInput');
        const descriptionInput = document.getElementById('descriptionInput');

        const menuItemDataForm = document.getElementById('menuItemDataForm');

        let popover = new bootstrap.Popover(nameInput, { trigger: 'manual' });

        nameInput.addEventListener('input', function () {
            checkNameValidity();
        });
        nameInput.addEventListener('focusin', function () {
            checkNameValidity();
        });
        nameInput.addEventListener('focusout', function () {
            popover.hide();
        });

        priceInput.addEventListener('input', function () {
            checkPriceValidity();
        });
        priceInput.addEventListener('focusin', function () {
            checkPriceValidity();
        });
        priceInput.addEventListener('focusout', function () {
            popover.hide();
        });

        descriptionInput.addEventListener('input', function () {
            checkDescriptionValidity();
        });
        descriptionInput.addEventListener('focusin', function () {
            checkDescriptionValidity();
        });
        descriptionInput.addEventListener('focusout', function () {
            popover.hide();
        });

        function checkNameValidity() {
            if (nameInput.validity.valid) {
                popover.hide();
            } else {
                showNameError();
            }
        }

        function checkPriceValidity() {
            if (priceInput.validity.valid) {
                popover.hide();
            } else {
                showPriceError();
            }
        }

        function checkDescriptionValidity() {
            if (descriptionInput.validity.valid) {
                popover.hide();
            } else {
                showDescriptionError();
            }
        }

        function showNameError() {
            if (nameInput.validity.valueMissing) {
                nameInput.setAttribute('data-bs-content', "${valueMissing}");
            } else if (nameInput.validity.patternMismatch) {
                nameInput.setAttribute('data-bs-content', "${namePatternMismatch}");
            }
            popover = createPopover(nameInput);
            popover.show();
        }

        function showPriceError() {
            if (priceInput.validity.valueMissing) {
                priceInput.setAttribute('data-bs-content', "${valueMissing}");
            } else if (priceInput.validity.rangeOverflow) {
                priceInput.setAttribute('data-bs-content', "${priceRangeOverflow}");
            } else if (priceInput.validity.rangeUnderflow) {
                priceInput.setAttribute('data-bs-content', "${priceRangeUnderflow}");
            }
            popover = createPopover(priceInput);
            popover.show();
        }

        function showDescriptionError() {
            if (descriptionInput.validity.valueMissing) {
                descriptionInput.setAttribute('data-bs-content', "${valueMissing}");
            } else if (descriptionInput.validity.tooShort) {
                descriptionInput.setAttribute('data-bs-content', "${descriptionTooShort}");
            }
            popover = createPopover(descriptionInput);
            popover.show();
        }

        function createPopover(input) {
            popover.dispose();
            return new bootstrap.Popover(input, { trigger: 'manual' });
        }

        menuItemDataForm.addEventListener('submit', function (event) {
            if (!nameInput.validity.valid) {
                showNameError();
            } else if (!priceInput.validity.valid) {
                popover = createPopover(priceInput);
                showPriceError();
            } else if (!descriptionInput.validity.valid) {
                popover = createPopover(descriptionInput);
                showDescriptionError();
            }
            if (!nameInput.validity.valid || !priceInput.validity.valid || !descriptionInput.validity.valid) {
                event.preventDefault();
            }
        });
    });
</script>
</body>
</html>
