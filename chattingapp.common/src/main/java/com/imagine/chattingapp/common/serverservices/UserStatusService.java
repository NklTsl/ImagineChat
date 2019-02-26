/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.serverservices;

import com.imagine.chattingapp.common.entity.User_Status;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public interface UserStatusService extends Remote {
    public List<User_Status> getUserStatus() throws RemoteException, SQLException;
    public void changeStatus(String phoneNumber, byte newStatus) throws RemoteException,SQLException;
    public User_Status getStatusByPhone(String phoneNumber) throws RemoteException, SQLException;
}
