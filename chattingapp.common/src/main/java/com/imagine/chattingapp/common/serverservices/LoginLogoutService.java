/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.LoginUser;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mahmoud Shereif
 */
public interface LoginLogoutService extends Remote {
    
    public LightUser login(LoginUser loginUser, ClientService clientService) throws RemoteException;
    public void logout(LoginUser loginUser) throws RemoteException;
}
