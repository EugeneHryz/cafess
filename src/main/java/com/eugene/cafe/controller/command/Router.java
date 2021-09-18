package com.eugene.cafe.controller.command;

public class Router {

    public enum RouterType {
        FORWARD,
        REDIRECT
    }

    private final RouterType type;

    private final String page;

    public Router(String page, RouterType type) {
        this.type = type;
        this.page = page;
    }

    public RouterType getType() {
        return type;
    }

    public String getPage() {
        return page;
    }
}
