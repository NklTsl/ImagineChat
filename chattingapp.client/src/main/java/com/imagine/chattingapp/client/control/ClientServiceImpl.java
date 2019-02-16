/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.customobj.FriendContact;
import com.imagine.chattingapp.common.customobj.Message;
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
}
