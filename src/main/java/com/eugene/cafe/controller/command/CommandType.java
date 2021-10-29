package com.eugene.cafe.controller.command;

import java.util.List;

public enum CommandType {
    LOG_IN("guest", "user", "admin"),
    LOG_OUT("guest", "user", "admin"),
    DEFAULT("guest", "user", "admin"),
    GO_TO_SIGNUP_PAGE("guest", "user", "admin"),
    GO_TO_LOGIN_PAGE("guest", "user", "admin"),
    SIGN_UP("guest", "user", "admin"),
    CHANGE_LOCALE("guest", "user", "admin"),
    GO_TO_PROFILE_PAGE("user", "admin"),
    EDIT_USER_PROFILE("user", "admin"),
    UPDATE_PROFILE_PICTURE("user", "admin"),
    CHANGE_PASSWORD("user", "admin"),
    GO_TO_ADMIN_DASHBOARD_PAGE("admin"),
    ADD_MENU_ITEM("admin"),
    GO_TO_MENU_PAGE("guest", "user", "admin"),
    CHANGE_SORT_ORDER("guest", "user", "admin"),
    CHANGE_CURRENT_CATEGORY("guest", "user", "admin"),
    TOP_UP_BALANCE("user", "admin"),
    GO_TO_CHECKOUT_PAGE("user", "admin"),
    PLACE_ORDER("user", "admin"),
    GO_TO_ORDER_HISTORY_PAGE("user", "admin"),
    SAVE_REVIEW("user", "admin"),
    GO_TO_ORDERS_PAGE("admin"),
    CHANGE_ORDER_STATUS("admin"),

    GET_USER_COUNT("admin"),
    LOAD_USER_PAGE("admin"),
    BAN_USER("admin"),
    UNBAN_USER("admin"),
    ADD_ITEM_TO_CART("user", "admin"),
    REMOVE_ITEM_FROM_CART("user", "admin"),
    GET_MENU_ITEM_COUNT("admin"),
    LOAD_MENU_PAGE("admin"),
    CHANGE_MENU_ITEM_STATUS("admin");

    private final List<String> roles;

    CommandType(String... roles) {
        this.roles = List.of(roles);
    }

    public boolean isValidRole(String role) {
        return roles.stream().anyMatch(r -> r.equalsIgnoreCase(role));
    }
}
