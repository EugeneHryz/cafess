package com.eugene.cafe.entity;

import java.sql.Timestamp;
import java.util.Map;

public class Order extends AbstractEntity {

    private int userId;
    private Timestamp date;
    private Timestamp pickUpTime;
    private double totalPrice;
    private OrderStatus orderStatus;
    private Map<MenuItem, Integer> menuItems;
    private Review review;

    public Order(int userId, Timestamp date, Timestamp pickUpTime, double totalPrice,
                 OrderStatus orderStatus, Map<MenuItem, Integer> menuItems, Review review) {

        this.userId = userId;
        this.date = date;
        this.pickUpTime = pickUpTime;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.menuItems = menuItems;
        this.review = review;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public Timestamp getPickUpTime() {
        return pickUpTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Map<MenuItem, Integer> getMenuItems() {
        return menuItems;
    }

    public Review getReview() {
        return review;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPickUpTime(Timestamp pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setMenuItems(Map<MenuItem, Integer> menuItems) {
        this.menuItems = menuItems;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order{id: ")
                .append(getId())
                .append(", date: ")
                .append(date)
                .append(", pickUpTime: ")
                .append(pickUpTime)
                .append(", totalPrice: ")
                .append(totalPrice)
                .append(", orderStatus: ")
                .append(orderStatus)
                .append(", review: ")
                .append(review)
                .append("}");
        return builder.toString();
    }

    // todo: implement hashcode(), equals()

    public static class Builder {

        private int id;
        private int userId;
        private Timestamp date;
        private Timestamp pickUpTime;
        private double totalPrice;
        private OrderStatus orderStatus = OrderStatus.COOKING;
        private Map<MenuItem, Integer> menuItems;
        private Review review;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public Builder setPickupTime(Timestamp pickUpTime) {
            this.pickUpTime = pickUpTime;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder setMenuItems(Map<MenuItem, Integer> menuItems) {
            this.menuItems = menuItems;
            return this;
        }

        public void setReview(Review review) {
            this.review = review;
        }

        public Order buildOrder() {
            Order order = new Order(userId, date, pickUpTime, totalPrice, orderStatus, menuItems, review);
            order.setId(id);
            return order;
        }
    }
}
