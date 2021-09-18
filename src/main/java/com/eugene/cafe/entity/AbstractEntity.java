package com.eugene.cafe.entity;

public abstract class AbstractEntity {

    private int id;

    public AbstractEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
