/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Message_Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Message_StatusDAO implements DAO<Message_Status> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public Message_StatusDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Message_Status messageStatus) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`message_status` (`ID`, `Description`) VALUES (?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(messageStatus.getId());
        parameterList.add(messageStatus.getDescription());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }
    
    @Override
    public void update(Message_Status messageStatus) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`message_status` SET `Description` = ? WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(messageStatus.getDescription());
        parameterList.add(messageStatus.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`message_status` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }
    
    @Override
    public Message_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `mesage_status`.`ID`, `mesage_status`.`Description` "
                + "FROM `chattingapp`.`mesage_status` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Message_Status messageStatus = new Message_Status();
        if(queryResult.next())
        {
            messageStatus.setId(queryResult.getByte(1));
            messageStatus.setDescription(queryResult.getString(2));
        }
        return messageStatus;
    }
    
    @Override
    public List<Message_Status> getAll() throws SQLException {
        String getAllQuery = "SELECT `mesage_status`.`ID`, `mesage_status`.`Description` "
                + "FROM `chattingapp`.`mesage_status` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Message_Status> messageStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Message_Status messageStatus = new Message_Status();
            messageStatus.setId(queryResult.getByte(1));
            messageStatus.setDescription(queryResult.getString(2));
            messageStatusList.add(messageStatus);
        }
        
        
        return messageStatusList;
    }

    @Override
    public List<Message_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `mesage_status`.`ID`, `mesage_status`.`Description` "
                + "FROM `chattingapp`.`mesage_status`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Message_Status> messageStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Message_Status messageStatus = new Message_Status();
            messageStatus.setId(queryResult.getByte(1));
            messageStatus.setDescription(queryResult.getString(2));
            messageStatusList.add(messageStatus);
        }
        
        
        return messageStatusList;
    }
    
}
