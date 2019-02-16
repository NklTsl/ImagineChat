/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group_Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group_UsersDAO implements DAO<Chat_Group_Users> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Chat_Group_UsersDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    

    @Override
    public void persist(Chat_Group_Users chatGroupUsers) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`chat_group_users` "
                + "(`Group_ID`, `User_Phone_Number`, `Font_Family`, `Font_Size`, "
                + "`Font_Color`, `Bold_Flag`, `Underline_Flag`, `Italic_Flag`,"
                + "`Text_Background` ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroupUsers.getGroupId());
        parameterList.add(chatGroupUsers.getUserPhoneNumber());
        parameterList.add(chatGroupUsers.getFontFamliy());
        parameterList.add(chatGroupUsers.getFontSize());
        parameterList.add(chatGroupUsers.getFontColor());
        parameterList.add(chatGroupUsers.getBoldFlag());
        parameterList.add(chatGroupUsers.getUnderlineFlag());
        parameterList.add(chatGroupUsers.getItalicFlag());
        parameterList.add(chatGroupUsers.getTextBackground());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(Chat_Group_Users chatGroupUsers) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`chat_group_users` "
                + "SET `Font_Family` = ?,"
                + " `Font_Size` = ?, `Font_Color` = ?, `Bold_Flag` = ?,"
                + " `Underline_Flag` = ?, `Italic_Flag` = ?, `Text_Background` = ?"
                + " WHERE (`Group_ID` = ?) and (`User_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroupUsers.getFontFamliy());
        parameterList.add(chatGroupUsers.getFontSize());
        parameterList.add(chatGroupUsers.getFontColor());
        parameterList.add(chatGroupUsers.getBoldFlag());
        parameterList.add(chatGroupUsers.getUnderlineFlag());
        parameterList.add(chatGroupUsers.getItalicFlag());
        parameterList.add(chatGroupUsers.getTextBackground());
        parameterList.add(chatGroupUsers.getGroupId());
        parameterList.add(chatGroupUsers.getUserPhoneNumber());
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);    
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`chat_group_users` "
                + "WHERE (`Group_ID` = ?) and (`User_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public Chat_Group_Users getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `chat_group_users`.`Group_ID`, `chat_group_users`.`User_Phone_Number`,"
                + " `chat_group_users`.`Font_Family`, `chat_group_users`.`Font_Size`,"
                + " `chat_group_users`.`Font_Color`, `chat_group_users`.`Bold_Flag`,"
                + " `chat_group_users`.`Underline_Flag`, `chat_group_users`.`Italic_Flag`,"
                + " `chat_group_users`.`Text_Background` FROM `chattingapp`.`chat_group_users`"
                + " WHERE (`Group_ID` = ?) and (`User_Phone_Number` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Chat_Group_Users chatGroupUsers = new Chat_Group_Users();
        if(queryResult.next())
        {
            chatGroupUsers.setGroupId(queryResult.getInt(1));
            chatGroupUsers.setUserPhoneNumber(queryResult.getString(2));
            chatGroupUsers.setFontFamliy(queryResult.getString(3));
            chatGroupUsers.setFontSize(queryResult.getShort(4));
            chatGroupUsers.setFontColor(queryResult.getInt(5));
            chatGroupUsers.setBoldFlag(queryResult.getInt(6) == 0 ? false : true);
            chatGroupUsers.setUnderlineFlag(queryResult.getInt(7) == 0 ? false : true);
            chatGroupUsers.setItalicFlag(queryResult.getInt(8) == 0 ? false : true);
            chatGroupUsers.setTextBackground(queryResult.getBytes(9));
        }
        return chatGroupUsers;
    }

    @Override
    public List<Chat_Group_Users> getAll() throws SQLException {
        String getAllQuery = "SELECT `chat_group_users`.`Group_ID`, `chat_group_users`.`User_Phone_Number`,"
                + " `chat_group_users`.`Font_Family`, `chat_group_users`.`Font_Size`,"
                + " `chat_group_users`.`Font_Color`, `chat_group_users`.`Bold_Flag`,"
                + " `chat_group_users`.`Underline_Flag`, `chat_group_users`.`Italic_Flag`,"
                + " `chat_group_users`.`Text_Background` FROM `chattingapp`.`chat_group_users`;";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Chat_Group_Users> chatGroupUsersList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Chat_Group_Users chatGroupUsers = new Chat_Group_Users();
            chatGroupUsers.setGroupId(queryResult.getInt(1));
            chatGroupUsers.setUserPhoneNumber(queryResult.getString(2));
            chatGroupUsers.setFontFamliy(queryResult.getString(3));
            chatGroupUsers.setFontSize(queryResult.getShort(4));
            chatGroupUsers.setFontColor(queryResult.getInt(5));
            chatGroupUsers.setBoldFlag(queryResult.getInt(6) == 0 ? false : true);
            chatGroupUsers.setUnderlineFlag(queryResult.getInt(7) == 0 ? false : true);
            chatGroupUsers.setItalicFlag(queryResult.getInt(8) == 0 ? false : true);
            chatGroupUsers.setTextBackground(queryResult.getBytes(9));
            chatGroupUsersList.add(chatGroupUsers);
        }
        
        
        return chatGroupUsersList;
    }

    @Override
    public List<Chat_Group_Users> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `chat_group_users`.`Group_ID`, `chat_group_users`.`User_Phone_Number`,"
                + " `chat_group_users`.`Font_Family`, `chat_group_users`.`Font_Size`,"
                + " `chat_group_users`.`Font_Color`, `chat_group_users`.`Bold_Flag`,"
                + " `chat_group_users`.`Underline_Flag`, `chat_group_users`.`Italic_Flag`,"
                + " `chat_group_users`.`Text_Background` FROM `chattingapp`.`chat_group_users` "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Chat_Group_Users> chatGroupUsersList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Chat_Group_Users chatGroupUsers = new Chat_Group_Users();
            chatGroupUsers.setGroupId(queryResult.getInt(1));
            chatGroupUsers.setUserPhoneNumber(queryResult.getString(2));
            chatGroupUsers.setFontFamliy(queryResult.getString(3));
            chatGroupUsers.setFontSize(queryResult.getShort(4));
            chatGroupUsers.setFontColor(queryResult.getInt(5));
            chatGroupUsers.setBoldFlag(queryResult.getInt(6) == 0 ? false : true);
            chatGroupUsers.setUnderlineFlag(queryResult.getInt(7) == 0 ? false : true);
            chatGroupUsers.setItalicFlag(queryResult.getInt(8) == 0 ? false : true);
            chatGroupUsers.setTextBackground(queryResult.getBytes(9));
            chatGroupUsersList.add(chatGroupUsers);
        }
        
        
        return chatGroupUsersList;
    }
    
    public List<String> getPhonesByGroupId(String groupId) throws SQLException {
        String getPhonesByGroupIdQuery = "SELECT User_Phone_Number "
                + "FROM chattingapp.chat_group_users "
                + "where Group_ID = ?";
       
        List<Object> parameters = new ArrayList<>();
        parameters.add(groupId);
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getPhonesByGroupIdQuery, parameters);
        queryResult.beforeFirst();
        List<String> chatGroupUsersPhones = new ArrayList<>();
        
        while(queryResult.next())
        {
            String chatGroupUserPhone = queryResult.getString(1);
            chatGroupUsersPhones.add(chatGroupUserPhone);
        }
        
        
        return chatGroupUsersPhones;
    }
    
}
