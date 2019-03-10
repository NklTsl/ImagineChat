/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.clientservices;

import com.healthmarketscience.rmiio.RemoteInputStream;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Hamada Abdrabou
 */
public interface ReceiveFileService extends Remote{
    public void sendFile(RemoteInputStream ristream, String exe, String file, double fileLength) throws RemoteException, IOException;
    
}
