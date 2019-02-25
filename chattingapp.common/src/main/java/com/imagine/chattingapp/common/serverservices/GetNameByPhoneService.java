/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author Mahmoud Shereif
 */
public interface GetNameByPhoneService extends Remote {
    public String getByPhone(String phoneNumber) throws RemoteException, SQLException;
            
}
