package com.eugene.cafe.entity;

public enum ClientStatus {

    NOT_ACTIVATED(1),
    ACTIVATED(2),
    BANNED(3);

    private final int id;

    ClientStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
