package com.eugene.cafe.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private static final String DB_URL;

    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);

            DB_URL = properties.getProperty("db.url");
            String dbDriver = properties.getProperty("db.driver");

            Class.forName(dbDriver);

        } catch (IOException e) {
            logger.fatal("Unable to load database configuration file " + e);
            throw new RuntimeException("Unable to load database configuration file " + e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Unable to register database driver " + e);
            throw new RuntimeException("Unable to register database driver " + e);
        }
    }

    private ConnectionFactory() { }

    static ProxyConnection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, properties);
        return new ProxyConnection(connection);
    }
}
