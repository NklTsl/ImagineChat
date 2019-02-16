/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mahmoud Shereif
 */
public class DatabaseConnection {
    public DatabaseConnection(){
    }
    
    public void loadDatabaseDriver() throws SQLException{
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
    }
    public Connection Connect() throws SQLException{
            loadDatabaseDriver();
            Connection databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ChattingApp","root", "Mennaahmed411");
            return databaseConnection;
    }
    
}
