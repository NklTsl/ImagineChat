package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.dto.FriendRequestEligibility;
import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.FriendRequest;
import com.imagine.chattingapp.server.dal.dao.FriendDAO;
import com.imagine.chattingapp.server.dal.dao.Friend_RequestDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendRequestImpl extends UnicastRemoteObject implements FriendRequest {

    UserDAO userDao;
    FriendDAO friendDao;
    Friend_RequestDAO friendRequestDao;

    public FriendRequestImpl() throws RemoteException {

        try {
            this.userDao = new UserDAO();
            this.friendDao = new FriendDAO();
            this.friendRequestDao = new Friend_RequestDAO();
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendFriendRequests(String senderPhoneNumber, List<String> usersToBeSentRequestsTo) throws RemoteException {
        usersToBeSentRequestsTo.forEach((request) -> {
            try {
                friendRequestDao.persist(FriendRequestObjectPreparation(senderPhoneNumber, request));
            } catch (SQLException ex) {
                Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public FriendRequestEligibility checkFriendRequestEligibility(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException {
        FriendRequestEligibility requestEligibility = new FriendRequestEligibility();
        requestEligibility.setPhoneNumber(null);
        requestEligibility.setName(null);
        if (checkIsOnSystem(receiverPhoneNumber)) {

            if (checkIsFriend(senderPhoneNumber, receiverPhoneNumber)) {

                requestEligibility.setEligibility("friend");

            } else if (checkIsBlocked(senderPhoneNumber, receiverPhoneNumber)) {
                requestEligibility.setEligibility("blocked");
            }else if(checkIsPending(senderPhoneNumber,receiverPhoneNumber)){
            
                requestEligibility.setEligibility("pending");
            }else{
                
                User receiverDetails = friendRequestedDetails(receiverPhoneNumber);
                if(receiverDetails!=null){
                    requestEligibility.setPhoneNumber(receiverDetails.getPhoneNumber());
                    requestEligibility.setName(receiverDetails.getName());
                    requestEligibility.setEligibility(null);
                }
            
            }

        }else {
        
            requestEligibility.setEligibility("notUser");
        }
        return requestEligibility;
    }

    private boolean checkIsOnSystem(String phoneNumberToCheck) {

        boolean isOnSys = false;
        try {

            List<Object> requestPhone = new ArrayList<>();
            requestPhone.add(phoneNumberToCheck);
            User user = userDao.getByPrimaryKey(requestPhone);
            if (user == null) {
                isOnSys = false;
            } else {
                isOnSys = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isOnSys;

    }

    private boolean checkIsFriend(String senderPhoneNumber, String receiverPhoneNumber) {
        boolean isFriend = false;
        try {

            List<Object> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(senderPhoneNumber);
            phoneNumbers.add(receiverPhoneNumber);
            Friend friend = friendDao.getByPrimaryKey(phoneNumbers);
            if (friend == null) {
                isFriend = false;
            } else {
                isFriend = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isFriend;

    }

    private boolean checkIsBlocked(String senderPhoneNumber, String receiverPhoneNumber) {
        boolean isBlocked = false;
        try {

            List<String> columnNames = new ArrayList<>();
            List<Object> columnValues = new ArrayList<>();
            columnNames.add("Phone_Number1");
            columnNames.add("Phone_Number2");
            columnNames.add("Block_Flag");
            columnValues.add(senderPhoneNumber);
            columnValues.add(receiverPhoneNumber);
            columnValues.add(true);
            List<Friend> results = friendDao.getByColumnNames(columnNames, columnValues);
            if (results.isEmpty()) {
                isBlocked = false;
            } else {
                isBlocked = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isBlocked;

    }

    private boolean checkIsPending(String senderPhoneNumber, String receiverPhoneNumber) {
        boolean isPending = false;
        
        try {
            
            List<Object> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(receiverPhoneNumber);
            phoneNumbers.add(senderPhoneNumber);
            Friend_Request request = friendRequestDao.getByPrimaryKey(phoneNumbers);
            if(request == null){
                isPending = false;
            }
            else{
                isPending= true;
                acceptPending(senderPhoneNumber, receiverPhoneNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isPending;
    }
    
    private User friendRequestedDetails(String receiverPhoneNumber){
        User receiver = null;
        try {
            List<User> userRequested = new ArrayList<>();
            List<String> columnNames = new ArrayList<>();
            List<Object> columnValues = new ArrayList<>();
            columnNames.add("Phone_Number");
            columnValues.add(receiverPhoneNumber);
            userRequested = userDao.getByColumnNames(columnNames, columnValues);
            if(!userRequested.isEmpty()){
            
                receiver  = new User();
                receiver = userRequested.get(0);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receiver;
    }
    
    private Friend_Request FriendRequestObjectPreparation(String sender,String receiver){
        Friend_Request friendRequest = new Friend_Request();
        friendRequest.setSenderPhoneNumber(sender);
        friendRequest.setReceiverPhoneNumber(receiver);
        int status = 1;
        friendRequest.setStatusID((byte)status);
        return friendRequest;
    
    }
    
    private void acceptPending(String senderPhoneNumber,String receiverPhoneNumber){
    
        
            addFriendRelation(senderPhoneNumber, receiverPhoneNumber);
            addFriendRelation(receiverPhoneNumber, senderPhoneNumber);
            removePendingRequest(senderPhoneNumber, receiverPhoneNumber);
         
    }
    
    private void removePendingRequest(String sender,String receiver){
    
        try {
            List<Object> phonesToDelete = new ArrayList<>();
            phonesToDelete.add(receiver);
            phonesToDelete.add(sender);
            friendRequestDao.delete(phonesToDelete);
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void addFriendRelation(String sender,String receiver){
        try {
            Friend friendCurrent = new Friend();
            friendCurrent.setPhoneNumber1(sender);
            friendCurrent.setPhoneNumber2(receiver);
            friendCurrent.setBlockFlag(false);
            friendCurrent.setRealtiveGroup("other");
            friendDao.persist(friendCurrent);
            
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
