/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import java.rmi.RemoteException;

/**
 *
 * @author Mahmoud Shereif
 */
public class ClientServiceImpl implements ClientService{

    @Override
    public void receive(String Message) throws RemoteException {
        System.out.println("received");
    }
    
    
}
