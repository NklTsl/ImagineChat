/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
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
 * @author Mahmoud Shereif
 */
public class RegisterServiceImpl extends UnicastRemoteObject implements RegisterService{

    public RegisterServiceImpl() throws RemoteException {
        
    }

    
    @Override
    public void register(User user) throws RemoteException {
        try {
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            System.out.println(user.getGender());
            System.out.println(user.getPhoneNumber());
            System.out.println(user.getDateOfBirth());
            System.out.println(user.getPassword());
            System.out.println(user.getBiography());
            System.out.println(user.getStatusID());
            System.out.println(user.getCountryID());
            System.out.println(user.getPicture());
            
            
            UserDAO userDAO = new UserDAO();
            userDAO.persist(user); 
        } catch (SQLException ex) {
            throw new RemoteException();
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
