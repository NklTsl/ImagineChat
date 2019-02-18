/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.clientservices;

import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.dto.Notification;
import com.imagine.chattingapp.common.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mahmoud Shereif
 */
public interface ClientService extends Remote{
    public void receive(Message message) throws RemoteException;
    public void notify(Notification notification) throws RemoteException;
}
