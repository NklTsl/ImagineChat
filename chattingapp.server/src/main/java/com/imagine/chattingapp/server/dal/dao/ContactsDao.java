/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.customobj.Contact;
import com.imagine.chattingapp.common.customobj.FriendContact;
import com.imagine.chattingapp.common.customobj.GroupContact;
import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.server.control.MainController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class ContactsDao {
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public ContactsDao() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    public List<Contact> getFriendContacts(String phoneNumber) throws SQLException
    {
        String getFriendContactsQuery = "select Phone_Number2, F.Name, F.Picture, Relative_Group,"
                + " F.Status_ID, Description, Last_Message_Sent_Time from user U join friend "
                + "on (U.Phone_Number = Phone_Number1)  join user F "
                + "on(Phone_Number2 = F.Phone_Number) join user_status "
                + "on (F.status_ID = ID) WHERE U.Phone_Number = ?";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(phoneNumber);
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getFriendContactsQuery, parameterList);
        queryResult.beforeFirst();
        List<Contact> contactsList = new ArrayList<>();
        
        while(queryResult.next())
        {
            FriendContact friendContact = new FriendContact();
            friendContact.setPhoneNumber(queryResult.getString(1));
            friendContact.setName(queryResult.getString(2));
            friendContact.setImage(queryResult.getBytes(3));
            friendContact.setRelativeGroup(queryResult.getString(4));
            
            if(MainController.getClientService(friendContact.getPhoneNumber()) != null){
                friendContact.setStatus(queryResult.getByte(5));
            }
            else
            {
                friendContact.setStatus(null);
            }
            
            friendContact.setStatusDescription(queryResult.getString(6));
            Timestamp lastMessageSentTime = queryResult.getTimestamp(7);
            if(lastMessageSentTime != null)
            {
                friendContact.setLastSentMessageTime(lastMessageSentTime.getTime());
            }
            else
            {
                friendContact.setLastSentMessageTime(null);
            }
                  
            contactsList.add(friendContact);
            
        }
        
        
        return contactsList;
    }
    
    public List<Contact> getGroupContacts(String phoneNumber) throws SQLException
    {
        String getGroupContactsQuery = "SELECT ID, Name, Picture, "
                + "Last_Message_Sent_Time FROM chattingapp.chat_group "
                + "join chat_group_users on(ID = Group_ID) "
                + "where User_Phone_Number = ?";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(phoneNumber);
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getGroupContactsQuery, parameterList);
        queryResult.beforeFirst();
        List<Contact> contactsList = new ArrayList<>();
        
        while(queryResult.next())
        {
            GroupContact groupContact = new GroupContact();
            groupContact.setGroupId(queryResult.getInt(1));
            groupContact.setName(queryResult.getString(2));
            groupContact.setImage(queryResult.getBytes(3));
            Timestamp lastMessageSentTime = queryResult.getTimestamp(4);
            
            if(lastMessageSentTime != null)
            {
                groupContact.setLastSentMessageTime(lastMessageSentTime.getTime());
            }
            else
            {
                groupContact.setLastSentMessageTime(null);
            }
            
            
            contactsList.add(groupContact);
            
        }
        
        
        return contactsList;
    }
}
