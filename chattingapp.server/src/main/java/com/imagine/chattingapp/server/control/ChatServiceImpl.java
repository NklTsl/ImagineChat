/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.ChatService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Mahmoud Shereif
 */
public class ChatServiceImpl extends UnicastRemoteObject implements ChatService{

    public ChatServiceImpl() throws RemoteException{
        
    }

    
    @Override
    public void sendMessage(User user) throws RemoteException{
        System.out.println("com.imagine.chattingapp.server.control.ChatServiceImpl.sendMessage()");
    }
    
}
