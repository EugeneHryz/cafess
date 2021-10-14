package com.eugene.cafe.model.service.impl;

import com.eugene.cafe.entity.UserRole;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.model.dao.UserDao;
import com.eugene.cafe.model.dao.impl.UserDaoImpl;
import com.eugene.cafe.model.dao.TransactionHelper;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
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

    public static final int USERS_PER_PAGE = 2;

    @Override
    public Optional<User> signIn(String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<User> result = Optional.empty();
        if (UserValidator.validateEmail(email)) {
            helper.init(userDao);
            try {
                Optional<User> client = userDao.findUserByEmail(email);
                if (client.isPresent()) {
                    PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                    if (encryptor.checkPassword(password, client.get().getHashedPassword())) {
                        result = client;
                    }
                }
            } catch (DaoException e) {
                // todo: write log
                throw new ServiceException("Unable to find client by email", e);
            } finally {
                helper.end();
            }
        }
        return result;
    }

    @Override
    public Optional<User> signUp(String name, String surname, String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<User> result = Optional.empty();
        helper.init(userDao);
        try {
            Optional<User> oldClient = userDao.findUserByEmail(email);
            if (oldClient.isEmpty()) {

                User.Builder builder = new User.Builder();
                builder.setName(name)
                        .setSurname(surname)
                        .setEmail(email);
                PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                String hashedPassword = encryptor.encryptPassword(password);
                builder.setHashedPassword(hashedPassword)
                        .setRole(UserRole.USER)
                        .setStatus(UserStatus.NOT_ACTIVATED);

                User newUser = builder.buildUser();
                if (userDao.create(newUser)) {
                    result = Optional.of(newUser);
                }
            }
        } catch (DaoException e) {
            // todo: write log
            throw new ServiceException("Unable to sign up new user", e);
        } finally {
            helper.end();
        }
        return result;
    }

    @Override
    public Optional<User> editProfile(int id, String name, String surname, String email) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        User.Builder builder = new User.Builder();
        builder.setId(id)
                .setName(name)
                .setSurname(surname)
                .setEmail(email);
        User user = builder.buildUser();

        Optional<User> edited = Optional.empty();
        helper.init(userDao);
        try {
            Optional<User> updatedUser = userDao.update(user);

            if (updatedUser.isPresent()) {
                edited = updatedUser;
            }
        } catch (DaoException e) {
            // todo write log
            throw new ServiceException("Unable to update user profile", e);
        } finally {
            helper.end();
        }
        return edited;
    }

    @Override
    public Optional<User> updateProfilePicture(int id, String picturePath) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        Optional<User> updated;
        helper.init(userDao);
        try {
            updated = userDao.updateProfilePicture(id, picturePath);
        } catch (DaoException e) {
            // todo: write log
            throw new ServiceException("Unable to update profile picture", e);
        } finally {
            helper.end();
        }
        return updated;
    }

    @Override
    public boolean changeUserPassword(int id, String oldPassword, String newPassword) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        boolean passwordChanged = false;
        try {
            Optional<User> user = userDao.findById(id);
            if (user.isPresent()) {

                PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                if (encryptor.checkPassword(oldPassword, user.get().getHashedPassword())) {

                    String hashed = encryptor.encryptPassword(newPassword);
                    passwordChanged = userDao.changeUserPassword(id, hashed);
                }
            }
        } catch (DaoException e) {
            // todo: write log
            throw new ServiceException("Unable to change user password", e);
        } finally {
            helper.end();
        }
        return passwordChanged;
    }

    @Override
    public List<User> getSubsetOfUsers(int pageNumber) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final UserDao userDao = new UserDaoImpl();

        helper.init(userDao);
        List<User> users;
        // todo: validate page number
        int offset = USERS_PER_PAGE * (pageNumber - 1);
        try {
            users = userDao.getSubsetOfUsers(USERS_PER_PAGE, offset);

        } catch (DaoException e) {
            logger.error("Unable to get a subset of users", e);
            throw new ServiceException("Unable to get a subset of users", e);
        } finally {
            helper.end();
        }
        return users;
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
}
