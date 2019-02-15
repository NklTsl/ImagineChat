/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_GroupDAO implements DAO<Chat_Group> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Chat_GroupDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Chat_Group chatGroup) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`chat_group` "
                + "(`ID`, `Owner_Phone_Number`, `Name`, `Last_Message_Sent_Time`) "
                + "VALUES (?, ?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroup.getId());
        parameterList.add(chatGroup.getOwnerPhoneNumber());
        parameterList.add(chatGroup.getName());
        parameterList.add(chatGroup.getLastMessageSentTime());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(Chat_Group chatGroup) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`chat_group` SET `Owner_Phone_Number` = ?,"
                + " `Name` = ?, `Last_Message_Sent_Time` = ? "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroup.getOwnerPhoneNumber());
        parameterList.add(chatGroup.getName());
        parameterList.add(chatGroup.getLastMessageSentTime());
        parameterList.add(chatGroup.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);     
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`chat_group` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public Chat_Group getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `chat_group`.`ID`, `chat_group`.`Owner_Phone_Number`, "
                + "`chat_group`.`Name`, `chat_group`.`Last_Message_Sent_Time` "
                + "FROM `chattingapp`.`chat_group` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Chat_Group chatGroup = new Chat_Group();
        if(queryResult.next())
        {
            chatGroup.setId(queryResult.getInt(1));
            chatGroup.setOwnerPhoneNumber(queryResult.getString(2));
            chatGroup.setName(queryResult.getString(3));
            chatGroup.setLastMessageSentTime(queryResult.getTimestamp(4));
        }
        return chatGroup;
    }

    @Override
    public List<Chat_Group> getAll() throws SQLException {
        String getAllQuery = "SELECT `chat_group`.`ID`, `chat_group`.`Owner_Phone_Number`, "
                + "`chat_group`.`Name`, `chat_group`.`Last_Message_Sent_Time` "
                + "FROM `chattingapp`.`chat_group` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Chat_Group> chatGroupList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Chat_Group chatGroup = new Chat_Group();
            chatGroup.setId(queryResult.getInt(1));
            chatGroup.setName(queryResult.getString(2));
            chatGroup.setName(queryResult.getString(3));
            chatGroup.setLastMessageSentTime(queryResult.getTimestamp(4));
            
            chatGroupList.add(chatGroup);
        }
        
        
        return chatGroupList;
    }

    @Override
    public List<Chat_Group> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `chat_group`.`ID`, `chat_group`.`Owner_Phone_Number`, "
                + "`chat_group`.`Name`, `chat_group`.`Last_Message_Sent_Time` "
                + "FROM `chattingapp`.`chat_group` "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Chat_Group> chatGroupList = new ArrayList<Chat_Group>();
        if(queryResult.next())
        {
            Chat_Group chatGroup = new Chat_Group();
            chatGroup.setId(queryResult.getInt(1));
            chatGroup.setOwnerPhoneNumber(queryResult.getString(2));
            chatGroup.setName(queryResult.getString(3));
            chatGroup.setLastMessageSentTime(queryResult.getTimestamp(4));
            chatGroupList.add(chatGroup);
        }
        
        return chatGroupList;
    }
    
}
