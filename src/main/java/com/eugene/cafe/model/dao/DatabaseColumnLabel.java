package com.eugene.cafe.model.dao;

public final class DatabaseColumnLabel {

    // clients table
    public static final String CLIENTS_ID = "id";

    public static final String CLIENTS_NAME = "name";

    public static final String CLIENTS_SURNAME = "surname";

    public static final String CLIENTS_ROLE_ID = "role_id";

    public static final String CLIENTS_STATUS_ID = "status_id";

    public static final String CLIENTS_EMAIL = "email";

    public static final String CLIENTS_PASSWORD = "password";

    public static final String CLIENTS_BALANCE = "balance";

    public static final String CLIENTS_PROFILE_IMAGE = "profile_image";

    // client_role table
    public static final String CLIENT_ROLE_ID = "id";

    public static final String CLIENT_ROLE_ROLE = "role";

    // client_status table
    public static final String CLIENT_STATUS_ID = "id";

    public static final String CLIENT_STATUS_STATUS = "status";

    // order_status table
    public static final String ORDER_STATUS_ID = "id";

    public static final String ORDER_STATUS_STATUS = "status";

    // order_payment_status table
    public static final String ORDER_PAYMENT_STATUS_ID = "id";

    public static final String ORDER_PAYMENT_STATUS_STATUS = "status";

    // orders table
    public static final String ORDERS_ID = "id";

    public static final String ORDERS_CLIENT_ID = "client_id";

    public static final String ORDERS_PICK_UP_TIME = "pick_up_time";

    public static final String ORDERS_TOTAL_PRICE = "total_price";

    public static final String ORDERS_ORDER_STATUS_ID = "order_status_id";

    public static final String ORDERS_PAYMENT_STATUS_ID = "payment_status_id";

    public static final String ORDERS_REVIEW_ID = "review_id";

    // menu_items table
    public static final String MENU_ITEMS_ID = "id";

    public static final String MENU_ITEMS_NAME = "name";

    public static final String MENU_ITEMS_DESCRIPTION = "description";

    public static final String MENU_ITEMS_PRICE = "price";

    public static final String MENU_ITEMS_IMAGE = "image";

    // reviews table
    public static final String REVIEWS_ID = "id";

    public static final String REVIEWS_RATING = "rating";

    public static final String REVIEWS_COMMENT = "comment";

    public static final String REVIEWS_DATE = "date";


    private DatabaseColumnLabel() { }
}
