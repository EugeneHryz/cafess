package com.eugene.cafe.entity;

public class Category extends AbstractEntity {

    private String category;

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category{id: ")
                .append(getId())
                .append(", category: ")
                .append(category)
                .append("}");
        return builder.toString();
    }

    // todo: implement hashcode(), equals()
}
