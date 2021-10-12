package com.eugene.cafe.entity;

public class MenuItem extends AbstractEntity {

    private String name;
    private String description;
    private double price;
    private int categoryId;
    private String imagePath;

    public MenuItem(String name, String description, double price, int categoryId, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getImagePath() {
        return imagePath;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MenuItem{id: ")
                .append(getId())
                .append(", name: ")
                .append(name)
                .append(", price: ")
                .append(price)
                .append(", description: ")
                .append(description)
                .append(", categoryId: ")
                .append(categoryId)
                .append("}");
        return builder.toString();
    }

    // todo: implement hashcode(), equals()

    public static class Builder {

        private int id;
        private String name;
        private String description;
        private double price;
        private int categoryId;
        private String imagePath;

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

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public MenuItem buildMenuItem() {
            MenuItem item = new MenuItem(name, description, price, categoryId, imagePath);
            item.setId(id);
            return item;
        }
    }
}
