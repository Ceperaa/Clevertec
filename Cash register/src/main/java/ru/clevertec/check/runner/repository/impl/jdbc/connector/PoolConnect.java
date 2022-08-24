package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PoolConnect {

    private final BlockingQueue<Connection> connectFree = new ArrayBlockingQueue(50);
    private final Map<String, Connection> connectBusy = new ConcurrentHashMap<>();
    private static final Logger logger = LogManager.getLogger(PoolConnect.class);

    public PoolConnect(ObjectProvider<SQLConnector> sqlConnectors) {
        Connection connection = sqlConnectors.getIfAvailable().getConnection();
        for (int i = 0; i < 50; i++) {
            connectFree.add(connection);
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
