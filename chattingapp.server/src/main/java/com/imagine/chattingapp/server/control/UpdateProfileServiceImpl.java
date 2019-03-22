/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.UpdateProfileService;
import com.imagine.chattingapp.server.dal.dao.CountryDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamada Abdrabou
 */
public class UpdateProfileServiceImpl extends UnicastRemoteObject implements UpdateProfileService{

    public UpdateProfileServiceImpl()throws RemoteException{
    }
    @Override
    public void updateProfile(User user) throws RemoteException {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.update(user);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Country> getCountries() throws RemoteException {
         try {
            CountryDAO countryDAO = new CountryDAO();
            return countryDAO.getAll();
        } catch (SQLException ex) {
            RemoteException remoteException = new RemoteException();
            remoteException.initCause(ex);
            throw remoteException;
        }

    }
    
}