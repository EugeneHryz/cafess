package com.eugene.cafe.entity;

import java.sql.Timestamp;

public class Review extends AbstractEntity {

    private int orderId;
    private short rating;
    private String comment;
    private Timestamp date;

    public Review(int orderId, short rating, String comment, Timestamp date) {
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public short getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    // todo: implement toString(), hashcode(), equals()

    public static class Builder {

        private int id;
        private int orderId;
        private short rating;
        private String comment;
        private Timestamp date;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setOrderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setRating(short rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public Review buildReview() {
            Review review = new Review(orderId, rating, comment, date);
            review.setId(id);
            return review;
        }

    }
}
