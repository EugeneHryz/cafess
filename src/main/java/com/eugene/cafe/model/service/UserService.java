package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> signIn(String email, String password) throws ServiceException;

    Optional<User> signUp(String name, String surname, String email, String password) throws ServiceException;

    Optional<User> editProfile(int id, String name, String surname, String email) throws ServiceException;

    Optional<User> updateProfilePicture(int id, String picturePath) throws ServiceException;

    boolean changeUserPassword(int id, String oldPassword, String newPassword) throws ServiceException;

    List<User> getAllUsersWithoutAdmins() throws ServiceException;
}
