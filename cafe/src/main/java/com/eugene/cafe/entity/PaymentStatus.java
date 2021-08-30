package com.eugene.cafe.entity;

public enum PaymentStatus {

    PAID(1),
    NOT_PAID(2);

    private final int id;

    PaymentStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
