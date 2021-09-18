package com.eugene.cafe.entity;

import java.io.InputStream;

public class MenuItem extends AbstractEntity {

    private String name;

    private String description;

    private double price;

    private InputStream image;

    public MenuItem(String name, String description, double price, InputStream image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public InputStream getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    // todo: implement hashcode(), equals()

    public static class Builder {

        private int id;

        private String name;

        private String description;

        private double price;

        private InputStream image;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setImage(InputStream image) {
            this.image = image;
            return this;
        }

        public MenuItem buildMenuItem() {
            MenuItem item = new MenuItem(name, description, price, image);
            item.setId(id);
            return item;
        }
    }
}
