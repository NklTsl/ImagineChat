/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.dal;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mahmoud Shereif
 */
public interface ClientService extends Remote {
    void receive(String message) throws RemoteException;
}
