package com.eugene.cafe.entity;

import java.sql.Timestamp;

public class Order extends AbstractEntity {

    private int clientId;

    private Timestamp pickUpTime;

    private double totalPrice;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private int reviewId;

    public Order(int clientId, Timestamp pickUpTime, double totalPrice,
                 OrderStatus orderStatus, PaymentStatus paymentStatus, int reviewId) {
        this.clientId = clientId;
        this.pickUpTime = pickUpTime;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.reviewId = reviewId;
    }

    public int getClientId() {
        return clientId;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    // todo: implement hashcode(), equals()

    public static class Builder {

        private int id;

        private int clientId;

        private Timestamp pickUpTime;

        private double totalPrice;

        private OrderStatus orderStatus;

        private PaymentStatus paymentStatus;

        private int reviewId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setClientId(int clientId) {
            this.clientId = clientId;
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

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public void setReviewId(int reviewId) {
            this.reviewId = reviewId;
        }

        public Order buildOrder() {
            Order order = new Order(clientId, pickUpTime, totalPrice, orderStatus, paymentStatus, reviewId);
            order.setId(id);
            return order;
        }
    }
}
