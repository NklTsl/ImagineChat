/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import com.imagine.chattingapp.common.serverservices.ClientSendFileServiceP2P;

/**
 *
 * @author Hamada Abdrabou
 */
public class ClientSendFileServiceP2PImpl extends UnicastRemoteObject implements ClientSendFileServiceP2P{
    public ClientSendFileServiceP2PImpl() throws RemoteException{
        
    }

    @Override
    public ReceiveFileService getReceiveFileService(String receiverPhone, String senderName, String fileName) throws RemoteException {
        ClientService clientService = MainController.getClientService(receiverPhone);
        return clientService.requestReceiveFileService(senderName, fileName);
    }
    
    
}
