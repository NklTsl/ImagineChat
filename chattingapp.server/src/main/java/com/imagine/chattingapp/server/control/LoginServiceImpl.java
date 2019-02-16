/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.LoginService;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class LoginServiceImpl extends UnicastRemoteObject implements LoginService{
    
    public LoginServiceImpl()throws RemoteException{}

    
    @Override
    public void login(LoginUser loginUser, ClientService clientService) throws RemoteException {
        try {
            UserDAO userDAO = new UserDAO();
            List<String> columnNames = new ArrayList<String>();
            columnNames.add("Phone_Number");
            columnNames.add("Password");
            
            List<Object> columnValues = new ArrayList<Object>();
            columnValues.add(loginUser.getPhoneNumber());
            columnValues.add(loginUser.getPassword());
            
            List<User> userList = userDAO.getByColumnNames(columnNames, columnValues);
            if(userList.isEmpty())
            {
                throw new RemoteException();
            }
            else
            {
                clientService.receiveUserDetails(userList.get(0));
            }
        
    }   catch (SQLException ex) {
            Logger.getLogger(LoginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
