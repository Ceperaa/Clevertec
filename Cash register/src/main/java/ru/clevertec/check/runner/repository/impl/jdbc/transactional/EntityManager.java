package ru.clevertec.check.runner.repository.impl.jdbc.transactional;

import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.PoolConnect;

import java.sql.Connection;

@Component
public class EntityManager {

    private final PoolConnect connector = new PoolConnect();

    public EntityManager() {
    }

    public Connection getConnect() throws InterruptedException {
       return connector.getConnect();
    }

    public void close() {
        connector.close();
    }
}
