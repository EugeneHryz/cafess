package com.eugene.cafe.dao;

import com.eugene.cafe.entity.Client;
import com.eugene.cafe.entity.ClientRole;
import com.eugene.cafe.entity.ClientStatus;
import com.eugene.cafe.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.dao.DatabaseColumnLabels.*;

public class ClientDao extends AbstractDao<Client> {

    private static final Logger logger = LogManager.getLogger(ClientDao.class);

    private static final String SQL_CREATE_CLIENT = "INSERT INTO clients(name, surname, role_id, status_id, email, " +
            "balance, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_CLIENTS = "SELECT " +
            "clients.id, name, surname, email, role, status, balance, profile_image FROM clients " +
            "INNER JOIN client_role ON clients.role_id=client_role.id " +
            "INNER JOIN client_status ON clients.status_id = client_status.id";

    private static final String SQL_DELETE_CLIENT_BY_ID = "DELETE FROM clients WHERE clients.id = ?";

    private static final String SQL_FIND_CLIENT_BY_ID = "SELECT " +
            "clients.id, name, surname, email, role, status, balance, profile_image FROM clients " +
            "INNER JOIN client_role ON clients.role_id=client_role.id " +
            "INNER JOIN client_status ON clients.status_id=client_status.id " +
            "WHERE clients.id = ?";

    private static final String SQL_UPDATE_CLIENT = "UPDATE clients SET name = ?, surname = ?, " +
            "email = ?, role_id = ?, status_id = ?, balance = ?, profile_image = ? " +
            "WHERE clients.id = ?";

    private static final String SQL_FIND_CLIENT_BY_EMAIL = "SELECT " +
            "clients.id, name, surname, email, role, status, balance, profile_image FROM clients " +
            "INNER JOIN client_role ON clients.role_id=client_role.id " +
            "INNER JOIN client_status ON clients.status_id=client_status.id " +
            "WHERE clients.email = ?";

    @Override
    public boolean create(Client entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for ClientDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CLIENT)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setInt(3, entity.getRole().getId());
            statement.setInt(4, entity.getStatus().getId());
            statement.setString(5, entity.getEmail());
            statement.setDouble(6, entity.getBalance());
            statement.setBlob(7, entity.getProfileImage());

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
    public Optional<Client> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for ClientDao");
        }

        Optional<Client> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_CLIENT_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = buildClient(resultSet);
                updated = Optional.of(client);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return updated;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for ClientDao");
        }

        List<Client> clients = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CLIENTS)) {

            while (resultSet.next()) {
                Client client = buildClient(resultSet);
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return clients;
    }

    @Override
    public Optional<Client> update(Client entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for for ClientDao");
        }

        Optional<Client> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getRole().getId());
            statement.setInt(5, entity.getStatus().getId());
            statement.setDouble(6, entity.getBalance());
            statement.setBlob(7, entity.getProfileImage());

            statement.setLong(8, entity.getId());
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
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for ClientDao");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT_BY_ID)) {
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

    Optional<Client> findClientByEmail(String email) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ClientDao");
            throw new DaoException("Database connection is not set for ClientDao");
        }

        Optional<Client> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_CLIENT_BY_EMAIL)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = buildClient(resultSet);
                result = Optional.of(client);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return result;
    }

    private Client buildClient(ResultSet resultSet) throws SQLException {

        Client.Builder builder = new Client.Builder();
        builder.setId(resultSet.getInt(CLIENTS_ID))
                .setName(resultSet.getString(CLIENTS_NAME))
                .setSurname(resultSet.getString(CLIENTS_SURNAME))
                .setRole(ClientRole.valueOf(resultSet
                        .getString(CLIENT_ROLE_ROLE).toUpperCase()))
                .setStatus(ClientStatus.valueOf(resultSet
                        .getString(CLIENT_STATUS_STATUS).toUpperCase()))
                .setEmail(resultSet.getString(CLIENTS_EMAIL))
                .setBalance(resultSet.getDouble(CLIENTS_BALANCE));

        InputStream profileImage = null;
        Blob blob = resultSet.getBlob(CLIENTS_PROFILE_IMAGE);
        if (blob != null) {
            profileImage = blob.getBinaryStream();
        }
        builder.setProfileImage(profileImage);

        return builder.buildClient();
    }
}
