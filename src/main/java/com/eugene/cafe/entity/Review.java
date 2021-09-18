package com.eugene.cafe.entity;

import java.sql.Timestamp;

public class Review extends AbstractEntity {

    private short rating;

    private String comment;

    private Timestamp date;

    public Review(short rating, String comment, Timestamp timestamp) {
        this.rating = rating;
        this.comment = comment;
        this.date = timestamp;
    }

    // todo: implement hashcode(), equals()

    public short getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getDate() {
        return date;
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
}
