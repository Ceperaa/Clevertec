package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.runner.util.ApplicationProperties;
import ru.clevertec.check.runner.util.entity.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

    private Connection connection;
    private final Logger logger = LogManager.getLogger(SQLException.class);
    private final DataSource dataSourceYaml = ApplicationProperties.getProperty().getDatasource();

    public Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    private Connection createConnection() {
        Connection connection;
        try {
            Class.forName(dataSourceYaml.getDriver());
            try {
                connection = DriverManager.getConnection(dataSourceYaml.getUrl(),
                        dataSourceYaml.getUsername(),
                        dataSourceYaml.getPassword());
                logger.info("database connection successful");
                return connection;
            } catch (SQLException e) {
                logger.error(e.getMessage());
                return null;
            }
        } catch (Exception e) {
            logger.warn(dataSourceYaml.getDriver() + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
