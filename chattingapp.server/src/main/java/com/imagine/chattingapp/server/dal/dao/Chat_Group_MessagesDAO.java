/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group_Messages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group_MessagesDAO implements DAO<Chat_Group_Messages> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public Chat_Group_MessagesDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Chat_Group_Messages chatGroupMessage) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`chat_group_messages` "
                + "(`Group_ID`, `Sender_Phone_Number`, `Timestamp`, "
                + "`Message_Content_Or_File_Name`,`File`, `Status`) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroupMessage.getGroupID());
        parameterList.add(chatGroupMessage.getSenderPhoneNumber());
        parameterList.add(chatGroupMessage.getTimestamp());
        parameterList.add(chatGroupMessage.getMessageContentOrFileName());
        parameterList.add(chatGroupMessage.getFile());
        parameterList.add(chatGroupMessage.getStatus());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }
    
    @Override
    public void update(Chat_Group_Messages chatGroupMessage) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`chat_group_messages` "
                + "SET `Message_Content_Or_File_Name` = ?, "
                + "`File` = ?, `Status` = ? "
                + "WHERE (`Group_ID` = ?) and (`Sender_Phone_Number` = ?) "
                + "and (`Timestamp` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroupMessage.getMessageContentOrFileName());
        parameterList.add(chatGroupMessage.getFile());
        parameterList.add(chatGroupMessage.getStatus());
        parameterList.add(chatGroupMessage.getGroupID());
        parameterList.add(chatGroupMessage.getSenderPhoneNumber());
        parameterList.add(chatGroupMessage.getTimestamp());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`chat_group_messages` "
                + "WHERE (`Group_ID` = ?) and (`Sender_Phone_Number` = ?) "
                + "and (`Timestamp` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }
    
    @Override
    public Chat_Group_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String getByPrimaryKeyQuery = "SELECT `chat_group_messages`.`Group_ID`, "
                + "`chat_group_messages`.`Sender_Phone_Number`, "
                + "`chat_group_messages`.`Timestamp`, "
                + "`chat_group_messages`.`Message_Content_Or_File_Name`, "
                + "`chat_group_messages`.`File`, `chat_group_messages`.`Status` "
                + "FROM `chattingapp`.`chat_group_messages` "
                + "WHERE (`Group_ID` = ?) and (`Sender_Phone_Number` = ?) "
                + "and (`Timestamp` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByPrimaryKeyQuery, parameterList);
        queryResult.beforeFirst();
        Chat_Group_Messages chatGroupMessage = new Chat_Group_Messages();
        if(queryResult.next())
        {
            chatGroupMessage.setGroupID(queryResult.getInt(1));
            chatGroupMessage.setSenderPhoneNumber(queryResult.getString(2));
            chatGroupMessage.setTimestamp(queryResult.getTimestamp(3));
            chatGroupMessage.setMessageContentOrFileName(queryResult.getString(4));
            chatGroupMessage.setFile(queryResult.getBytes(5));
            chatGroupMessage.setStatus(queryResult.getByte(6));
        }
        return chatGroupMessage;
    }
    
    @Override
    public List<Chat_Group_Messages> getAll() throws SQLException {
        String getAllQuery = "SELECT `chat_group_messages`.`Group_ID`, "
                + "`chat_group_messages`.`Sender_Phone_Number`, "
                + "`chat_group_messages`.`Timestamp`, "
                + "`chat_group_messages`.`Message_Content_Or_File_Name`, "
                + "`chat_group_messages`.`File`, `chat_group_messages`.`Status` "
                + "FROM `chattingapp`.`chat_group_messages`;";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Chat_Group_Messages> chatGroupMessagesList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Chat_Group_Messages chatGroupMessage = new Chat_Group_Messages();
            chatGroupMessage.setGroupID(queryResult.getInt(1));
            chatGroupMessage.setSenderPhoneNumber(queryResult.getString(2));
            chatGroupMessage.setTimestamp(queryResult.getTimestamp(3));
            chatGroupMessage.setMessageContentOrFileName(queryResult.getString(4));
            chatGroupMessage.setFile(queryResult.getBytes(5));
            chatGroupMessage.setStatus(queryResult.getByte(6));
            
            chatGroupMessagesList.add(chatGroupMessage);
        }
        
        
        return chatGroupMessagesList;
    }
    
    @Override
    public List<Chat_Group_Messages> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `chat_group_messages`.`Group_ID`, "
                + "`chat_group_messages`.`Sender_Phone_Number`, "
                + "`chat_group_messages`.`Timestamp`, "
                + "`chat_group_messages`.`Message_Content_Or_File_Name`, "
                + "`chat_group_messages`.`File`, `chat_group_messages`.`Status` "
                + "FROM `chattingapp`.`chat_group_messages` "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Chat_Group_Messages> chatGroupMessagesList = new ArrayList<Chat_Group_Messages>();
        if(queryResult.next())
        {
            Chat_Group_Messages chatGroupMessage = new Chat_Group_Messages();
            chatGroupMessage.setGroupID(queryResult.getInt(1));
            chatGroupMessage.setSenderPhoneNumber(queryResult.getString(2));
            chatGroupMessage.setTimestamp(queryResult.getTimestamp(3));
            chatGroupMessage.setMessageContentOrFileName(queryResult.getString(4));
            chatGroupMessage.setFile(queryResult.getBytes(5));
            chatGroupMessage.setStatus(queryResult.getByte(6));
            
            chatGroupMessagesList.add(chatGroupMessage);
        }
        
        return chatGroupMessagesList;
    }
    
}
