package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.dao.CategoryDao;
import static com.eugene.cafe.model.dao.DatabaseColumnLabel.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl extends CategoryDao {

    private static final String SQL_CREATE_CATEGORY = "INSERT INTO menu_categories(category) VALUES(?)";

    private static final String SQL_FIND_ALL_CATEGORIES = "SELECT id, category FROM menu_categories";

    // todo: implement other methods

    @Override
    public boolean create(Category entity) throws DaoException {
        if (connection == null) {
            // todo: write log
            throw new DaoException("Database connection is not set for CategoryDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CATEGORY)) {
            statement.setString(1, entity.getCategory());

            if (statement.executeUpdate() > 0) {
                created = true;
            }
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }
        return created;
    }

    @Override
    public Optional<Category> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() throws DaoException {
        if (connection == null) {
            // todo: write log
            throw new DaoException("Database connection is not set for ClientDao");
        }

        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CATEGORIES);

            while (resultSet.next()) {
                Category category = buildCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }
        return categories;
    }

    @Override
    public Optional<Category> update(Category entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        return false;
    }

    private Category buildCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(MENU_CATEGORIES_ID);
        String category = resultSet.getString(MENU_CATEGORIES_CATEGORY);

        Category categoryEntity = new Category(category);
        categoryEntity.setId(id);
        return categoryEntity;
    }
}
