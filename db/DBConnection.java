package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cityinfo", // your DB
                "root",                                 // your username
                "cse1234"                    //  use your MySQL password
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
