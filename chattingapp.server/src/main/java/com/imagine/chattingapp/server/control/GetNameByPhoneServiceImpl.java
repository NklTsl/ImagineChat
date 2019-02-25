/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.serverservices.GetNameByPhoneService;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class GetNameByPhoneServiceImpl extends UnicastRemoteObject implements GetNameByPhoneService {

    public GetNameByPhoneServiceImpl() throws RemoteException {
    }

    
    @Override
    public String getByPhone(String phoneNumber) throws RemoteException, SQLException {
        UserDAO userDAO = new UserDAO();
        List<Object> primaryKey = new ArrayList<>();
        primaryKey.add(phoneNumber);
        return userDAO.getByPrimaryKey(primaryKey).getName();
    }
    
}
