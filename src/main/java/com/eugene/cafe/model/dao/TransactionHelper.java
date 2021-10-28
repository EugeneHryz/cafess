package com.eugene.cafe.model.dao;

import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {

    private static final Logger logger = LogManager.getLogger(TransactionHelper.class);

    private Connection connection;

    public void beginTransaction(AbstractDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }

        for (AbstractDao d : daos) {
            d.setConnection(connection);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Database error occurred", e);
            throw new DaoException("Database error occurred", e);
        }
        ConnectionPool.getInstance().releaseConnection(connection);
        connection = null;
    }

    public void init(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        dao.setConnection(connection);
    }

    public void end() {
        ConnectionPool.getInstance().releaseConnection(connection);
        connection = null;
    }
}
