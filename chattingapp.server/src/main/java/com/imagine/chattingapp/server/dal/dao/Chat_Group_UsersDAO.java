/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group_Users;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.ChatGroup;
import com.imagine.chattingapp.server.dal.entity.ChatGroupUsers;
import com.imagine.chattingapp.server.dal.entity.ChatGroupUsersId;
import com.imagine.chattingapp.server.dal.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group_UsersDAO implements DAO<Chat_Group_Users> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Chat_Group_UsersDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    com.imagine.chattingapp.server.dal.entity.ChatGroupUsers mapToAnnoChatGroupUser(Chat_Group_Users chatGroupUser)
    {
        com.imagine.chattingapp.server.dal.entity.ChatGroupUsers chatGroupUserAnno = new com.imagine.chattingapp.server.dal.entity.ChatGroupUsers();
        ChatGroupUsersId chatGroupUsersId = new ChatGroupUsersId(chatGroupUser.getUserPhoneNumber(), chatGroupUser.getGroupId());
        chatGroupUserAnno.setId(chatGroupUsersId);
        chatGroupUserAnno.setChatGroup(MainController.session.get(ChatGroup.class, chatGroupUser.getGroupId()));
        chatGroupUserAnno.setUser(MainController.session.get(User.class, chatGroupUser.getUserPhoneNumber()));
        return chatGroupUserAnno;
    }
    
    public Chat_Group_Users mapToGroupUsers(com.imagine.chattingapp.server.dal.entity.ChatGroupUsers chatGroupUsersAnno){
        Chat_Group_Users chatGroupUsers = new Chat_Group_Users();
        chatGroupUsers.setUserPhoneNumber(chatGroupUsersAnno.getUser().getPhoneNumber());
        //get Other Fields
        return chatGroupUsers;
    }
    
    @Override
    public void persist(Chat_Group_Users chatGroupUsers) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoChatGroupUser(chatGroupUsers));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void update(Chat_Group_Users chatGroupUsers) throws SQLException {        
        com.imagine.chattingapp.server.dal.entity.ChatGroupUsers  chatGroupAnno = mapToAnnoChatGroupUser(chatGroupUsers);
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(chatGroupAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        ChatGroupUsersId key = (ChatGroupUsersId) primaryKey;
        MainController.session.beginTransaction();
        com.imagine.chattingapp.server.dal.entity.ChatGroupUsers deletedChatGroup = MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroupUsers.class,key);
        MainController.session.delete(deletedChatGroup);
        MainController.session.getTransaction().commit();
    }

    @Override
    public Chat_Group_Users getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        Chat_Group_Users chatGroupUsers;
        ChatGroupUsersId key = (ChatGroupUsersId) primaryKeys;
        chatGroupUsers = mapToGroupUsers(MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroupUsers.class, key));
        return chatGroupUsers;
    }

    @Override
    public List<Chat_Group_Users> getAll() throws SQLException {
        Query groupUsersQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.ChatGroupUsers");
        List<com.imagine.chattingapp.server.dal.entity.ChatGroupUsers> allAnnoGroupUsers = groupUsersQuery.list();
        List<Chat_Group_Users> chatGroupUsers = new ArrayList<>();
        allAnnoGroupUsers.forEach((annoGroupUser)->{
            chatGroupUsers.add(mapToGroupUsers(annoGroupUser));
        });
    return chatGroupUsers;
    }
    

    @Override
    public List<Chat_Group_Users> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria groupUsersCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.ChatGroup.class);
        for(int i=0;i<columnNames.size();i++){
            groupUsersCriteria = groupUsersCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        List<com.imagine.chattingapp.server.dal.entity.ChatGroupUsers> allAnnoGroupUsers = groupUsersCriteria.list();
        List<Chat_Group_Users> returnedGroups = new ArrayList<>();
        allAnnoGroupUsers.forEach((grp)->{
            returnedGroups.add(mapToGroupUsers(grp));
        });
        return returnedGroups;
    }
    
    /*public List<String> getPhonesByGroupId(String groupId) throws SQLException {
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
    }*/
    
    public List<String> getPhonesByGroupId(String groupId) throws SQLException {
        int Id = Integer.parseInt(groupId);
        com.imagine.chattingapp.server.dal.entity.ChatGroup chatGroup = MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroup.class, Id);
        Set<ChatGroupUsers> chatGroupUsers = chatGroup.getChatGroupUserses();
        List<String> chatGroupUsersPhones = new ArrayList<>();
        chatGroupUsers.forEach((chatGroupUser)->{
            chatGroupUsersPhones.add(chatGroupUser.getId().getUserPhoneNumber());
        });
        return chatGroupUsersPhones;
    }
    
}
