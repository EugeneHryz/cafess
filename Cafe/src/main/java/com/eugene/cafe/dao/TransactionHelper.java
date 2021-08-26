package com.eugene.cafe.dao;

import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.pool.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {

    // todo: replace with proxy connection
    private Connection connection;

    public void beginTransaction(AbstractDao dao, AbstractDao... daos) throws DaoException {
        if (connection == null) {
            // todo: get connection from pool
            connection = ConnectionFactory.createConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred " + e);
        }
        dao.setConnection(connection);
        for (AbstractDao d : daos) {
            d.setConnection(connection);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            // todo: write to log
            throw new DaoException("Database error occurred " + e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred " + e);
        }
        // todo: return connection to pool
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }

    public void init(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionFactory.createConnection();
        }

        dao.setConnection(connection);
    }

    public void end() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }
}
