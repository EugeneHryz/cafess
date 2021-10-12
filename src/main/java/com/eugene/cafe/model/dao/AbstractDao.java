package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.AbstractEntity;
import com.eugene.cafe.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends AbstractEntity> {

    protected Connection connection;

    public abstract boolean create(T entity) throws DaoException;

    public abstract Optional<T> findById(int id) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> update(T entity) throws DaoException;

    public abstract boolean deleteById(int id) throws DaoException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
