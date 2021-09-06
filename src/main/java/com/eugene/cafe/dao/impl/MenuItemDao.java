package com.eugene.cafe.dao.impl;

import com.eugene.cafe.dao.AbstractDao;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.dao.DatabaseColumnLabels.*;

public class MenuItemDao extends AbstractDao<MenuItem> {

    private static final Logger logger = LogManager.getLogger(MenuItemDao.class);

    private static final String SQL_CREATE_MENU_ITEM = "INSERT INTO menu_items(name, description, price, image) " +
            "VALUES (?, ?, ?, ?)";

    private static final String SQL_FIND_MENU_ITEM_BY_ID = "SELECT (id, name, description, price, image) FROM menu_items " +
            "WHERE menu_items.id = ?";

    private static final String SQL_FIND_ALL_MENU_ITEMS = "SELECT (id, name, description, price, image) FROM menu_items";

    private static final String SQL_UPDATE_MENU_ITEM = "UPDATE menu_items SET(name = ?, description = ?, price = ?, image = ?) " +
            "WHERE menu_items.id = ?";

    private static final String SQL_DELETE_MENU_ITEM_BY_ID = "DELETE FROM menu_items WHERE menu_items.id = ?";

    @Override
    public boolean create(MenuItem entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for MenuItemDao");
            throw new DaoException("Database connection is not set for MenuItemDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_MENU_ITEM)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDouble(3, entity.getPrice());
            statement.setBlob(4, entity.getImage());

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
    public Optional<MenuItem> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for MenuItemDao");
            throw new DaoException("Database connection is not set for MenuItemDao");
        }

        Optional<MenuItem> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MENU_ITEM_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MenuItem menuItem = buildMenuItem(resultSet);
                result = Optional.of(menuItem);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return Optional.empty();
    }

    @Override
    public List<MenuItem> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for MenuItemDao");
            throw new DaoException("Database connection is not set for MenuItemDao");
        }

        List<MenuItem> menuItems = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_MENU_ITEMS);

            while (resultSet.next()) {
                MenuItem menuItem = buildMenuItem(resultSet);
                menuItems.add(menuItem);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return menuItems;
    }

    @Override
    public Optional<MenuItem> update(MenuItem entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for MenuItemDao");
            throw new DaoException("Database connection is not set for MenuItemDao");
        }

        Optional<MenuItem> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MENU_ITEM)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDouble(3, entity.getPrice());
            statement.setBlob(4, entity.getImage());
            statement.setInt(5, entity.getId());

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
            logger.error("Database connection is not set for MenuItemDao");
            throw new DaoException("Database connection is not set for MenuItemDao");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MENU_ITEM_BY_ID)) {
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

    private MenuItem buildMenuItem(ResultSet resultSet) throws SQLException {

        MenuItem.Builder builder = new MenuItem.Builder();
        builder.setId(resultSet.getInt(MENU_ITEMS_ID))
                .setName(resultSet.getString(MENU_ITEMS_NAME))
                .setDescription(resultSet.getString(MENU_ITEMS_DESCRIPTION))
                .setPrice(resultSet.getDouble(MENU_ITEMS_PRICE));

        InputStream inputStream = null;
        Blob image = resultSet.getBlob(MENU_ITEMS_IMAGE);
        if (image != null) {
            inputStream = image.getBinaryStream();
        }
        builder.setImage(inputStream);
        return builder.buildMenuItem();
    }
}
