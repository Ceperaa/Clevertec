package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class PoolConnect {

    private final BlockingQueue<Connection> connectFree = new ArrayBlockingQueue(5);
    private final Map<String, Connection> connectBusy = new ConcurrentHashMap<>();
    private static final Logger logger = LogManager.getLogger(PoolConnect.class);

    public PoolConnect() {
        SQLConnector connector = new SQLConnector();
        for (int i = 0; i < 5; i++) {
            connectFree.add(connector.getConnection());
        }
    }

    public Connection getConnect() throws InterruptedException {
            if (!connectBusy.containsKey(Thread.currentThread().getName())) {
                Connection take = connectFree.take();
                connectBusy.put(Thread.currentThread().getName(), take);
                return take;
        }
        return connectBusy.get(Thread.currentThread().getName());
    }

    public void close() {
        connectFree.add(connectBusy.remove(Thread.currentThread().getName()));
        logger.debug("connector released");
    }
}
