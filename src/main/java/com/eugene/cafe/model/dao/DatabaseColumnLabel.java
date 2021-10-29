package com.eugene.cafe.model.dao;

public final class DatabaseColumnLabel {

    // users table
    public static final String USERS_ID = "id";

    public static final String USERS_NAME = "name";

    public static final String USERS_SURNAME = "surname";

    public static final String USERS_ROLE_ID = "role_id";

    public static final String USERS_STATUS_ID = "status_id";

    public static final String USERS_EMAIL = "email";

    public static final String USERS_PASSWORD = "password";

    public static final String USERS_BALANCE = "balance";

    public static final String USERS_PROFILE_IMAGE = "profile_image";

    // user_role table
    public static final String USER_ROLE_ID = "id";

    public static final String USER_ROLE_ROLE = "role";

    // user_status table
    public static final String USER_STATUS_ID = "id";

    public static final String USER_STATUS_STATUS = "status";

    // order_status table
    public static final String ORDER_STATUS_ID = "id";

    public static final String ORDER_STATUS_STATUS = "status";


    // orders table
    public static final String ORDERS_ID = "id";

    public static final String ORDERS_USER_ID = "user_id";

    public static final String ORDERS_DATE = "date";

    public static final String ORDERS_PICK_UP_TIME = "pick_up_time";

    public static final String ORDERS_TOTAL_PRICE = "total_price";

    public static final String ORDERS_STATUS_ID = "status_id";

    public static final String ORDERS_REVIEW_ID = "review_id";


    // menu_items table
    public static final String MENU_ITEMS_ID = "id";

    public static final String MENU_ITEMS_NAME = "name";

    public static final String MENU_ITEMS_DESCRIPTION = "description";

    public static final String MENU_ITEMS_PRICE = "price";

    public static final String MENU_ITEMS_IMAGE = "image";

    public static final String MENU_ITEMS_STATUS = "status";

    public static final String MENU_ITEMS_CATEGORY_ID = "category_id";

    // reviews table
    public static final String REVIEWS_ID = "id";

    public static final String REVIEWS_ORDER_ID = "order_id";

    public static final String REVIEWS_RATING = "rating";

    public static final String REVIEWS_COMMENT = "comment";

    public static final String REVIEWS_DATE = "date";

    // categories table
    public static final String MENU_CATEGORIES_ID = "id";

    public static final String MENU_CATEGORIES_CATEGORY = "category";


    private DatabaseColumnLabel() {
    }
}
