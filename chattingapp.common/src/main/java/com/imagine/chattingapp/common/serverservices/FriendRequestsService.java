/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.dto.FriendRequestNotification;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public interface FriendRequestsService extends Remote{
    public List<FriendRequestNotification> getFriendRequests(String phoneNumber) throws RemoteException, SQLException;
    public void acceptRequest(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException, SQLException;
    public void rejectRequest(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException, SQLException;
}
