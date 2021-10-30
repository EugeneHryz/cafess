package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * UserService interface
 */
public interface UserService {

    /**
     * log in user (find user with given email and password)
     *
     * @param email user email
     * @param password user plain-text password
     * @return Optional User
     * @throws ServiceException if error
     */
    Optional<UserDto> logIn(String email, String password) throws ServiceException;

    /**
     * sign up user (create a user in database with given email, password, etc.)
     *
     * @param name user name
     * @param surname user surname
     * @param email user email
     * @param password user plain-text password
     * @return Optional UserDto
     * @throws ServiceException if error
     */
    Optional<UserDto> signUp(String name, String surname, String email, String password) throws ServiceException;

    /**
     * edit user name, surname and email
     *
     * @param id user id
     * @param name new name
     * @param surname new surname
     * @param email new email
     * @return Optional UserDto
     * @throws ServiceException if error
     */
    Optional<UserDto> editProfile(int id, String name, String surname, String email) throws ServiceException;

    /**
     * update user profile picture
     *
     * @param id use id
     * @param picturePath relative path to the new image
     * @return Optional UserDto
     * @throws ServiceException if error
     */
    Optional<UserDto> updateProfilePicture(int id, String picturePath) throws ServiceException;

    /**
     * change user password
     *
     * @param id user id
     * @param oldPassword old plain-text password
     * @param newPassword new plain-text password
     * @return true, if successful
     * @throws ServiceException if error
     */
    boolean changeUserPassword(int id, String oldPassword, String newPassword) throws ServiceException;

    /**
     * get subset of users by page
     *
     * @param pageNumber page number
     * @return list of users
     * @throws ServiceException if error
     */
    List<UserDto> getSubsetOfUsers(int pageNumber) throws ServiceException;

    /**
     * get number of all users
     *
     * @return number of users
     * @throws ServiceException if error
     */
    int getUserCount() throws ServiceException;

    /**
     * change user status (ban or unban user)
     *
     * @param userId user id
     * @param status new status
     * @return Optional UserDto
     * @throws ServiceException if error
     */
    Optional<UserDto> changeUserStatus(int userId, UserStatus status) throws ServiceException;

    /**
     * top up user balance
     *
     * @param userId user id
     * @param topUpAmount top up amount
     * @return Optional UserDto
     * @throws ServiceException if error
     */
    Optional<UserDto> topUpUserBalance(int userId, String topUpAmount) throws ServiceException;
}
