// In src/DatabaseConnection.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnection {
    // --- IMPORTANT: configure your DB via environment variables or edit the defaults ---
    private static final String DEFAULT_DB_HOST = "localhost";
    private static final String DEFAULT_DB_PORT = "3306";
    private static final String DEFAULT_DB_NAME = "adoptions";
    private static final String DEFAULT_DB_USER = "root";
    private static final String DEFAULT_DB_PASSWORD = "admin";

    private static Connection connection = null;

    /**
     * Establishes a connection to the database.
     * @return a Connection object or null if connection fails.
     */
    public static Connection getConnection() {
        try {
            // Build connection values from environment variables if present
            String host = Optional.ofNullable(System.getenv("DB_HOST")).orElse(DEFAULT_DB_HOST);
            String port = Optional.ofNullable(System.getenv("DB_PORT")).orElse(DEFAULT_DB_PORT);
            String name = Optional.ofNullable(System.getenv("DB_NAME")).orElse(DEFAULT_DB_NAME);
            String user = Optional.ofNullable(System.getenv("DB_USER")).orElse(DEFAULT_DB_USER);
            String password = Optional.ofNullable(System.getenv("DB_PASSWORD")).orElse(DEFAULT_DB_PASSWORD);

            String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC";

            // Attempt to connect to the database
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Database connection successful!");
            return connection;

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the database connection if it is open.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

