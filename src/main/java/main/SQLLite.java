package main;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLLite {

    private static Connection connection;
    private static Statement statement;

    public static void connectToDataBase () {
        connection = null;
        try {
            File file = new File("testDataBase.db");
            if(!file.exists()){
                file.createNewFile();
            }

            String url = "jdbc:sqlite:" + file.getPath();
            connection = DriverManager.getConnection(url);

            System.out.println("Connected to Database");

            statement = connection.createStatement();


        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnectFromDataBase () {
        try {
            if (connection != null) {
                connection.close();

                System.out.println("Disconnected from Database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void update(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet onQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
