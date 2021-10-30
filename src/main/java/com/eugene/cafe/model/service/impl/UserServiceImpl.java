package com.eugene.cafe.model.service.impl;

import com.eugene.cafe.entity.UserRole;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.model.dao.UserDao;
import com.eugene.cafe.model.dao.impl.UserDaoImpl;
import com.eugene.cafe.model.dao.TransactionHelper;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dto.UserDto;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.util.PasswordEncryptor;
import com.eugene.cafe.util.impl.PasswordEncryptorImpl;
import com.eugene.cafe.model.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public static final int USERS_PER_PAGE = 8;

    @Override
    public Optional<UserDto> logIn(String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<UserDto> result = Optional.empty();
        helper.init(userDao);
        try {
            if (UserValidator.validateEmail(email) && UserValidator.validatePassword(password)) {

                Optional<User> user = userDao.findByEmail(email);
                if (user.isPresent()) {
                    PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                    if (encryptor.checkPassword(password, user.get().getHashedPassword())) {

                        result = Optional.of(new UserDto(user.get()));
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Unable to find client by email", e);
            throw new ServiceException("Unable to find client by email", e);
        } finally {
            helper.end();
        }
        return result;
    }

    @Override
    public Optional<UserDto> signUp(String name, String surname, String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<UserDto> result = Optional.empty();
        helper.init(userDao);
        try {
            Optional<User> user = userDao.findByEmail(email);
            if (user.isEmpty()) {

                User.Builder builder = new User.Builder();
                builder.setName(name)
                        .setSurname(surname)
                        .setEmail(email);
                PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                String hashedPassword = encryptor.encryptPassword(password);
                builder.setHashedPassword(hashedPassword)
                        .setRole(UserRole.USER)
                        .setStatus(UserStatus.ACTIVE);

                User newUser = builder.buildUser();
                if (userDao.create(newUser)) {
                    result = Optional.of(new UserDto(newUser));
                }
            }
        } catch (DaoException e) {
            logger.error("Unable to sign up new user", e);
            throw new ServiceException("Unable to sign up new user", e);
        } finally {
            helper.end();
        }
        return result;
    }

    @Override
    public Optional<UserDto> editProfile(int id, String name, String surname, String email) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<UserDto> edited = Optional.empty();
        helper.init(userDao);
        try {
            if (UserValidator.validateUser(name, surname, email)) {

                Optional<User> userToEdit = userDao.findById(id);
                if (userToEdit.isPresent()) {

                    User user = userToEdit.get();
                    user.setName(name);
                    user.setSurname(surname);
                    user.setEmail(email);

                    Optional<User> updatedUser = userDao.update(user);
                    if (updatedUser.isPresent()) {
                        edited = Optional.of(new UserDto(updatedUser.get()));
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Unable to update user profile", e);
            throw new ServiceException("Unable to update user profile", e);
        } finally {
            helper.end();
        }
        return edited;
    }

    @Override
    public Optional<UserDto> updateProfilePicture(int id, String picturePath) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<UserDto> userDto = Optional.empty();
        helper.init(userDao);
        try {
             Optional<User> updated = userDao.updateProfilePicture(id, picturePath);
             if (updated.isPresent()) {
                 userDto = Optional.of(new UserDto(updated.get()));
             }
        } catch (DaoException e) {
            logger.error("Unable to update profile picture", e);
            throw new ServiceException("Unable to update profile picture", e);
        } finally {
            helper.end();
        }
        return userDto;
    }

    @Override
    public boolean changeUserPassword(int id, String oldPassword, String newPassword) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        boolean passwordChanged = false;
        try {
            if (UserValidator.validatePassword(newPassword)) {
                Optional<User> user = userDao.findById(id);
                if (user.isPresent()) {

                    PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                    if (encryptor.checkPassword(oldPassword, user.get().getHashedPassword())) {

                        String hashed = encryptor.encryptPassword(newPassword);
                        passwordChanged = userDao.changePassword(id, hashed);
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Unable to change user password", e);
            throw new ServiceException("Unable to change user password", e);
        } finally {
            helper.end();
        }
        return passwordChanged;
    }

    @Override
    public List<UserDto> getSubsetOfUsers(int pageNumber) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        List<UserDto> usersDto;
        int offset = USERS_PER_PAGE * (pageNumber - 1);
        try {
            List<User> users = userDao.getSubsetOfUsers(USERS_PER_PAGE, offset);
            usersDto = users.stream().map(UserDto::new).toList();

        } catch (DaoException e) {
            logger.error("Unable to get a subset of users", e);
            throw new ServiceException("Unable to get a subset of users", e);
        } finally {
            helper.end();
        }
        return usersDto;
    }

    @Override
    public int getUserCount() throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        int count;
        try {
            count = userDao.getCount();
        } catch (DaoException e) {
            logger.error("Unable to get user count", e);
            throw new ServiceException("Unable to get user count", e);
        } finally {
            helper.end();
        }
        return count;
    }

    @Override
    public Optional<UserDto> changeUserStatus(int userId, UserStatus status) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        Optional<UserDto> userDto = Optional.empty();
        try {
            Optional<User> user = userDao.findById(userId);
            if (user.isPresent()) {
                user.get().setStatus(status);

                Optional<User> updatedUser = userDao.update(user.get());
                if (updatedUser.isPresent()) {
                    userDto = Optional.of(new UserDto(updatedUser.get()));
                }
            }
        } catch (DaoException e) {
            logger.error("Unable to ban the user (id: " + userId + ")", e);
            throw new ServiceException("Unable to ban the user (id: " + userId + ")", e);
        } finally {
            helper.end();
        }
        return userDto;
    }

    @Override
    public Optional<UserDto> topUpUserBalance(int userId, String topUpAmount) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        Optional<UserDto> userDto = Optional.empty();
        try {
            if (UserValidator.validateTopUpAmount(topUpAmount)) {

                Optional<User> userOptional = userDao.findById(userId);
                if (userOptional.isPresent()) {

                    double amount = Double.parseDouble(topUpAmount);
                    User user = userOptional.get();
                    user.setBalance(user.getBalance() + amount);

                    Optional<User> updated = userDao.update(user);
                    if (updated.isPresent()) {
                        userDto = Optional.of(new UserDto(updated.get()));
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Failed to top up user balance", e);
            throw new ServiceException("Failed to top up user balance", e);
        } finally {
            helper.end();
        }
        return userDto;
    }
}
