
package com.imagine.chattingapp.common.clientservices;

import com.imagine.chattingapp.common.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ClientService extends Remote{
    public void receive(String Message) throws RemoteException;
    public void receiveUserDetails(User user) throws RemoteException;
    
}
