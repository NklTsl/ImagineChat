/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Message_Status;
import com.imagine.chattingapp.server.control.MainController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class Message_StatusDAO implements DAO<Message_Status> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public Message_StatusDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    public com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus mapToMessageStatusAnno(Message_Status messageStatus){
        com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus messageStatusAnno = new com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus();
        messageStatusAnno.setDescription(messageStatus.getDescription());
        messageStatusAnno.setId(messageStatus.getId());
        return messageStatusAnno;
    }
    public Message_Status mapToMessageStatus(com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus messageStatusAnno){
        Message_Status messageStatus = new Message_Status();
        messageStatus.setId(messageStatusAnno.getId());
        messageStatus.setDescription(messageStatus.getDescription());
        return messageStatus;
    }
    
    @Override
    public void persist(Message_Status messageStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToMessageStatusAnno(messageStatus));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void update(Message_Status messageStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToMessageStatusAnno(messageStatus));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        byte key = (byte)primaryKey.get(0);
        MainController.session.beginTransaction();
        MainController.session.delete(key);
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public Message_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
       byte key = (byte) primaryKeys.get(0);
       com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus messageStatusAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus.class, key);
       return mapToMessageStatus(messageStatusAnno);
    }
    
    @Override
    public List<Message_Status> getAll() throws SQLException {
        Query messageStatusQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus");
        List<com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus> allMessageStatuses = messageStatusQuery.list();
        List<Message_Status> messageStatuses = new ArrayList<>();
        allMessageStatuses.forEach((messageStatus)->{
            messageStatuses.add(mapToMessageStatus(messageStatus));
        });
        return messageStatuses;
    }

    @Override
    public List<Message_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
       Criteria messageStatusCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus.class);
       for(int i = 0;i<columnNames.size();i++){
           messageStatusCriteria = messageStatusCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
       }
        List<com.imagine.chattingapp.server.dal.entity.OneToOneMessageStatus> allMessageStatuses = messageStatusCriteria.list();
        List<Message_Status> messageStatuses = new ArrayList<>();
        allMessageStatuses.forEach((messageStatus)->{
            messageStatuses.add(mapToMessageStatus(messageStatus));
        });
        return messageStatuses;
       
    }
    
}
