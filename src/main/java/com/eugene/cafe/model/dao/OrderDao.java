package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

/**
 * OrderDao abstract class
 */
public abstract class OrderDao extends AbstractDao<Order> {

    /**
     * create order - menu items mappings
     *
     * @param order order
     * @param menuItems list of menu items in the order
     * @return true, if successful
     * @throws DaoException if error
     */
    public abstract boolean createOrderMenuItemMappings(Order order, List<MenuItem> menuItems) throws DaoException;

    /**
     * find list of menu items by order
     *
     * @param orderId order id
     * @return list of menu items
     * @throws DaoException if error
     */
    public abstract List<MenuItem> findMenuItemsByOrderId(int orderId) throws DaoException;

    /**
     * get subset of user orders with specified offset and limit
     *
     * @param userId user id
     * @param offset offset from the beginning of the result set
     * @param limit limit of menu items in the subset
     * @return list of orders
     * @throws DaoException if error
     */
    public abstract List<Order> getSubsetOfUserOrders(int userId, int offset, int limit) throws DaoException;

    /**
     * get subset of all orders with specified offset and limit
     *
     * @param offset offset from the beginning of the result set
     * @param limit limit of menu items in the subset
     * @return list of orders
     * @throws DaoException if error
     */
    public abstract List<Order> getSubsetOfOrders(int offset, int limit) throws DaoException;

    /**
     * get number of orders with specified user id
     *
     * @param userId user id
     * @return number of orders
     * @throws DaoException if error
     */
    public abstract int getUserOrderCount(int userId) throws DaoException;

    /**
     * get number of all orders
     *
     * @return number of orders
     * @throws DaoException if error
     */
    public abstract int getOrderCount() throws DaoException;
}
