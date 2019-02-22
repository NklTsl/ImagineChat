/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.client.view.ChatController;
import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import com.imagine.chattingapp.common.dto.ContactNotification;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.dto.Notification;
import com.imagine.chattingapp.common.entity.One_To_One_Messages;
import com.imagine.chattingapp.common.entity.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Mahmoud Shereif
 */
public class ClientServiceImpl extends UnicastRemoteObject implements ClientService{

    private MainController mainController;
    public ClientServiceImpl(MainController mainController) throws RemoteException{
        this.mainController = mainController;
    }

    
    @Override
    public void receive(Message message) throws RemoteException {
       mainController.getChatController().receive(message);
    }

    @Override
    public void notify(Notification notification) {
        mainController.getChatController().handleNotification(notification);
    }

    @Override
    public ReceiveFileService requestReceiveFileService(String senderName) throws RemoteException {
        return mainController.getChatController().showReceiveFileRequest(senderName);
    }
}
