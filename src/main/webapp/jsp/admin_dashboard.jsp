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
</head>

<body id="admin-dashboard-body">

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

    <div class="card overflow-hidden border-top-0 rounded-0">

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
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="footer.jsp"/>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type='text/javascript'></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.6.0.min.js"></script>

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

    $('#manageUsersButton').one('click', function () {

        const url = "${pageContext.request.contextPath}/jsp/users.jsp";
        $.ajax(url, {
            method: 'POST',
            success: function (data) {
                $('#manage-users').html(data);
            }
        });
    });
</script>
</body>
