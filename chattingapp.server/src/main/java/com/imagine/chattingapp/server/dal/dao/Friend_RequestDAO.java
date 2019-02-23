/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Friend_Request;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Friend_RequestDAO implements DAO<Friend_Request> {

    DatabaseDataRetreival databaseDataRetreival = null;

    public Friend_RequestDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    
    @Override
    public void persist(Friend_Request friendRequest) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`friend_request` (`Sender_Phone_Number`, `Receiver_Phone_Number`, `Status_ID`) VALUES (?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(friendRequest.getSenderPhoneNumber());
        parameterList.add(friendRequest.getReceiverPhoneNumber());
        parameterList.add(friendRequest.getStatusID());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(Friend_Request friendRequest) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`friend_request` SET `Status_ID` = ? "
                + "WHERE (`Sender_Phone_Number` = ?) and (`Receiver_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(friendRequest.getStatusID());
        parameterList.add(friendRequest.getSenderPhoneNumber());
        parameterList.add(friendRequest.getReceiverPhoneNumber());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`friend_request` "
                + "WHERE (`Sender_Phone_Number` = ?) and (`Receiver_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public Friend_Request getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `friend_request`.`Sender_Phone_Number`, "
                + "`friend_request`.`Receiver_Phone_Number`, "
                + "`friend_request`.`Status_ID` FROM `chattingapp`.`friend_request` "
                + "WHERE (`Sender_Phone_Number` = ?) and (`Receiver_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Friend_Request friendRequest = null;
        if(queryResult.next())
        {
            friendRequest = new Friend_Request();
            friendRequest.setSenderPhoneNumber(queryResult.getString(1));
            friendRequest.setReceiverPhoneNumber(queryResult.getString(2));
            friendRequest.setStatusID(queryResult.getByte(3));
        }
        return friendRequest;
    }

    @Override
    public List<Friend_Request> getAll() throws SQLException {
        String getAllQuery = "SELECT `friend_request`.`Sender_Phone_Number`, "
                + "`friend_request`.`Receiver_Phone_Number`, "
                + "`friend_request`.`Status_ID` FROM `chattingapp`.`friend_request`";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Friend_Request> friendRequestList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Friend_Request friendRequest = new Friend_Request();
            friendRequest.setSenderPhoneNumber(queryResult.getString(1));
            friendRequest.setReceiverPhoneNumber(queryResult.getString(2));
            friendRequest.setStatusID(queryResult.getByte(3));
            friendRequestList.add(friendRequest);
        }
        
        
        return friendRequestList;
    }

    @Override
    public List<Friend_Request> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `friend_request`.`Sender_Phone_Number`, "
                + "`friend_request`.`Receiver_Phone_Number`, "
                + "`friend_request`.`Status_ID` FROM `chattingapp`.`friend_request` "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Friend_Request> friendRequestList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Friend_Request friendRequest = new Friend_Request();
            friendRequest.setSenderPhoneNumber(queryResult.getString(1));
            friendRequest.setReceiverPhoneNumber(queryResult.getString(2));
            friendRequest.setStatusID(queryResult.getByte(3));
            friendRequestList.add(friendRequest);
        }
        
        
        return friendRequestList;
    }
    
}
