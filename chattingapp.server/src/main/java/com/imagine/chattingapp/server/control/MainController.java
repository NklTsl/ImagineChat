/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class MainController {
    private static Map<String, ClientService> onlineClients = new TreeMap<>();
    public static void main(String[] args)
    {
        try {
            
            LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
            ContactsServiceImpl contactsServiceImpl = new ContactsServiceImpl();
            ChatServiceImpl chatServiceImpl = new ChatServiceImpl();
            Registry registery = LocateRegistry.createRegistry(2000);
            registery.rebind("LoginService", loginServiceImpl);
            registery.rebind("ContactsService", contactsServiceImpl);
            registery.rebind("ChatService", chatServiceImpl);
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static  void registerClient(String phoneNumber, ClientService clientService)
    {
        MainController.onlineClients.put(phoneNumber, clientService);
    }
    
    public static void unregisterClient(String phoneNumber)
    {
        MainController.onlineClients.remove(phoneNumber);
    }
    public static ClientService getClientService(String phoneNumber)
    {
        return onlineClients.get(phoneNumber);
    }
}
