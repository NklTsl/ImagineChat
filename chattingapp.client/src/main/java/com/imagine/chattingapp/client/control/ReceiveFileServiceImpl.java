/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.imagine.chattingapp.client.view.ChatController;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Hamada Abdrabou
 */
public class ReceiveFileServiceImpl extends UnicastRemoteObject implements ReceiveFileService{
    ChatController chatController;
    public ReceiveFileServiceImpl(MainController mainController) throws RemoteException{
        this.chatController = mainController.getChatController();
    }
    
    @Override
    public void sendFile(RemoteInputStream remoteInputStream, String ext, String fileName) throws RemoteException, IOException {
        chatController.receiveFile(remoteInputStream, ext, fileName);
    }
}
