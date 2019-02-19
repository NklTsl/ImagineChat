
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.common.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Menna Helmy
 */
public interface AddNewContact extends Remote{
    
    
    public boolean checkIsOnSys(User requested) throws RemoteException;
    public boolean checkIsFriend(User userMain,User requested) throws RemoteException;
    public boolean checkIsPending(User userMain,User requested) throws RemoteException;
    public boolean checkIsBlocked(User userMain,User requested) throws RemoteException;
    public void acceptPending(User current,User requested) throws RemoteException;

    public void addNewContact(List<Friend_Request> usersToBeSentRequestsTo) throws RemoteException;

    
   
    
}
