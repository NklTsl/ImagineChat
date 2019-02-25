/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.ContactNotification;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.server.dal.dao.ContactsDAO;
import com.imagine.chattingapp.server.dal.dao.FriendDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            RegisterServiceImpl registerServiceImpl = new RegisterServiceImpl();
            AddNewGroupServiceImpl addNewGroupServiceImpl = new AddNewGroupServiceImpl();
            FriendRequestImpl sendFriendRequest = new FriendRequestImpl();
            ClientSendFileServiceP2PImpl clientSendFileServiceP2PImpl = new ClientSendFileServiceP2PImpl();
            GetNameByPhoneServiceImpl getNameByPhoneServiceImpl = new GetNameByPhoneServiceImpl();
            
            
            Registry registery = LocateRegistry.createRegistry(2000);
            registery.rebind("LoginLogoutService", loginServiceImpl);
            registery.rebind("ContactsService", contactsServiceImpl);
            registery.rebind("ChatService", chatServiceImpl);
            registery.rebind("RegisterService", registerServiceImpl);
            registery.rebind("AddNewGroupService", addNewGroupServiceImpl);
            registery.rebind("AddContactService", sendFriendRequest);
            registery.rebind("SendFileService", clientSendFileServiceP2PImpl);
            registery.rebind("GetNameByPhoneService", getNameByPhoneServiceImpl);
            
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static  void registerClient(String phoneNumber, ClientService clientService)
    {
        try {
            MainController.onlineClients.put(phoneNumber, clientService);
            
            ContactNotification contactNotification = new ContactNotification();
            UserDAO userDAO = new UserDAO();
            List<Object> primaryKeys = new ArrayList<>();
            primaryKeys.add(phoneNumber);
            
            User user = userDAO.getByPrimaryKey(primaryKeys);
            
            contactNotification.setContactPhoneNumber(phoneNumber);
            contactNotification.setNewStatus("Online");
            contactNotification.setStatusId(user.getStatusID());
            contactNotification.setContactName(user.getName());
            
            ContactsDAO contactsDao = new ContactsDAO();
            List<Contact> contactsList = contactsDao.getFriendContacts(phoneNumber);
            
            
            contactsList.forEach((contact) -> {
                ClientService clientServiceNotify = onlineClients.get(((FriendContact)contact).getPhoneNumber());
                if(clientServiceNotify != null)
                {
                    try {
                        clientServiceNotify.notify(contactNotification);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void unregisterClient(String phoneNumber)
    {
        try {
            MainController.onlineClients.remove(phoneNumber);
            ContactNotification contactNotification = new ContactNotification();
            UserDAO userDAO = new UserDAO();
            List<Object> primaryKeys = new ArrayList<>();
            primaryKeys.add(phoneNumber);
            
            User user = userDAO.getByPrimaryKey(primaryKeys);
            
            contactNotification.setContactPhoneNumber(phoneNumber);
            contactNotification.setNewStatus("Offline");
            contactNotification.setStatusId(null);
            contactNotification.setContactName(user.getName());
            
            ContactsDAO contactsDao = new ContactsDAO();
            List<Contact> contactsList = contactsDao.getFriendContacts(phoneNumber);
            
            
            contactsList.forEach((contact) -> {
                ClientService clientService = onlineClients.get(((FriendContact)contact).getPhoneNumber());
                if(clientService != null)
                {
                    try {
                        clientService.notify(contactNotification);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static ClientService getClientService(String phoneNumber)
    {
        return onlineClients.get(phoneNumber);
    }
}
