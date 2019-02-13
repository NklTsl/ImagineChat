/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.dal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mahmoud Shereif
 */
public interface ServerService extends Remote{
    static Map<String, ClientService> clientsMap = new Hashtable<>();
    void oneToOneMessage(String message, String id) throws RemoteException;
    //TODO login and contains automatic registration
    void login(ClientService clientService, String phoneNumber, String password) throws RemoteException;
    //TODO logout and contains automatic unregistration
    void logout(ClientService clientService, String phoneNumber) throws RemoteException;
    Map<String, ClientService> getOpennedClients(ClientService currentClient) throws RemoteException;
}
