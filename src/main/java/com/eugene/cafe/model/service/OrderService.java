package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;

import java.time.LocalTime;
import java.util.Map;

public interface OrderService {

    boolean placeOrder(int userId, double orderTotal, Map<MenuItem, Integer> menuItems, LocalTime pickupTime) throws ServiceException;

    double calculateOrderTotal(Map<MenuItem, Integer> shoppingCart) throws ServiceException;
}
