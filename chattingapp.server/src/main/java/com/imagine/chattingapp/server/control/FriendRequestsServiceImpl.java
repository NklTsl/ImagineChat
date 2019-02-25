/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.dto.FriendRequestNotification;
import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.FriendRequest;
import com.imagine.chattingapp.common.serverservices.FriendRequestsService;
import com.imagine.chattingapp.server.dal.dao.Friend_RequestDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class FriendRequestsServiceImpl extends UnicastRemoteObject implements FriendRequestsService{

    public FriendRequestsServiceImpl() throws RemoteException {
        
    }

    @Override
    public List<FriendRequestNotification> getFriendRequests(String phoneNumber) throws RemoteException, SQLException {
        Friend_RequestDAO friend_RequestDAO = new Friend_RequestDAO();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Receiver_Phone_Number");
        List<Object> columnValues = new ArrayList<>();
        columnValues.add(phoneNumber);
        
        List<Friend_Request> friend_Requests = friend_RequestDAO.getByColumnNames(columnNames, columnValues);
        
        List<FriendRequestNotification> friendRequestNotifications = new ArrayList<>();
        
        for(Friend_Request request : friend_Requests)
        {
            friendRequestNotifications.add(mapToFriendRequestNotification(request));
        }
        
        return friendRequestNotifications;
        
    }

    @Override
    public void acceptRequest(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException, SQLException {
        Friend_RequestDAO friend_RequestDAO = new Friend_RequestDAO();
        List<Object> primaryKeys = new ArrayList<>();
        primaryKeys.add(senderPhoneNumber);
        primaryKeys.add(receiverPhoneNumber);
                
        Friend_Request friend_Request = friend_RequestDAO.getByPrimaryKey(primaryKeys);
        friend_Request.setStatusID((byte)1);
        
        friend_RequestDAO.update(friend_Request);
    }

    @Override
    public void rejectRequest(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException, SQLException {
        Friend_RequestDAO friend_RequestDAO = new Friend_RequestDAO();
        List<Object> primaryKeys = new ArrayList<>();
        primaryKeys.add(senderPhoneNumber);
        primaryKeys.add(receiverPhoneNumber);
                
        Friend_Request friend_Request = friend_RequestDAO.getByPrimaryKey(primaryKeys);
        friend_Request.setStatusID((byte)2);
        
        friend_RequestDAO.update(friend_Request);
    }
    
    private FriendRequestNotification mapToFriendRequestNotification(Friend_Request friend_Request) throws SQLException{
        User user = new User();
        UserDAO userDAO = new UserDAO();
        List<Object> primaryKeys = new ArrayList<>();
        primaryKeys.add(friend_Request.getSenderPhoneNumber());
        user = userDAO.getByPrimaryKey(primaryKeys);
        
        
        FriendRequestNotification requestNotification = new FriendRequestNotification();
        requestNotification.setPhoneNumber(friend_Request.getSenderPhoneNumber());
        requestNotification.setName(user.getName());
        requestNotification.setImage(user.getPicture());
        requestNotification.setStatusId(friend_Request.getStatusID());
        requestNotification.setSeen(friend_Request.getSeen());
        
        return requestNotification;
    }
}
