
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.AddNewContact;
import com.imagine.chattingapp.server.dal.dao.FriendDAO;
import com.imagine.chattingapp.server.dal.dao.Friend_RequestDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menna Helmy
 */
public class AddNewContactImpl extends UnicastRemoteObject implements AddNewContact{
    
    UserDAO userDao;
    FriendDAO friendDao;
    Friend_RequestDAO friend_requestDao;
    public AddNewContactImpl()throws RemoteException{
        try {
            this.userDao = new UserDAO();
            this.friendDao = new FriendDAO();
            this.friend_requestDao = new Friend_RequestDAO();
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    

    @Override
    public boolean checkIsOnSys(User requested) throws RemoteException {
        boolean isOnSys = false;
        try {
            
            List<Object> requestPhone = new ArrayList<>();
            requestPhone.add(requested.getPhoneNumber());
            User user = userDao.getByPrimaryKey(requestPhone);
            if(user == null){
                isOnSys = false;
            }
            else
            { isOnSys= true;}
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isOnSys;
    }

    @Override
    public boolean checkIsFriend(User userMain, User requested) throws RemoteException {
        boolean isFriend= false;
        try {
            
            List<Object> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(userMain.getPhoneNumber());
            phoneNumbers.add(requested.getPhoneNumber());
            Friend friend = friendDao.getByPrimaryKey(phoneNumbers);
            if(friend == null){
                isFriend = false;
            }
            else{
                isFriend= true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isFriend;
        
        
    } 

    @Override
    public boolean checkIsPending(User userMain, User requested) throws RemoteException {
        boolean isPending = false;
        
        try {
            
            List<Object> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(requested.getPhoneNumber());
            phoneNumbers.add(userMain.getPhoneNumber());
            Friend_Request request = friend_requestDao.getByPrimaryKey(phoneNumbers);
            if(request == null){
                isPending = false;
            }
            else
                isPending= true;
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isPending;
        
    }

    @Override
    public boolean checkIsBlocked(User userMain, User requested) throws RemoteException {
        
        boolean isBlocked= false;
        try {
            
            List<String> columnNames = new ArrayList<>();
            List<Object> columnValues = new ArrayList<>();
            columnNames.add("Phone_Number1");
            columnNames.add("Phone_Number2");
            columnNames.add("Block_Flag");
            columnValues.add(userMain.getPhoneNumber());
            columnValues.add(requested.getPhoneNumber());
            columnValues.add(true);
            List<Friend> results = friendDao.getByColumnNames(columnNames, columnValues);
            if(results.isEmpty()){
                isBlocked = false;
            }
            else
                isBlocked = true;
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isBlocked;
        
    }

    @Override
    public void acceptPending(User current, User requested) throws RemoteException {
        try {
            Friend friendCurrent = new Friend();
            friendCurrent.setPhoneNumber1(current.getPhoneNumber());
            friendCurrent.setPhoneNumber2(requested.getPhoneNumber());
            friendCurrent.setBlockFlag(false);
            friendDao.persist(friendCurrent);
            List<Object> phonesToDelete = new ArrayList<>();
            phonesToDelete.add(requested.getPhoneNumber());
            phonesToDelete.add(current.getPhoneNumber());
            friend_requestDao.delete(phonesToDelete);
            Friend friendRequested = new Friend();
            friendRequested.setPhoneNumber1(requested.getPhoneNumber());
            friendRequested.setPhoneNumber2(current.getPhoneNumber());
            friendRequested.setBlockFlag(false);
            friendDao.persist(friendRequested);
        } catch (SQLException ex) {
            Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addNewContact(List<Friend_Request> usersToBeSentRequestsTo) throws RemoteException {
        usersToBeSentRequestsTo.forEach((request) -> {
            try {
                friend_requestDao.persist(request);
            } catch (SQLException ex) {
                Logger.getLogger(AddNewContactImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }
    
}
