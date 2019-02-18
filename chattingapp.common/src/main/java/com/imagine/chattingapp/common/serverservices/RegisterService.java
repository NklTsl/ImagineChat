/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public interface RegisterService extends Remote {
    public void register(User user) throws RemoteException;
    public List<Country> getCountries() throws RemoteException;
    
}
