package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.AbstractEntity;
import com.eugene.cafe.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Base class for dao subclasses
 *
 * @param <T> entity
 */
public abstract class AbstractDao<T extends AbstractEntity> {

    /**
     * connection used by dao methods
     */
    protected Connection connection;

    /**
     * create entity
     *
     * @param entity entity
     * @return true, if successful
     * @throws DaoException if error
     */
    public abstract boolean create(T entity) throws DaoException;

    /**
     * find entity by id
     *
     * @param id entity id
     * @return optional
     * @throws DaoException if error
     */
    public abstract Optional<T> findById(int id) throws DaoException;

    /**
     * find all entities
     *
     * @return list of entities
     * @throws DaoException if error
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * update entity
     *
     * @param entity the entity
     * @return optional
     * @throws DaoException if error
     */
    public abstract Optional<T> update(T entity) throws DaoException;

    /**
     * delete entity by id
     *
     * @param id entity id
     * @return true, if successful
     * @throws DaoException if error
     */
    public abstract boolean deleteById(int id) throws DaoException;

    /**
     * sets connection
     *
     * @param connection connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
