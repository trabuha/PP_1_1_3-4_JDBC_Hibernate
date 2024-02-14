package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    //private static final String connection = null;
    private static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "trab";
    private static final String PASSWORD = "trab";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}