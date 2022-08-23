package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.runner.util.ApplicationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

    private final String url = ApplicationProperties.getByKey("datasource.url");
    private final String driverClassName = ApplicationProperties.getByKey("datasource.driver-class-name");
    private final String user= ApplicationProperties.getByKey("datasource.username");
    private final String password = ApplicationProperties.getByKey("datasource.password");
    private Connection connection;
    private final Logger logger = LogManager.getLogger(SQLException.class);

    public Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    private Connection createConnection() {
        Connection connection;
        try {
            Class.forName(driverClassName);
            try {
                connection = DriverManager.getConnection(url,
                        user,
                        password);
                logger.info("database connection successful");
                return connection;
            } catch (SQLException e) {
                logger.error(e.getMessage());
                return null;
            }
        } catch (Exception e) {
            logger.warn(driverClassName + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
