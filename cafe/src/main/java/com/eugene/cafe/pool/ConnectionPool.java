package com.eugene.cafe.pool;

import com.eugene.cafe.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    // todo: connection pool needs to be a singleton

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
            } catch (SQLException e) {
                // todo: write to log
            }
        }

        if (availableConnections.isEmpty()) {
            // todo: write fatal log
            throw new RuntimeException("Unable to populate connection pool");
        }
    }

    public static ConnectionPool getInstance() {
        if (initialized.get()) {
            lock.lock();
            try {
                if (instance != null) {
                    instance = new ConnectionPool();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection() throws ConnectionPoolException {
        try {
            ProxyConnection connection = availableConnections.take();
            busyConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            // todo: write log
            Thread.currentThread().interrupt();
        }
        throw new ConnectionPoolException("Thread was interrupted while performing blocking operation");
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection instanceof ProxyConnection proxyConnection) {
            try {
                busyConnections.take();
                availableConnections.put(proxyConnection);
                return;
            } catch (InterruptedException e) {
                // todo write log
                Thread.currentThread().interrupt();
            }
            throw new ConnectionPoolException("Thread was interrupted while performing blocking operation");
        } else {
            // todo: write log
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                availableConnections.take().reallyClose();
            } catch (InterruptedException e) {
                // todo: write log
            } catch (SQLException e) {
                // todo: write log
            }
        }
    }
}
