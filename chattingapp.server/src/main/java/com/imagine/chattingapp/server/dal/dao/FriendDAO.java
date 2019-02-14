/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.Admin;
import com.imagine.chattingapp.server.dal.entity.Friend;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class FriendDAO implements DAO<Friend> {
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public FriendDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Friend friend) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`friend` (`Phone_Number1`, `Phone_Number2`,"
                + " `Relative_Group`, `Block_Flag`, `Last_Message_Sent_Time`, "
                + "`Font_Family`, `Font_Size`, `Font_Color`, `Bold_Flag`, "
                + "`Underline_Flag`, `Italic_Flag`, `Text_Background`) VALUES"
                + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(friend.getPhoneNumber1());
        parameterList.add(friend.getPhoneNumber2());
        parameterList.add(friend.getRealtiveGroup());
        parameterList.add(friend.getBlockFlag());
        parameterList.add(friend.getLastMessageSentTime());
        parameterList.add(friend.getFontFamliy());
        parameterList.add(friend.getFontSize());
        parameterList.add(friend.getFontColor());
        parameterList.add(friend.getBoldFlag());
        parameterList.add(friend.getUnderlineFlag());
        parameterList.add(friend.getItalicFlag());
        parameterList.add(friend.getTextBackground());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }
    
    @Override
    public void update(Friend friend) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`friend` "
                + "SET `Relative_Group` = ?, `Block_Flag` = ?, "
                + "`Last_Message_Sent_Time` = ?, "
                + "`Font_Family` = ?, `Font_Size` = ?, `Font_Color` = ?,"
                + " `Bold_Flag` = ?, `Underline_Flag` = ?, `Italic_Flag` = ?, "
                + "`Text_Background` = ? "
                + "WHERE (`Phone_Number1` = ?) and (`Phone_Number2` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(friend.getRealtiveGroup());
        parameterList.add(friend.getBlockFlag());
        parameterList.add(friend.getLastMessageSentTime());
        parameterList.add(friend.getFontFamliy());
        parameterList.add(friend.getFontSize());
        parameterList.add(friend.getFontColor());
        parameterList.add(friend.getBoldFlag());
        parameterList.add(friend.getUnderlineFlag());
        parameterList.add(friend.getItalicFlag());
        parameterList.add(friend.getTextBackground());
        
        parameterList.add(friend.getPhoneNumber1());
        parameterList.add(friend.getPhoneNumber2());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`friend` "
                + "WHERE (`Phone_Number1` = ?) and (`Phone_Number2` = ?)";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }
    
    @Override
    public Friend getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String getByPrimaryKeyQuery = "SELECT `friend`.`Phone_Number1`,`friend`.`Phone_Number2`,"
                + "`friend`.`Relative_Group`,`friend`.`Block_Flag`,"
                + "`friend`.`Last_Message_Sent_Time`,`friend`.`Font_Family`,"
                + "`friend`.`Font_Size`,`friend`.`Font_Color`,`friend`.`Bold_Flag`,"
                + "`friend`.`Underline_Flag`,`friend`.`Italic_Flag`,"
                + "`friend`.`Text_Background` FROM `chattingapp`.`friend` "
                + "WHERE (`friend`.`Phone_Number1` = ?) and (`friend`.`Phone_Number2` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByPrimaryKeyQuery, parameterList);
        queryResult.beforeFirst();
        Friend friend = new Friend();
        if(queryResult.next())
        {
            friend.setPhoneNumber1(queryResult.getString(1));
            friend.setPhoneNumber2(queryResult.getString(2));
            friend.setRealtiveGroup(queryResult.getString(3));
            friend.setBlockFlag(queryResult.getInt(4) == 0 ? false : true);
            friend.setLastMessageSentTime(queryResult.getTimestamp(5));
            friend.setFontFamliy(queryResult.getString(6));
            friend.setFontSize(queryResult.getShort(7));
            friend.setFontColor(queryResult.getInt(8));
            friend.setBoldFlag(queryResult.getInt(9) == 0 ? false : true);
            friend.setUnderlineFlag(queryResult.getInt(10) == 0 ? false : true);
            friend.setItalicFlag(queryResult.getInt(11) == 0 ? false : true);
            friend.setTextBackground(queryResult.getBytes(12));
        }
        return friend;
    }
    
    @Override
    public List<Friend> getAll() throws SQLException {
        String getAllQuery = "SELECT `friend`.`Phone_Number1`,`friend`.`Phone_Number2`,"
                + "`friend`.`Relative_Group`,`friend`.`Block_Flag`,"
                + "`friend`.`Last_Message_Sent_Time`,`friend`.`Font_Family`,"
                + "`friend`.`Font_Size`,`friend`.`Font_Color`,`friend`.`Bold_Flag`,"
                + "`friend`.`Underline_Flag`,`friend`.`Italic_Flag`,"
                + "`friend`.`Text_Background` FROM `chattingapp`.`friend` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Friend> friendList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Friend friend = new Friend();
            friend.setPhoneNumber1(queryResult.getString(1));
            friend.setPhoneNumber2(queryResult.getString(2));
            friend.setRealtiveGroup(queryResult.getString(3));
            friend.setBlockFlag(queryResult.getInt(4) == 0 ? false : true);
            friend.setLastMessageSentTime(queryResult.getTimestamp(5));
            friend.setFontFamliy(queryResult.getString(6));
            friend.setFontSize(queryResult.getShort(7));
            friend.setFontColor(queryResult.getInt(8));
            friend.setBoldFlag(queryResult.getInt(9) == 0 ? false : true);
            friend.setUnderlineFlag(queryResult.getInt(10) == 0 ? false : true);
            friend.setItalicFlag(queryResult.getInt(11) == 0 ? false : true);
            friend.setTextBackground(queryResult.getBytes(12));
            friendList.add(friend);
        }
        
        
        return friendList;
    }

    @Override
    public List<Friend> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `friend`.`Phone_Number1`,`friend`.`Phone_Number2`,"
                + "`friend`.`Relative_Group`,`friend`.`Block_Flag`,"
                + "`friend`.`Last_Message_Sent_Time`,`friend`.`Font_Family`,"
                + "`friend`.`Font_Size`,`friend`.`Font_Color`,`friend`.`Bold_Flag`,"
                + "`friend`.`Underline_Flag`,`friend`.`Italic_Flag`,"
                + "`friend`.`Text_Background` FROM `chattingapp`.`friend`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Friend> friendList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Friend friend = new Friend();
            friend.setPhoneNumber1(queryResult.getString(1));
            friend.setPhoneNumber2(queryResult.getString(2));
            friend.setRealtiveGroup(queryResult.getString(3));
            friend.setBlockFlag(queryResult.getInt(4) == 0 ? false : true);
            friend.setLastMessageSentTime(queryResult.getTimestamp(5));
            friend.setFontFamliy(queryResult.getString(6));
            friend.setFontSize(queryResult.getShort(7));
            friend.setFontColor(queryResult.getInt(8));
            friend.setBoldFlag(queryResult.getInt(9) == 0 ? false : true);
            friend.setUnderlineFlag(queryResult.getInt(10) == 0 ? false : true);
            friend.setItalicFlag(queryResult.getInt(11) == 0 ? false : true);
            friend.setTextBackground(queryResult.getBytes(12));
            friendList.add(friend);
        }
        
        
        return friendList;
    }
}
