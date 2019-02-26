/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.entity.User_Status;
import com.imagine.chattingapp.common.serverservices.UserStatusService;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import com.imagine.chattingapp.server.dal.dao.User_StatusDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class UserStatusServiceImpl extends UnicastRemoteObject implements UserStatusService {
    public UserStatusServiceImpl() throws RemoteException {
    }

    @Override
    public List<User_Status> getUserStatus() throws RemoteException, SQLException {
        User_StatusDAO statusDAO = new User_StatusDAO();
        return statusDAO.getAll();
    }

    @Override
    public void changeStatus(String phoneNumber, byte newStatus) throws RemoteException, SQLException {
        UserDAO userDAO = new UserDAO();
        List<Object> primaryKey = new ArrayList<>();
        primaryKey.add(phoneNumber);
        
        User user = userDAO.getByPrimaryKey(primaryKey);
        user.setStatusID(newStatus);
        
        userDAO.update(user);
        
        MainController.notifiyStatusChange(phoneNumber, newStatus);
    }

    @Override
    public User_Status getStatusByPhone(String phone) throws RemoteException, SQLException {
        UserDAO userDAO = new UserDAO();
        List<Object> primaryKey = new ArrayList<>();
        primaryKey.add(phone);
        
        User user = userDAO.getByPrimaryKey(primaryKey);
        
        User_StatusDAO statusDAO= new User_StatusDAO();
        List<Object> primaryKeys = new ArrayList<>();
        primaryKeys.add(user.getStatusID());
        return statusDAO.getByPrimaryKey(primaryKeys);
    }
    
}
