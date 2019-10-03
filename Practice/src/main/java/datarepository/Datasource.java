package main.java.datarepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    private Connection conn;

    private static final String CONNECTION_STRING = String.format("localhost", "5432", "postgres");
    private static final String USER = "luca";
    private static final String PASS = "parola";

    public Connection open() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Trouble connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Trouble closing the database connection: " + e.getMessage());
        }
    }
}
