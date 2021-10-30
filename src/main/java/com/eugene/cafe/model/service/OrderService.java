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

/**
 * OrderService interface
 */
public interface OrderService {

    /**
     * place order
     *
     * @param userId user id
     * @param orderTotal order total
     * @param menuItems menu items in the order
     * @param pickupTime order pickup time
     * @return Optional User with updated balance
     * @throws ServiceException if error
     */
    Optional<User> placeOrder(int userId, double orderTotal, Map<MenuItem, Integer> menuItems, LocalTime pickupTime) throws ServiceException;

    /**
     * get subset of user orders by page
     *
     * @param userId user id
     * @param pageNumber page number
     * @return list of orders
     * @throws ServiceException if error
     */
    List<Order> getSubsetOfUserOrders(int userId, int pageNumber) throws ServiceException;

    /**
     * get number of user orders
     *
     * @param userId user id
     * @return number of orders
     * @throws ServiceException if error
     */
    int getUserOrderCount(int userId) throws ServiceException;

    /**
     * save order review
     *
     * @param orderId order id
     * @param rating order rating
     * @param comment order comment
     * @return true, if successful
     * @throws ServiceException if error
     */
    boolean saveOrderReview(int orderId, short rating, String comment) throws ServiceException;

    /**
     * change order status
     *
     * @param orderId order id
     * @param newStatus new order status
     * @return true, if successful
     * @throws ServiceException if error
     */
    boolean changeOrderStatus(int orderId, OrderStatus newStatus) throws ServiceException;

    /**
     * calculate order total based on shopping cart
     *
     * @param shoppingCart shopping cart
     * @return order total
     */
    double calculateOrderTotal(Map<MenuItem, Integer> shoppingCart);
}
