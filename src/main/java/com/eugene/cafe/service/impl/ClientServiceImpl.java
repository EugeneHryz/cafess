package com.eugene.cafe.service.impl;

import com.eugene.cafe.dao.impl.ClientDao;
import com.eugene.cafe.dao.TransactionHelper;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.service.ClientService;
import com.eugene.cafe.util.PasswordEncryptor;
import com.eugene.cafe.validator.UserValidator;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final TransactionHelper helper = new TransactionHelper();
    private final ClientDao clientDao = new ClientDao();

    @Override
    public Optional<Client> signIn(String email, String password) throws ServiceException {

        Optional<Client> result = Optional.empty();
        if (UserValidator.validateEmail(email)) {
            helper.init(clientDao);
            try {
                Optional<Client> client = clientDao.findClientByEmail(email);
                helper.end();
                if (client.isPresent()) {

                    if (PasswordEncryptor.checkPassword(password, client.get().getHashedPassword())) {
                        result = client;
                    }
                }

            } catch (DaoException e) {
                // todo: write log
                throw new ServiceException("Unable to find client by email", e);
            }
        }
        return result;
    }
}
