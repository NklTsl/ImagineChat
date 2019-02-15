/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class RegisterServiceImpl extends UnicastRemoteObject implements RegisterService{

    public RegisterServiceImpl() throws RemoteException {
        
    }

    
    @Override
    public void register(User user) throws RemoteException {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.persist(user);
        } catch (SQLException ex) {
            throw new RemoteException();
        }
        
    }
    
}
