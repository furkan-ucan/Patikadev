package com.patikadev.Helper;

import java.sql.Connection;
public class DBConnector {
    private  Connection connection = null;

    public Connection connect(){
        try {
            this.connection = java.sql.DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this.connection;
    }

    public static  Connection getConnection(){
        DBConnector db = new DBConnector();
        return db.connect();
    }
}
