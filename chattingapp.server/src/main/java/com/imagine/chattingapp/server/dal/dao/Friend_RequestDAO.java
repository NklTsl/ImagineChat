package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.FriendRequestId;
import com.imagine.chattingapp.server.dal.entity.RequestStatus;
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
public class Friend_RequestDAO implements DAO<Friend_Request> {

    DatabaseDataRetreival databaseDataRetreival = null;

    public Friend_RequestDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.FriendRequest mapToFriendRequestAnno(Friend_Request friendRequest){
        com.imagine.chattingapp.server.dal.entity.FriendRequest friendRequestAnno = new com.imagine.chattingapp.server.dal.entity.FriendRequest();
        String senderPhone = friendRequest.getSenderPhoneNumber();
        String receiverPhone = friendRequest.getReceiverPhoneNumber();
        FriendRequestId Id = new FriendRequestId(senderPhone, receiverPhone);
        friendRequestAnno.setId(Id);
        friendRequestAnno.setSeen(friendRequest.getSeen()?(byte)1:(byte)0);
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setId(friendRequest.getStatusID());
        friendRequestAnno.setRequestStatus(requestStatus);
        return friendRequestAnno;
    }   
    Friend_Request mapToFriendRequest(com.imagine.chattingapp.server.dal.entity.FriendRequest friendRequestAnno){
        Friend_Request friendRequest = new Friend_Request();
        friendRequest.setSeen(friendRequestAnno.getSeen() != 0);
        friendRequest.setStatusID(friendRequestAnno.getRequestStatus().getId());
        FriendRequestId Id = friendRequestAnno.getId();
        friendRequest.setSenderPhoneNumber(Id.getSenderPhoneNumber());
        friendRequest.setReceiverPhoneNumber(Id.getReceiverPhoneNumber());
        return friendRequest;
    }
    @Override
    public void persist(Friend_Request friendRequest) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToFriendRequestAnno(friendRequest));
        MainController.session.getTransaction().commit();
       }

    @Override
    public void update(Friend_Request friendRequest) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToFriendRequestAnno(friendRequest));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String senderPhone = (String) primaryKey.get(0);
        String receiverPhone = (String) primaryKey.get(1);
        FriendRequestId key = new FriendRequestId(senderPhone, receiverPhone);
        com.imagine.chattingapp.server.dal.entity.FriendRequest friendRequestAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.FriendRequest.class, key);
        MainController.session.beginTransaction();
        MainController.session.delete(friendRequestAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public Friend_Request getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String senderPhone = (String) primaryKeys.get(0);
        String receiverPhone = (String) primaryKeys.get(1);
        FriendRequestId key = new FriendRequestId(senderPhone, receiverPhone);
        com.imagine.chattingapp.server.dal.entity.FriendRequest friendRequestAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.FriendRequest.class, key);
        return mapToFriendRequest(friendRequestAnno);
    }
        

    @Override
    public List<Friend_Request> getAll() throws SQLException {
        Query friendRequestQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.FriendRequest");
        List<com.imagine.chattingapp.server.dal.entity.FriendRequest> allFriendRequestsAnno = friendRequestQuery.list();
        List<Friend_Request> friendRequests = new ArrayList<>();
        allFriendRequestsAnno.forEach((friendRequestAnno)->{
            friendRequests.add(mapToFriendRequest(friendRequestAnno));
        });
        return friendRequests;
    }

    @Override
    public List<Friend_Request> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria friendRequestCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.FriendRequest.class);
        for(int i = 0; i<columnNames.size();i++)
           friendRequestCriteria = friendRequestCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        List<com.imagine.chattingapp.server.dal.entity.FriendRequest> allFriendRequestsAnno = friendRequestCriteria.list();
        List<Friend_Request> friendRequests = new ArrayList<>();
        allFriendRequestsAnno.forEach((friendRequestAnno)->{
            friendRequests.add(mapToFriendRequest(friendRequestAnno));
        });
        return friendRequests;
    }
    
}
