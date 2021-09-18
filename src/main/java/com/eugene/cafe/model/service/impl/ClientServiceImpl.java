package com.eugene.cafe.model.service.impl;

import com.eugene.cafe.entity.ClientRole;
import com.eugene.cafe.entity.ClientStatus;
import com.eugene.cafe.model.dao.ClientDao;
import com.eugene.cafe.model.dao.impl.ClientDaoImpl;
import com.eugene.cafe.model.dao.TransactionHelper;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.ClientService;
import com.eugene.cafe.util.PasswordEncryptor;
import com.eugene.cafe.util.impl.PasswordEncryptorImpl;
import com.eugene.cafe.model.validator.UserValidator;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    @Override
    public Optional<Client> signIn(String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final ClientDao clientDao = new ClientDaoImpl();

        Optional<Client> result = Optional.empty();
        if (UserValidator.validateEmail(email)) {
            helper.init(clientDao);
            try {
                Optional<Client> client = clientDao.findClientByEmail(email);
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
                try {
                    helper.end();
                } catch (DaoException e) {
                    // todo: write log
                }
            }
        }
        return result;
    }

    @Override
    public Optional<Client> signUp(String name, String surname, String email, String password) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final ClientDao clientDao = new ClientDaoImpl();

        Optional<Client> result = Optional.empty();
        helper.init(clientDao);
        try {
            Optional<Client> oldClient = clientDao.findClientByEmail(email);
            if (oldClient.isEmpty()) {

                Client.Builder builder = new Client.Builder();
                builder.setName(name)
                        .setSurname(surname)
                        .setEmail(email);
                PasswordEncryptor encryptor = new PasswordEncryptorImpl();
                String hashedPassword = encryptor.encryptPassword(password);
                builder.setHashedPassword(hashedPassword)
                        .setRole(ClientRole.USER)
                        .setStatus(ClientStatus.NOT_ACTIVATED);

                Client newClient = builder.buildClient();
                if (clientDao.create(newClient)) {
                    result = Optional.of(newClient);
                }
            }
        } catch (DaoException e) {
            // todo: write log
            System.out.println("DaoException happened " + e);
            throw new ServiceException("Unable to sign up new user", e);
        } finally {
            try {
                helper.end();
            } catch (DaoException e) {
                // todo: write log
            }
        }
        return result;
    }
}
