package com.eugene.cafe.model.dao;

import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {

    private Connection connection;

    public void beginTransaction(AbstractDao dao, AbstractDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
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
            throw new DaoException("Database error occurred", e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // todo: write to log
            throw new DaoException("Database error occurred", e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }
        connection = null;
    }

    public void init(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }

        dao.setConnection(connection);
    }

    public void end() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            // todo: write log
            throw new DaoException("Database error occurred", e);
        }
        connection = null;
    }
}
