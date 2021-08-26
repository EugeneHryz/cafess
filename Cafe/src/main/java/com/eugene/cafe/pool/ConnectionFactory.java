package com.eugene.cafe.pool;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String dbUrl;

    private static final Properties properties;
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            // todo: FATAL ERROR
            e.printStackTrace();
        }

        dbUrl = "jdbc:mysql://localhost:3306/cafe_db";

        properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "zglwvz671CAj");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "true");
        properties.put("useJDBCCompliantTimezoneShift", "true");
        properties.put("useLegacyDatetimeCode", "false");
        properties.put("serverTimezone", "UTC");
        properties.put("serverSslCert", "classpath:server.crt");
    }

    private ConnectionFactory() { }

    public static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
