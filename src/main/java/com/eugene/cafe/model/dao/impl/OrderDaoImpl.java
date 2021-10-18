package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.model.dao.OrderDao;
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

import static com.eugene.cafe.model.dao.DatabaseColumnLabel.*;

public class OrderDaoImpl extends OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders(client_id, pick_up_time, " +
            "total_price, order_status_id) VALUES(?, ?, ?, ?)";

    private static final String SQL_CREATE_ORDER_MENU_ITEM_MAPPING = "INSERT INTO order_menu_item_mapping(menu_item_id, order_id) " +
            "VALUES(?, ?)";

    private static final String SQL_FIND_MENU_ITEMS_BY_ORDER_ID = "SELECT name, description, price, category_id, image " +
            "FROM order_menu_item_mapping INNER JOIN menu_items ON menu_items.id = menu_item_id WHERE order_id = ?";

    private static final String SQL_FIND_ORDER_BY_ID = "SELECT orders.id, client_id, pick_up_time, total_price, " +
            "order_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id WHERE orders.id = ?";

    private static final String SQL_FIND_ALL_ORDERS = "SELECT id, client_id, pick_up_time, total_price, " +
            "order_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id";

    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET client_id = ?, pick_up_time = ?, total_price = ?, " +
            "order_status_id = ?, review_id = ? WHERE orders.id = ?";

    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE orders.id = ?";

    private static final String SQL_FIND_CLIENT_ORDERS = "SELECT id, client_id, pick_up_time, total_price, " +
            "order_status.status, review_id FROM orders INNER JOIN order_status ON " +
            "orders.order_status_id = order_status.id WHERE orders.client_id = ?";

    @Override
    public boolean create(Order entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getUserId());
            statement.setTimestamp(2, entity.getPickUpTime());
            statement.setDouble(3, entity.getTotalPrice());
            statement.setInt(4, entity.getOrderStatus().ordinal());

            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    entity.setId(generatedId);
                }
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
            // fixme
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

    @Override
    public List<Order> findAllClientOrders(User user) throws DaoException {
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

    @Override
    public boolean createOrderMenuItemMappings(Order order, List<MenuItem> menuItems) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER_MENU_ITEM_MAPPING)) {

            for (MenuItem item : menuItems) {
                statement.setInt(1, item.getId());
                statement.setInt(2, order.getId());
                statement.addBatch();
            }
            int[] results = statement.executeBatch();
            int sum = 0;
            for (int result : results) {
                sum += result;
            }
            created = (sum >= results.length);

        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return created;
    }

    @Override
    public List<MenuItem> findMenuItemsByOrderId(int orderId) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for OrderDao");
            throw new DaoException("Database connection is not set for OrderDao");
        }

        List<MenuItem> menuItems = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MENU_ITEMS_BY_ORDER_ID)) {
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuItem item = buildMenuItem(resultSet);
                menuItems.add(item);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return menuItems;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {

        Order.Builder builder = new Order.Builder();
        builder.setId(resultSet.getInt(ORDERS_ID))
                .setUserId(resultSet.getInt(ORDERS_CLIENT_ID))
                .setPickupTime(resultSet.getTimestamp(ORDERS_PICK_UP_TIME))
                .setTotalPrice(resultSet.getDouble(ORDERS_TOTAL_PRICE))
                .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDERS_ORDER_STATUS_ID).toUpperCase()))
                .setReviewId(resultSet.getInt(ORDERS_REVIEW_ID));

        return builder.buildOrder();
    }

    private MenuItem buildMenuItem(ResultSet resultSet) throws SQLException {

        MenuItem.Builder builder = new MenuItem.Builder();
        builder.setId(resultSet.getInt(MENU_ITEMS_ID))
                .setName(resultSet.getString(MENU_ITEMS_NAME))
                .setDescription(resultSet.getString(MENU_ITEMS_DESCRIPTION))
                .setPrice(resultSet.getDouble(MENU_ITEMS_PRICE))
                .setCategoryId(resultSet.getInt(MENU_ITEMS_CATEGORY_ID))
                .setImagePath(resultSet.getString(MENU_ITEMS_IMAGE));

        return builder.buildMenuItem();
    }
}
