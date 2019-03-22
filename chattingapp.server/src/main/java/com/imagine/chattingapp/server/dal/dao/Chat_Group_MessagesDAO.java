package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Chat_Group_Messages;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.ChatGroupMessagesId;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group_MessagesDAO implements DAO<Chat_Group_Messages> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public Chat_Group_MessagesDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.ChatGroupMessages mapToGroupMessagesAnno(Chat_Group_Messages groupMessages){
        com.imagine.chattingapp.server.dal.entity.ChatGroupMessages groupMessagesAnno = new com.imagine.chattingapp.server.dal.entity.ChatGroupMessages();
        groupMessagesAnno.setFile(groupMessages.getFile());
        int groupId = groupMessages.getGroupID();
        String sender = groupMessages.getSenderPhoneNumber();
        Date sendDate = new Date(groupMessages.getTimestamp().getTime());
        ChatGroupMessagesId Id = new ChatGroupMessagesId(groupId, sender, sendDate);
        groupMessagesAnno.setId(Id);
        groupMessagesAnno.setStatus(groupMessages.getStatus());
        groupMessagesAnno.setMessageContentOrFileName(groupMessages.getMessageContentOrFileName());
        return groupMessagesAnno;
    }
    Chat_Group_Messages mapToGroupMessages(com.imagine.chattingapp.server.dal.entity.ChatGroupMessages groupMessagesAnno){
        Chat_Group_Messages groupMessages = new Chat_Group_Messages();
        groupMessages.setFile(groupMessagesAnno.getFile());
        groupMessages.setGroupID(groupMessagesAnno.getId().getGroupId());
        groupMessages.setSenderPhoneNumber(groupMessagesAnno.getId().getSenderPhoneNumber());
        groupMessages.setTimestamp(new Timestamp(groupMessagesAnno.getId().getTimestamp().getTime()));
        groupMessages.setStatus(groupMessagesAnno.getStatus());
        groupMessages.setMessageContentOrFileName(groupMessagesAnno.getMessageContentOrFileName());
        return groupMessages;
    }
    @Override
    public void persist(Chat_Group_Messages chatGroupMessage) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToGroupMessagesAnno(chatGroupMessage));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void update(Chat_Group_Messages chatGroupMessage) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToGroupMessagesAnno(chatGroupMessage));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        int groupId = (Integer)primaryKey.get(0);
        String sender = (String)primaryKey.get(1);
        Date sendDate = (Date)primaryKey.get(2);
        ChatGroupMessagesId Id = new ChatGroupMessagesId(groupId, sender, sendDate);
        com.imagine.chattingapp.server.dal.entity.ChatGroupMessages groupMessagesAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroupMessages.class, Id);
        MainController.session.beginTransaction();
        MainController.session.delete(Id);
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public Chat_Group_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        int groupId = (Integer)primaryKeys.get(0);
        String sender = (String)primaryKeys.get(1);
        Date sendDate = (Date)primaryKeys.get(2);
        ChatGroupMessagesId Id = new ChatGroupMessagesId(groupId, sender, sendDate);
        com.imagine.chattingapp.server.dal.entity.ChatGroupMessages groupMessagesAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.ChatGroupMessages.class, Id);
        return mapToGroupMessages(groupMessagesAnno);
    }
    
    @Override
    public List<Chat_Group_Messages> getAll() throws SQLException {
        Query groupMessagesQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.ChatGroupMessages");
        List<com.imagine.chattingapp.server.dal.entity.ChatGroupMessages> allGroupMessagesAnno = groupMessagesQuery.list();
        List<Chat_Group_Messages> groupMessages = new ArrayList<>();
        allGroupMessagesAnno.forEach((groupMessageAnno)->{
            groupMessages.add(mapToGroupMessages(groupMessageAnno));
        });
        return groupMessages;
    }
    
    @Override
    public List<Chat_Group_Messages> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria groupMessagesCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.ChatGroupMessages.class);
        for(int i = 0; i<columnNames.size();i++){
            groupMessagesCriteria = groupMessagesCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        List<com.imagine.chattingapp.server.dal.entity.ChatGroupMessages> allGroupMessagesAnno = groupMessagesCriteria.list();
        List<Chat_Group_Messages> groupMessages = new ArrayList<>();
        allGroupMessagesAnno.forEach((groupMessageAnno)->{
            groupMessages.add(mapToGroupMessages(groupMessageAnno));
        });
        return groupMessages;
    }
    
}
