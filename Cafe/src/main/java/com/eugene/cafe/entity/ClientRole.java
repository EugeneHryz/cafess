package com.eugene.cafe.entity;

public enum ClientRole {

    USER(1),
    ADMIN(2);

    private final int id;

    ClientRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
