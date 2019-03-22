/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group;
import com.imagine.chattingapp.server.control.AddNewGroupServiceImpl;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.ChatGroup;
import com.imagine.chattingapp.server.dal.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_GroupDAO implements DAO<Chat_Group> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Chat_GroupDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    com.imagine.chattingapp.server.dal.entity.ChatGroup mapToAnnoChatGroup(Chat_Group chatGroup)
    {
        com.imagine.chattingapp.server.dal.entity.ChatGroup chatGroupAnno = new com.imagine.chattingapp.server.dal.entity.ChatGroup();
        chatGroupAnno.setName(chatGroup.getName());
        chatGroupAnno.setPicture(chatGroup.getPicture());
        chatGroupAnno.setUser(MainController.session.get(User.class, chatGroup.getOwnerPhoneNumber()));
        return chatGroupAnno;
    }
    
    public Chat_Group mapToGroup(com.imagine.chattingapp.server.dal.entity.ChatGroup chatGroupAnno){
        Chat_Group chatGroup = new Chat_Group();
        chatGroup.setName(chatGroupAnno.getName());
        chatGroup.setPicture(chatGroupAnno.getPicture());
        chatGroup.setLastMessageSentTime(new Timestamp(chatGroupAnno.getLastMessageSentTime().getTime()));
        chatGroup.setOwnerPhoneNumber(chatGroupAnno.getUser().getPhoneNumber());
        return chatGroup;
    }
    
    
    @Override
    public void persist(Chat_Group chatGroup) throws SQLException {
        MainController.session.beginTransaction();
        AddNewGroupServiceImpl.chatGroup = (ChatGroup)MainController.session.merge(mapToAnnoChatGroup(chatGroup));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void update(Chat_Group chatGroup) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`chat_group` SET `Owner_Phone_Number` = ?,"
                + " `Name` = ?, `Picture` = ?,`Last_Message_Sent_Time` = ? "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(chatGroup.getOwnerPhoneNumber());
        parameterList.add(chatGroup.getName());
        parameterList.add(chatGroup.getPicture());
        parameterList.add(chatGroup.getLastMessageSentTime());
        parameterList.add(chatGroup.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);     
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.delete(MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroup.class,(Integer)primaryKey.get(0)));
        MainController.session.getTransaction().commit();
    }

    @Override
    public Chat_Group getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        int key = (Integer)primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.ChatGroup groupAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroup.class, key);
        Chat_Group group = mapToGroup(groupAnno);
        return group;          
    }

    @Override
    public List<Chat_Group> getAll() throws SQLException {
        Query groupsQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.ChatGroup");
        List<com.imagine.chattingapp.server.dal.entity.ChatGroup> allAnnoGroups = groupsQuery.list();
        List<Chat_Group> returnedGroups = new ArrayList();
        allAnnoGroups.forEach((groupAnno)->{
            returnedGroups.add(mapToGroup(groupAnno));
        });
        return returnedGroups;
    }

    @Override
    public List<Chat_Group> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Conjunction restrictions = Restrictions.conjunction();
        for(int i=0;i<columnNames.size();i++){
            restrictions.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        Criteria groupCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.ChatGroup.class);
        groupCriteria = groupCriteria.add(restrictions);
        List<com.imagine.chattingapp.server.dal.entity.ChatGroup> allAnnoGroups = groupCriteria.list();
        List<Chat_Group> returnedGroups = new ArrayList<>();
        allAnnoGroups.forEach((grp)->{
            returnedGroups.add(mapToGroup(grp));
        });
        return returnedGroups;
    }
    
    public int getLastInsertedGroupId() throws SQLException {
        String getByColumnNamesQuery = "SELECT LAST_INSERT_ID()" 
                + "FROM `chattingapp`.`chat_group` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, new ArrayList<>());
        queryResult.beforeFirst();
        List<Chat_Group> chatGroupList = new ArrayList<Chat_Group>();
        
        int lastInsertedId = -1;
        
        if(queryResult.next())
        {
            lastInsertedId = queryResult.getInt(1);
        }
        
        return lastInsertedId;
    }
    
}
