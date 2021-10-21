package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.entity.OrderStatus;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    Optional<User> placeOrder(int userId, double orderTotal, Map<MenuItem, Integer> menuItems, LocalTime pickupTime) throws ServiceException;

    List<Order> getSubsetOfUserOrders(int userId, int pageNumber) throws ServiceException;

    int getUserOrderCount(int userId) throws ServiceException;

    double calculateOrderTotal(Map<MenuItem, Integer> shoppingCart) throws ServiceException;

    boolean saveOrderReview(int orderId, short rating, String comment) throws ServiceException;

    boolean changeOrderStatus(int orderId, OrderStatus newStatus) throws ServiceException;
}
