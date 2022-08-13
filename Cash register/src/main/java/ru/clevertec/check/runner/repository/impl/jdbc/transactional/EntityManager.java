package ru.clevertec.check.runner.repository.impl.jdbc.transactional;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.PoolConnect;

import java.sql.Connection;

@Component
public class EntityManager {

    private final PoolConnect connector = new PoolConnect();

    public EntityManager() {
    }


    @SneakyThrows(InterruptedException.class)
    public Connection getConnect() {
       return connector.getConnect();
    }

    public void close() {
        connector.close();
    }
}
