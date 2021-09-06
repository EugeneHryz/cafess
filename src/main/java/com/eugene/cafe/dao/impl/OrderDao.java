package com.eugene.cafe.dao.impl;

import com.eugene.cafe.dao.AbstractDao;
import com.eugene.cafe.entity.*;
import com.eugene.cafe.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.dao.DatabaseColumnLabels.*;

public class OrderDao extends AbstractDao<Order> {

    private static final Logger logger = LogManager.getLogger(OrderDao.class);

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders(client_id, estimated_time, " +
            "total_price, order_status_id, payment_status_id, review_id) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ORDER_BY_ID = "SELECT id, client_id, pick_up_time, total_price, " +
            "order_status.status, order_payment_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id INNER JOIN order_payment_status " +
            "ON orders.payment_status_id = order_payment_status.id WHERE clients.id = ?";

    private static final String SQL_FIND_ALL_ORDERS = "SELECT id, client_id, pick_up_time, total_price, " +
            "order_status.status, order_payment_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id INNER JOIN order_payment_status " +
            "ON orders.payment_status_id = order_payment_status.id";

    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET client_id = ?, pick_up_time = ?, total_price = ?, " +
            "order_status_id = ?, payment_status_id = ?, review_id = ? WHERE orders.id = ?";

    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE orders.id = ?";

    private static final String SQL_FIND_CLIENT_ORDERS = "SELECT id, client_id, pick_up_time, total_price, " +
            "order_status.status, order_payment_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id INNER JOIN order_payment_status " +
            "ON orders.payment_status_id = order_payment_status.id WHERE orders.client_id = ?";

    @Override
    public boolean create(Order entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER)) {
            statement.setInt(1, entity.getClientId());
            statement.setTimestamp(2, entity.getPickUpTime());
            statement.setDouble(3, entity.getTotalPrice());
            statement.setInt(4, entity.getOrderStatus().getId());
            statement.setInt(5, entity.getPaymentStatus().getId());
            statement.setInt(6, entity.getReviewId());

            if (statement.executeUpdate() > 0) {
                created = true;
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return created;
    }

    @Override
    public Optional<Order> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        Optional<Order> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Order order = buildOrder(resultSet);
                result = Optional.of(order);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return result;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);

            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return orders;
    }

    @Override
    public Optional<Order> update(Order entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        Optional<Order> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            statement.setInt(1, entity.getId());

            if (statement.executeUpdate() > 0) {
                updated = Optional.of(entity);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return updated;
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return deleted;
    }

    public List<Order> findAllClientOrders(Client client) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_CLIENT_ORDERS)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return orders;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {

        Order.Builder builder = new Order.Builder();
        builder.setId(resultSet.getInt(ORDERS_ID))
                .setClientId(resultSet.getInt(ORDERS_CLIENT_ID))
                .setPickupTime(resultSet.getTimestamp(ORDERS_PICK_UP_TIME))
                .setTotalPrice(resultSet.getDouble(ORDERS_TOTAL_PRICE))
                .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDERS_ORDER_STATUS_ID).toUpperCase()))
                .setPaymentStatus(PaymentStatus.valueOf(resultSet.getString(ORDER_PAYMENT_STATUS_ID).toUpperCase()))
                .setReviewId(resultSet.getInt(ORDERS_REVIEW_ID));

        return builder.buildOrder();
    }
}
