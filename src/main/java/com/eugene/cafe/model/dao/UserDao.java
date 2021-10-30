package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * UserDao abstract class
 */
public abstract class UserDao extends AbstractDao<User> {

    /**
     * find user by email
     *
     * @param email email
     * @return optional User
     * @throws DaoException if error
     */
    public abstract Optional<User> findByEmail(String email) throws DaoException;

    /**
     * update user profile picture
     *
     * @param id user id
     * @param imagePath relative path to the new image
     * @return optional User
     * @throws DaoException if error
     */
    public abstract Optional<User> updateProfilePicture(int id, String imagePath) throws DaoException;

    /**
     * change user password
     *
     * @param id user id
     * @param newPassword new plain-text password
     * @return true, if successful
     * @throws DaoException if error
     */
    public abstract boolean changePassword(int id, String newPassword) throws DaoException;

    /**
     * get subset of users with specified limit and offset
     *
     * @param limit limit of menu items in the subset
     * @param offset offset from the beginning of the result set
     * @return list of users
     * @throws DaoException if error
     */
    public abstract List<User> getSubsetOfUsers(int limit, int offset) throws DaoException;

    /**
     * get number of all users
     *
     * @return number of users
     * @throws DaoException if error
     */
    public abstract int getCount() throws DaoException;
}
