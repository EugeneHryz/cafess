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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Review{id: ")
                .append(getId())
                .append(", rating: ")
                .append(rating)
                .append(", orderId: ")
                .append(orderId)
                .append(", comment: ")
                .append(comment)
                .append(", date: ")
                .append(date);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Review review) {
            return getId() == review.getId() && orderId == review.orderId
                    && rating == review.rating && comment.equals(review.comment)
                    && date.equals(review.date);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(getId());
        hashCode = hashCode * 31 + Short.hashCode(rating);
        hashCode = hashCode * 31 + Integer.hashCode(orderId);
        hashCode = hashCode * 31 + comment.hashCode();
        hashCode = hashCode * 31 + date.hashCode();

        return hashCode;
    }

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
