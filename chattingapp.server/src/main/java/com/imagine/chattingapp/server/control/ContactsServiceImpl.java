/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.customobj.Contact;
import com.imagine.chattingapp.common.customobj.FriendContact;
import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import com.imagine.chattingapp.server.dal.dao.ContactsDao;
import com.imagine.chattingapp.server.dal.dao.DatabaseDataRetreival;
import com.imagine.chattingapp.server.dal.dao.FriendDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class ContactsServiceImpl extends UnicastRemoteObject implements ContactsService {

    public ContactsServiceImpl() throws RemoteException {
        
    }

    
    @Override
    public List<Contact> getContacts(String phoneNumber) throws RemoteException{
        List<Contact> contactList = null;
        try {

            ContactsDao contactsDao = new ContactsDao();
            contactList = contactsDao.getFriendContacts(phoneNumber);
            contactList.addAll(contactsDao.getGroupContacts(phoneNumber));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ContactsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contactList;
    }
    
}
