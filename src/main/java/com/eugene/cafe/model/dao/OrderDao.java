package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

public abstract class OrderDao extends AbstractDao<Order> {

    public abstract boolean createOrderMenuItemMappings(Order order, List<MenuItem> menuItems) throws DaoException;

    public abstract List<MenuItem> findMenuItemsByOrderId(int orderId) throws DaoException;

    public abstract List<Order> getSubsetOfUserOrders(int userId, int offset, int limit) throws DaoException;

    public abstract List<Order> getSubsetOfOrders(int offset, int limit) throws DaoException;

    public abstract int getUserOrderCount(int userId) throws DaoException;

    public abstract int getOrderCount() throws DaoException;
}
