package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.CategoryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.eugene.cafe.model.dao.DatabaseColumnLabel.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl extends CategoryDao {

    private static final Logger logger = LogManager.getLogger(CategoryDaoImpl.class);

    private static final String SQL_CREATE_CATEGORY = "INSERT INTO menu_categories(category) VALUES(?)";

    private static final String SQL_FIND_BY_ID = "SELECT id, category FROM menu_categories WHERE menu_categories.id = ?";

    private static final String SQL_FIND_ALL_CATEGORIES = "SELECT id, category FROM menu_categories";

    private static final String SQL_UPDATE_CATEGORY = "UPDATE menu_categories SET category = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM menu_categories WHERE id = ?";

    @Override
    public boolean create(Category entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set");
            throw new DaoException("Database connection is not set");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CATEGORY)) {
            statement.setString(1, entity.getCategory());

            if (statement.executeUpdate() > 0) {
                created = true;
            }
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
        return created;
    }

    @Override
    public Optional<Category> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set");
            throw new DaoException("Database connection is not set");
        }

        Optional<Category> category = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                category = Optional.of(buildCategory(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
        return category;
    }

    @Override
    public List<Category> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set");
            throw new DaoException("Database connection is not set");
        }

        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CATEGORIES);

            while (resultSet.next()) {
                Category category = buildCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
        return categories;
    }

    @Override
    public Optional<Category> update(Category entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set");
            throw new DaoException("Database connection is not set");
        }

        Optional<Category> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CATEGORY)) {
            statement.setString(1, entity.getCategory());
            statement.setInt(2, entity.getId());

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
            logger.error("Database connection is not set");
            throw new DaoException("Database connection is not set");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
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

    private Category buildCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(MENU_CATEGORIES_ID);
        String category = resultSet.getString(MENU_CATEGORIES_CATEGORY);

        Category categoryEntity = new Category(category);
        categoryEntity.setId(id);
        return categoryEntity;
    }
}
