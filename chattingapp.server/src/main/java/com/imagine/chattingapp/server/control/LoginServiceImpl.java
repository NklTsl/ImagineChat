/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.imagine.chattingapp.common.serverservices.LoginLogoutService;

/**
 *
 * @author Mahmoud Shereif
 */
public class LoginServiceImpl extends UnicastRemoteObject implements LoginLogoutService{
    public LoginServiceImpl() throws RemoteException{
        
    }

    
    @Override
    public LightUser login(LoginUser loginUser, ClientService clientService) throws RemoteException {
        LightUser lightUser = null;
        try {
            UserDAO userDAO = new UserDAO();
            List<String> columnNames = new ArrayList<String>();
            columnNames.add("Phone_Number");
            columnNames.add("Password");
            
            List<Object> columnValues = new ArrayList<Object>();
            columnValues.add(loginUser.getPhoneNumber());
            columnValues.add(loginUser.getPassword());
            
            List<User> userList = userDAO.getByColumnNames(columnNames, columnValues);
            
            
            if(!userList.isEmpty())
            {
                User user = userList.get(0);
                lightUser = new LightUser();
                lightUser.setPhoneNumber(user.getPhoneNumber());
                lightUser.setName(user.getName());
                lightUser.setImage(user.getPicture());
                MainController.registerClient(lightUser.getPhoneNumber(), clientService);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lightUser;
    }

    @Override
    public void logout(LoginUser loginUser) {
        MainController.unregisterClient(loginUser.getPhoneNumber());
    }
    
}
