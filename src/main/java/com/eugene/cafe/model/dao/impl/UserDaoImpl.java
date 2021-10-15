package com.eugene.cafe.model.dao.impl;

import com.eugene.cafe.model.dao.UserDao;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserRole;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.model.dao.DatabaseColumnLabel.*;

public class UserDaoImpl extends UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String SQL_CREATE_USER = "INSERT INTO users(name, surname, role_id, status_id, email, password, " +
            "balance, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_USERS = "SELECT " +
            "users.id, name, surname, email, password, role, status, balance, profile_image FROM users " +
            "INNER JOIN user_role ON users.role_id = user_role.id " +
            "INNER JOIN user_status ON users.status_id = user_status.id";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM clients WHERE users.id = ?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT " +
            "users.id, name, surname, email, password, role, status, balance, profile_image FROM users " +
            "INNER JOIN user_role ON users.role_id = user_role.id " +
            "INNER JOIN user_status ON users.status_id = user_status.id " +
            "WHERE users.id = ?";

    private static final String SQL_UPDATE_USER = "UPDATE users SET name = ?, surname = ?, role_id = ?, status_id = ?, " +
            "email = ?, password = ?, balance = ?, profile_image = ? WHERE users.id = ?";

    private static final String SQL_UPDATE_PROFILE_PICTURE = "UPDATE users SET profile_image = ? WHERE users.id = ?";

    private static final String SQL_CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE users.id = ?";

    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT " +
            "users.id, name, surname, email, password, role, status, balance, profile_image FROM users " +
            "INNER JOIN user_role ON users.role_id = user_role.id " +
            "INNER JOIN user_status ON users.status_id = user_status.id " +
            "WHERE users.email = ?";

    private static final String SQL_FIND_SUBSET = "SELECT " +
            "users.id, name, surname, email, password, role, status, balance, profile_image FROM users " +
            "INNER JOIN user_role ON users.role_id = user_role.id " +
            "INNER JOIN user_status ON users.status_id = user_status.id " +
            "ORDER BY users.id LIMIT ? OFFSET ?";

    private static final String SQL_COUNT_ALL = "SELECT COUNT(*) FROM users";

    @Override
    public boolean create(User entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            initStatement(statement, entity);

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
    public Optional<User> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        Optional<User> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = buildUser(resultSet);
                updated = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return updated;
    }

    @Override
    public List<User> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);

            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return users;
    }

    @Override
    public Optional<User> update(User entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for for UserDao");
        }

        Optional<User> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            initStatement(statement, entity);

            statement.setInt(9, entity.getId());
            if (statement.executeUpdate() > 0) {
                updated = findById(entity.getId());
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return updated;
    }

    @Override
    public Optional<User> updateProfilePicture(int id, String imagePath) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for for UserDao");
        }

        Optional<User> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PROFILE_PICTURE)) {
            statement.setString(1, imagePath);

            statement.setInt(2, id);
            if (statement.executeUpdate() > 0) {
                updated = findById(id);
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
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
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
    public Optional<User> findUserByEmail(String email) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        Optional<User> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = buildUser(resultSet);
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return result;
    }

    @Override
    public boolean changeUserPassword(int id, String newPassword) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for for UserDao");
        }

        boolean passwordUpdated = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_PASSWORD)) {
            statement.setString(1, newPassword);

            statement.setInt(2, id);
            if (statement.executeUpdate() > 0) {
                passwordUpdated = true;
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return passwordUpdated;
    }

    @Override
    public List<User> getSubsetOfUsers(int limit, int offset) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBSET)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return users;
    }

    @Override
    public int getCount() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for UserDao");
            throw new DaoException("Database connection is not set for UserDao");
        }

        int number = 0;
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL_COUNT_ALL);
            if (resultSet.next()) {
                number = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }
        return number;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {

        User.Builder builder = new User.Builder();
        builder.setId(resultSet.getInt(CLIENTS_ID))
                .setName(resultSet.getString(CLIENTS_NAME))
                .setSurname(resultSet.getString(CLIENTS_SURNAME))
                .setRole(UserRole.valueOf(resultSet
                        .getString(CLIENT_ROLE_ROLE).toUpperCase()))
                .setStatus(UserStatus.valueOf(resultSet
                        .getString(CLIENT_STATUS_STATUS).toUpperCase()))
                .setEmail(resultSet.getString(CLIENTS_EMAIL))
                .setHashedPassword(resultSet.getString(CLIENTS_PASSWORD))
                .setBalance(resultSet.getDouble(CLIENTS_BALANCE))
                .setProfileImagePath(resultSet.getString(CLIENTS_PROFILE_IMAGE));

        return builder.buildUser();
    }

    private void initStatement(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getRole().ordinal());
        statement.setInt(4, entity.getStatus().ordinal());
        statement.setString(5, entity.getEmail());
        statement.setString(6, entity.getHashedPassword());
        statement.setDouble(7, entity.getBalance());
        statement.setString(8, entity.getProfileImagePath());
    }
}
