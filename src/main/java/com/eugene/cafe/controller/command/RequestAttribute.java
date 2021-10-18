package com.eugene.cafe.controller.command;

public class RequestAttribute {

    // messages
    public static final String INVALID_LOGIN_OR_PASSWORD = "invalidLoginOrPassword";

    public static final String INVALID_SIGN_UP_DATA = "invalidSignUpData";

    public static final String EMAIL_ALREADY_EXISTS = "emailAlreadyExists";

    public static final String MENU_ITEM_ADDED = "menuItemAdded";

    public static final String MENU_ITEM_NOT_ADDED = "menuItemNotAdded";

    public static final String PLACE_ORDER_FAIL = "placeOrderFail";

    public static final String PLACE_ORDER_SUCCESS = "placeOrderSuccess";


    // request/session attributes
    public static final String MENU_ITEMS_SUBLIST = "menuItemsSublist";

    public static final String MENU_ITEMS_PAGE_NUMBER = "menuItemsPageNumber";

    public static final String MENU_ITEMS_COUNT = "menuItemCount";

    public static final String MENU_ITEMS_SORT_ORDER = "menuItemsSortOrder";

    public static final String MENU_ITEMS_CURRENT_CATEGORY = "menuItemsCurrentCategory";

    public static final String MENU_CATEGORIES_LIST = "menuCategoriesList";

    public static final String SHOPPING_CART = "shoppingCart";

    public static final String SHOPPING_CART_SIZE = "shoppingCartSize";

    public static final String ORDER_TOTAL = "orderTotal";

    public static final String PICKUP_TIME_LIST = "pickupTimeList";

    public static final String ORDER_RESULT_MESSAGE = "orderResult";

    public static final String USER = "user";

    public static final String ROLE = "role";


    public static final String PREVIOUS_REQUEST = "previousRequest";

    public static final String LOCALE = "locale";

    public static final String EXCEPTION = "exception";



    private RequestAttribute() {
    }
}
