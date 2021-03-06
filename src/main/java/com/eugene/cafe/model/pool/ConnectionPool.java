package com.eugene.cafe.model.pool;

import com.eugene.cafe.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final ReentrantLock lock = new ReentrantLock();

    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private static final int DEFAULT_POOL_SIZE = 8;

    private static ConnectionPool instance;

    private final BlockingQueue<ProxyConnection> availableConnections;

    private final BlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {

        availableConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
        busyConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = ConnectionFactory.createConnection();
                availableConnections.offer(connection);
            } catch (DatabaseConnectionException e) {
                logger.error("Unable to create connection " + e);
            }
        }

        if (availableConnections.isEmpty()) {
            logger.fatal("Unable to populate connection pool");
            throw new RuntimeException("Unable to populate connection pool");
        }
    }

    public static ConnectionPool getInstance() {
        if (!initialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted while performing blocking operation " + e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxyConnection) {
            try {
                busyConnections.remove(proxyConnection);
                availableConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.error("Thread was interrupted while performing blocking operation " + e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.warn("You can't put your own connection into the pool");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                availableConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Thread was interrupted while performing blocking operation " + e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Unable to close a connection " + e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.drivers().forEach(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Unable to deregister a driver " + e);
            }
        });
    }
}
