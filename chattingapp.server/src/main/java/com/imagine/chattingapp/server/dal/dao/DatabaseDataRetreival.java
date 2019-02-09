/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.imagine.chattingapp.server.dal.config.DatabaseConnection;

/**
 *
 * @author Mahmoud Shereif
 */
public class DatabaseDataRetreival {
    Connection databaseConnection;
    Statement sqlStatment;
    ResultSet queryResult;
    public DatabaseDataRetreival() throws SQLException{
        this.databaseConnection = new DatabaseConnection().Connect();
    }
    
    public ResultSet executeSelectQuery(String sqlQuery) throws SQLException{
        CallableStatement statment = databaseConnection.prepareCall("call persistUserStatus(?)");
        statment.setInt(1, 5);
        queryResult = statment.executeQuery();
        while(queryResult.next()){
            System.out.println(queryResult.getInt(1) + " " + queryResult.getString(2));
        }
        return queryResult;
    }
    public int executeUpdateQuery(String sqlQuery) throws SQLException{
        Integer updatedRowsNumber = null ;
        updatedRowsNumber = sqlStatment.executeUpdate(sqlQuery);
        return updatedRowsNumber;
    }
}
