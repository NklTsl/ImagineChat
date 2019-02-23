/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Hamada Abdrabou
 */
public interface ClientSendFileServiceP2P extends Remote{
    public ReceiveFileService getReceiveFileService(String receiverPhone, String senderName, String fileName)throws RemoteException;
}
