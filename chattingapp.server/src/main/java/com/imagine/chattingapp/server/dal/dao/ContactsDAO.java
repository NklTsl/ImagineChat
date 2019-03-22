/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.GroupContact;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.ChatGroup;
import com.imagine.chattingapp.server.dal.entity.ChatGroupUsers;
import com.imagine.chattingapp.server.dal.entity.Friend;
import com.imagine.chattingapp.server.dal.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mahmoud Shereif
 */
public class ContactsDAO {

    DatabaseDataRetreival databaseDataRetreival = null;

    public ContactsDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }

    public List<Contact> getFriendContacts(String phoneNumber) throws SQLException {
        Criteria criteria = MainController.session.createCriteria(Friend.class, "f")
                .add(Restrictions.eq("f.userByPhoneNumber1.phoneNumber", phoneNumber));

        List result = criteria.list();
        List<Contact> contactsList = new ArrayList<>();
        for (Object friendTemp : result) {
            Friend friend = (Friend) friendTemp;
            FriendContact friendContact = new FriendContact();
            friendContact.setPhoneNumber(friend.getUserByPhoneNumber2().getPhoneNumber());
            friendContact.setName(friend.getUserByPhoneNumber2().getName());
            friendContact.setImage(friend.getUserByPhoneNumber2().getPicture());
            friendContact.setRelativeGroup(friend.getRelativeGroup());

            if (MainController.getClientService(friendContact.getPhoneNumber()) != null) {
                friendContact.setStatus(friend.getUserByPhoneNumber2().getUserStatus().getId());
            } else {
                friendContact.setStatus(null);
            }

            friendContact.setStatusDescription(friend.getUserByPhoneNumber2().getUserStatus().getDescription());
            Date lastMessageSentTime = friend.getLastMessageSentTime();
            if (lastMessageSentTime != null) {
                friendContact.setLastSentMessageTime(lastMessageSentTime.getTime());
            } else {
                friendContact.setLastSentMessageTime(null);
            }

            contactsList.add(friendContact);
        }

        return contactsList;
    }

    public List<Contact> getGroupContacts(String phoneNumber) throws SQLException {

        Criteria criteria = MainController.session.createCriteria(ChatGroupUsers.class);
        criteria = criteria.add(Restrictions.eq("id.userPhoneNumber", phoneNumber));
        
        List<ChatGroupUsers> chatGroupUserses = criteria.list();

        List<Contact> contactsList = new ArrayList<>();

        for (ChatGroupUsers chatGroupUsers : chatGroupUserses) {
            GroupContact groupContact = new GroupContact();
            groupContact.setGroupId(chatGroupUsers.getChatGroup().getId());
            groupContact.setName(chatGroupUsers.getChatGroup().getName());
            groupContact.setImage(chatGroupUsers.getChatGroup().getPicture());
            Date lastMessageSentTime = chatGroupUsers.getChatGroup().getLastMessageSentTime();

            if (lastMessageSentTime != null) {
                groupContact.setLastSentMessageTime(lastMessageSentTime.getTime());
            } else {
                groupContact.setLastSentMessageTime(null);
            }

            contactsList.add(groupContact);

        }

        return contactsList;
    }

}
