package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;

import java.util.Optional;

public abstract class UserDao extends AbstractDao<User> {

    public abstract Optional<User> findUserByEmail(String email) throws DaoException;

    public abstract Optional<User> updateProfilePicture(int id, String imagePath) throws DaoException;

    public abstract boolean changeUserPassword(int id, String newPassword) throws DaoException;
}
