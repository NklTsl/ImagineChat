/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.Friend;
import com.imagine.chattingapp.server.dal.entity.One_To_One_Messages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class One_To_One_MessagesDAO implements DAO<One_To_One_Messages> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public One_To_One_MessagesDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    
    @Override
    public void persist(One_To_One_Messages oneToOneMessage) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`one_to_one_messages` "
                + "(`Sender_Phone_Number`, `Receiver_Phone_Number`, `Timestamp`, "
                + "`Message_Content_Or_File_Name`, `File`, `Status`) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(oneToOneMessage.getSenderPhoneNumber());
        parameterList.add(oneToOneMessage.getReceiverPhoneNumber());
        parameterList.add(oneToOneMessage.getTimestamp());
        parameterList.add(oneToOneMessage.getMessageContentOrFileName());
        parameterList.add(oneToOneMessage.getFile());
        parameterList.add(oneToOneMessage.getStatus());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }
    
    @Override
    public void update(One_To_One_Messages oneToOneMessage) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`one_to_one_messages` "
                + "SET `Timestamp` = ?, `Message_Content_Or_File_Name` = ?, "
                + "`File` = ?, `Status` = ? "
                + "WHERE (`Sender_Phone_Number` = ?) and (`Receiver_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(oneToOneMessage.getTimestamp());
        parameterList.add(oneToOneMessage.getMessageContentOrFileName());
        parameterList.add(oneToOneMessage.getFile());
        parameterList.add(oneToOneMessage.getStatus());
        parameterList.add(oneToOneMessage.getSenderPhoneNumber());
        parameterList.add(oneToOneMessage.getReceiverPhoneNumber());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`one_to_one_messages` "
                + "WHERE (`Sender_Phone_Number` = ?)"
                + " and (`Receiver_Phone_Number` = ?)"
                + " and (`Timestamp` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }
    
    @Override
    public One_To_One_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String getByPrimaryKeyQuery = "SELECT `one_to_one_messages`.`Sender_Phone_Number`,"
                + " `one_to_one_messages`.`Receiver_Phone_Number`, "
                + "`one_to_one_messages`.`Timestamp`, "
                + "`one_to_one_messages`.`Message_Content_Or_File_Name`, "
                + "`one_to_one_messages`.`File`, `one_to_one_messages`.`Status` "
                + "FROM `chattingapp`.`one_to_one_messages` "
                + "WHERE (`Sender_Phone_Number` = ?) and (`Receiver_Phone_Number` = ?) and (`Timestamp` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByPrimaryKeyQuery, parameterList);
        queryResult.beforeFirst();
        One_To_One_Messages oneToOneMessage = new One_To_One_Messages();
        if(queryResult.next())
        {
            oneToOneMessage.setSenderPhoneNumber(queryResult.getString(1));
            oneToOneMessage.setReceiverPhoneNumber(queryResult.getString(2));
            oneToOneMessage.setTimestamp(queryResult.getTimestamp(3));
            oneToOneMessage.setMessageContentOrFileName(queryResult.getString(4));
            oneToOneMessage.setFile(queryResult.getBytes(5));
            oneToOneMessage.setStatus(queryResult.getByte(6));
        }
        return oneToOneMessage;
    }
    
    @Override
    public List<One_To_One_Messages> getAll() throws SQLException {
        String getAllQuery = "SELECT `one_to_one_messages`.`Sender_Phone_Number`,"
                + " `one_to_one_messages`.`Receiver_Phone_Number`, "
                + "`one_to_one_messages`.`Timestamp`, "
                + "`one_to_one_messages`.`Message_Content_Or_File_Name`, "
                + "`one_to_one_messages`.`File`, `one_to_one_messages`.`Status` "
                + "FROM `chattingapp`.`one_to_one_messages`;";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<One_To_One_Messages> oneToOneMessagesList = new ArrayList<>();
        
        while(queryResult.next())
        {
            One_To_One_Messages oneToOneMessage = new One_To_One_Messages();
            oneToOneMessage.setSenderPhoneNumber(queryResult.getString(1));
            oneToOneMessage.setReceiverPhoneNumber(queryResult.getString(2));
            oneToOneMessage.setTimestamp(queryResult.getTimestamp(3));
            oneToOneMessage.setMessageContentOrFileName(queryResult.getString(4));
            oneToOneMessage.setFile(queryResult.getBytes(5));
            oneToOneMessage.setStatus(queryResult.getByte(6));
            
            oneToOneMessagesList.add(oneToOneMessage);
        }
        
        
        return oneToOneMessagesList;
    }

    @Override
    public List<One_To_One_Messages> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `one_to_one_messages`.`Sender_Phone_Number`,"
                + " `one_to_one_messages`.`Receiver_Phone_Number`, "
                + "`one_to_one_messages`.`Timestamp`, "
                + "`one_to_one_messages`.`Message_Content_Or_File_Name`, "
                + "`one_to_one_messages`.`File`, `one_to_one_messages`.`Status` "
                + "FROM `chattingapp`.`one_to_one_messages`; "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<One_To_One_Messages> oneToOneMessagesList = new ArrayList<>();
        
        while(queryResult.next())
        {
            One_To_One_Messages oneToOneMessage = new One_To_One_Messages();
            oneToOneMessage.setSenderPhoneNumber(queryResult.getString(1));
            oneToOneMessage.setReceiverPhoneNumber(queryResult.getString(2));
            oneToOneMessage.setTimestamp(queryResult.getTimestamp(3));
            oneToOneMessage.setMessageContentOrFileName(queryResult.getString(4));
            oneToOneMessage.setFile(queryResult.getBytes(5));
            oneToOneMessage.setStatus(queryResult.getByte(6));
            
            oneToOneMessagesList.add(oneToOneMessage);
        }
        
        
        return oneToOneMessagesList;
    }
    
}
