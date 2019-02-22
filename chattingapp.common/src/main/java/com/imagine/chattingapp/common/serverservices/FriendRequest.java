/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.dto.FriendRequestEligibility;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public interface FriendRequest extends Remote{
    
    public void sendFriendRequests(String senderPhoneNumber,List<String> usersToBeSentRequestsTo)throws RemoteException;

    public FriendRequestEligibility checkFriendRequestEligibility(String senderphoneNumber,String receiverPhoneNumber) throws RemoteException;
}
