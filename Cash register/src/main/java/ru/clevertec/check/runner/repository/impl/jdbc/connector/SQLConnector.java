package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
 public class SQLConnector {

    private final String url;
    private final String driver;
    private final String user;
    private final String password;

   private Connection connection;

    public SQLConnector(String url, String driver, String user, String password) {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    @SneakyThrows
    private Connection createConnection() {
        Connection connection;
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url,
                        user,
                        password);
                log.info("database connection successful");
                return connection;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            log.warn(driver + e.getMessage());
            e.printStackTrace();
            throw  e;
        }
    }
}
