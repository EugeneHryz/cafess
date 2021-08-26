package com.eugene.cafe._main;

import com.eugene.cafe.dao.ClientDao;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.entity.ClientRole;
import com.eugene.cafe.entity.ClientStatus;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.pool.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        try {
//            Connection connection = ConnectionFactory.createConnection();
//
//            ClientDao clientDao = new ClientDao();
//            clientDao.setConnection(connection);
//
//            Client.Builder builder = new Client.Builder();
//                builder.setName("Danil")
//                        .setSurname("qujhggg")
//                        .setRole(ClientRole.USER)
//                        .setStatus(ClientStatus.ACTIVATED)
//                        .setBalance(76.90);
//
//            if (clientDao.deleteById(4)) {
//                System.out.println("client id=4 deleted");
//            }
//
//        } catch (SQLException | DaoException e) {
//            e.printStackTrace();
//        }

    }
}
