package com.eugene.cafe.entity;

public enum OrderStatus {

    COOKING(1),
    READY(2),
    PICKED_UP(3);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
