package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
@RequiredArgsConstructor
public class EntityManager {

    private final PoolConnect connector;

    @SneakyThrows(InterruptedException.class)
    public Connection getConnect() {
       return connector.getConnect();
    }

    public void close() {
        connector.close();
    }
}
