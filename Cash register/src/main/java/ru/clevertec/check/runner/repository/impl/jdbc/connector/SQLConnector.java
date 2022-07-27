package ru.clevertec.check.runner.repository.impl.jdbc.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

    private final static String url = "jdbc:postgresql://localhost:5432/cash_register";
    private final static String driverClassName = "org.postgresql.Driver";
    private final static String user = "postgres";
    private final static String password = "root";
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
            Class.forName("org.postgresql.Driver");
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
