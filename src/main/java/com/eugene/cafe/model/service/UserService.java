package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> logIn(String email, String password) throws ServiceException;

    Optional<UserDto> signUp(String name, String surname, String email, String password) throws ServiceException;

    Optional<UserDto> editProfile(int id, String name, String surname, String email) throws ServiceException;

    Optional<UserDto> updateProfilePicture(int id, String picturePath) throws ServiceException;

    boolean changeUserPassword(int id, String oldPassword, String newPassword) throws ServiceException;

    List<UserDto> getSubsetOfUsers(int pageNumber) throws ServiceException;

    int getUserCount() throws ServiceException;

    Optional<UserDto> changeUserStatus(int userId, UserStatus status) throws ServiceException;

    Optional<UserDto> topUpUserBalance(int userId, String topUpAmount) throws ServiceException;
}
