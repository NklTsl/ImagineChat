package com.imagine.chattingapp.server.dal.dao;


import com.imagine.chattingapp.common.entity.One_To_One_Messages;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.OneToOneMessagesId;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class One_To_One_MessagesDAO implements DAO<One_To_One_Messages> {
    
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public One_To_One_MessagesDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.OneToOneMessages mapToOneMessageAnno(One_To_One_Messages oneMessage){
        com.imagine.chattingapp.server.dal.entity.OneToOneMessages oneMessageAnno = new com.imagine.chattingapp.server.dal.entity.OneToOneMessages();
        oneMessageAnno.setFile(oneMessage.getFile());
        String sender = oneMessage.getSenderPhoneNumber();
        String receiver = oneMessage.getReceiverPhoneNumber();
        OneToOneMessagesId Id = new OneToOneMessagesId(sender, receiver);
        oneMessageAnno.setId(Id);
        oneMessageAnno.setStatus(oneMessage.getStatus());
        oneMessageAnno.setTimestamp(oneMessage.getTimestamp());
        return oneMessageAnno;
    }
    public One_To_One_Messages mapToOneMessage(com.imagine.chattingapp.server.dal.entity.OneToOneMessages oneMessageAnno){
        One_To_One_Messages oneMessage = new One_To_One_Messages();
        oneMessage.setFile(oneMessageAnno.getFile());
        oneMessage.setSenderPhoneNumber(oneMessageAnno.getId().getSenderPhoneNumber());
        oneMessage.setReceiverPhoneNumber(oneMessageAnno.getId().getReceiverPhoneNumber());
        oneMessage.setStatus(oneMessageAnno.getStatus());
        oneMessage.setTimestamp(new Timestamp(oneMessageAnno.getTimestamp().getTime()));
        return oneMessage;
    }
    @Override
    public void persist(One_To_One_Messages oneToOneMessage) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToOneMessageAnno(oneToOneMessage));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void update(One_To_One_Messages oneToOneMessage) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToOneMessageAnno(oneToOneMessage));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String sender = (String) primaryKey.get(0);
        String receiver = (String) primaryKey.get(1);
        OneToOneMessagesId Id = new OneToOneMessagesId(sender, receiver);
        com.imagine.chattingapp.server.dal.entity.OneToOneMessages oneToOneMessage = MainController.session.get(com.imagine.chattingapp.server.dal.entity.OneToOneMessages.class, Id);
        MainController.session.beginTransaction();
        MainController.session.delete(oneToOneMessage);
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public One_To_One_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String sender = (String) primaryKeys.get(0);
        String receiver = (String) primaryKeys.get(1);
        OneToOneMessagesId Id = new OneToOneMessagesId(sender, receiver);
        com.imagine.chattingapp.server.dal.entity.OneToOneMessages oneToOneMessage = MainController.session.get(com.imagine.chattingapp.server.dal.entity.OneToOneMessages.class, Id);
        return mapToOneMessage(oneToOneMessage);
    }
    @Override
    public List<One_To_One_Messages> getAll() throws SQLException {
        Query oneMessageQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.OneToOneMessages");
        List<com.imagine.chattingapp.server.dal.entity.OneToOneMessages> allOneMessagesAnno = oneMessageQuery.list();
        List<One_To_One_Messages> oneMessages = new ArrayList<>();
        allOneMessagesAnno.forEach((oneMessageAnno)->{
            oneMessages.add(mapToOneMessage(oneMessageAnno));
        });
        return oneMessages;
    }

    @Override
    public List<One_To_One_Messages> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria oneMessageCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.OneToOneMessages.class);
        for(int i = 0;i<columnNames.size();i++){
           oneMessageCriteria = oneMessageCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        List<com.imagine.chattingapp.server.dal.entity.OneToOneMessages> allOneMessagesAnno = oneMessageCriteria.list();
        List<One_To_One_Messages> oneMessages = new ArrayList<>();
        allOneMessagesAnno.forEach((oneMessageAnno)->{
            oneMessages.add(mapToOneMessage(oneMessageAnno));
        });
        return oneMessages;
    }
    
}
