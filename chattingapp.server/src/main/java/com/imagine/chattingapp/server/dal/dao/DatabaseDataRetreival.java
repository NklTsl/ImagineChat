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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class DatabaseDataRetreival {
    Connection databaseConnection;
    public DatabaseDataRetreival() throws SQLException{
        this.databaseConnection = new DatabaseConnection().Connect();
    }
    
    public ResultSet executeSelectQuery(String preparedQuery, List<Object> parameterList) throws SQLException{
        PreparedStatement sqlPreparedStatment = databaseConnection.prepareStatement(preparedQuery);
        setStatmentParameters(sqlPreparedStatment, parameterList);
        return sqlPreparedStatment.executeQuery();
    }
    public int executeUpdateQuery(String preparedQuery, List<Object> parameterList) throws SQLException{
        PreparedStatement sqlPreparedStatment = databaseConnection.prepareStatement(preparedQuery);
        setStatmentParameters(sqlPreparedStatment, parameterList);
        sqlPreparedStatment.execute();
        return sqlPreparedStatment.getUpdateCount();
    }
    public void setStatmentParameters(PreparedStatement preparedStatment, List<Object> parameterList) throws SQLException
    {
        for(int i = 0 ; i < parameterList.size() ; i++){
            if(parameterList.get(i) instanceof Integer)
                preparedStatment.setInt(i + 1, (Integer)parameterList.get(i));
            else if(parameterList.get(i) instanceof String)
                preparedStatment.setString(i + 1, (String)parameterList.get(i));
            else if(parameterList.get(i) instanceof Short)
                preparedStatment.setShort(i + 1, (Short)parameterList.get(i));
            else if(parameterList.get(i) instanceof Byte)
                preparedStatment.setByte(i + 1, (Byte)parameterList.get(i));
            else if(parameterList.get(i) instanceof Boolean)
                preparedStatment.setByte(i + 1, (parameterList.get(i)).equals(false)? (byte)0:(byte)1);
            else if(parameterList.get(i) instanceof Byte[])
                preparedStatment.setBytes(i + 1, (byte[])parameterList.get(i));
            else if(parameterList.get(i) instanceof Date)
                preparedStatment.setDate(i + 1, (Date)parameterList.get(i));
            else if(parameterList.get(i) instanceof Timestamp)
                preparedStatment.setTimestamp(i + 1, (Timestamp)parameterList.get(i));
            else
                preparedStatment.setNull(i + 1, java.sql.Types.NULL);
        }
    }
}
