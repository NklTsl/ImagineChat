/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mahmoud Shereif
 */
public interface ChatService extends Remote {
    public void sendMessage(Message message) throws RemoteException;
}
