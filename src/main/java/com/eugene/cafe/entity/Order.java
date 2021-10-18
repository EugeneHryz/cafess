package com.eugene.cafe.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class Order extends AbstractEntity {

    private int userId;
    private Timestamp pickUpTime;
    private double totalPrice;
    private OrderStatus orderStatus;
    private Map<MenuItem, Integer> menuItems;
    private int reviewId;

    public Order(int userId, Timestamp pickUpTime, double totalPrice,
                 OrderStatus orderStatus, Map<MenuItem, Integer> menuItems, int reviewId) {

        this.userId = userId;
        this.pickUpTime = pickUpTime;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.menuItems = menuItems;
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
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

    public int getReviewId() {
        return reviewId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPickUpTime(Timestamp pickUpTime) {
        this.pickUpTime = pickUpTime;
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

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    // todo: implement toString(), hashcode(), equals()

    public static class Builder {

        private int id;
        private int userId;
        private Timestamp pickUpTime;
        private double totalPrice;
        private OrderStatus orderStatus = OrderStatus.COOKING;
        private Map<MenuItem, Integer> menuItems;
        private int reviewId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
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

        public void setReviewId(int reviewId) {
            this.reviewId = reviewId;
        }

        public Order buildOrder() {
            Order order = new Order(userId, pickUpTime, totalPrice, orderStatus, menuItems, reviewId);
            order.setId(id);
            return order;
        }
    }
}
