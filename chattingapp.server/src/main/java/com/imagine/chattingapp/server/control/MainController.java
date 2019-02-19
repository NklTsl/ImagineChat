/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.serverservices.RegisterService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class MainController {
    public static void main(String[] args)
    {
        try {
            LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
            Registry registery = LocateRegistry.createRegistry(2000);
            registery.rebind("LoginService", loginServiceImpl);
            AddNewContactImpl addContact = new AddNewContactImpl();
            registery.rebind("AddContactService", addContact);
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
