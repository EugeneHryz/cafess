package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Client;
import com.eugene.cafe.entity.Order;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

public abstract class OrderDao extends AbstractDao<Order> {

    public abstract List<Order> findAllClientOrders(Client client) throws DaoException;
}
