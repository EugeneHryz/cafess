package com.eugene.cafe.model.pool;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionPoolTest {

    static ConnectionPool connectionPool;

    @BeforeAll
    static void setUp() {
        connectionPool = ConnectionPool.getInstance();
    }

    @AfterAll
    static void done() {
        connectionPool.destroyPool();
    }

    @Test
    public void takeConnectionShouldBeCorrect() {
        Connection connection = connectionPool.takeConnection();
        try {
            assertTrue(connection instanceof ProxyConnection);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
