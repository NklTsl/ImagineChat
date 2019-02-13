/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.dal;

import com.imagine.chattingapp.client.control.ChatController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Mahmoud Shereif
 */
public class ClientServiceImpl extends UnicastRemoteObject implements ClientService{
    private ChatController chatController;
    public ClientServiceImpl(ChatController chatController) throws RemoteException{
        this.chatController = chatController;
    }
    public void receive(String message){
        chatController.receiveMessage(message);
    }
}
