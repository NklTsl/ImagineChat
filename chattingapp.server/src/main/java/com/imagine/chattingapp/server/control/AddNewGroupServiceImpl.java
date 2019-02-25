/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.dto.NewGroupInfo;
import com.imagine.chattingapp.common.entity.Chat_Group;
import com.imagine.chattingapp.common.entity.Chat_Group_Users;
import com.imagine.chattingapp.common.serverservices.AddNewGroupService;
import com.imagine.chattingapp.server.dal.dao.Chat_GroupDAO;
import com.imagine.chattingapp.server.dal.dao.Chat_Group_UsersDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class AddNewGroupServiceImpl extends UnicastRemoteObject implements AddNewGroupService {
    
    public AddNewGroupServiceImpl() throws RemoteException {
        
    }
    
    
    @Override
    public void addNewGroup(NewGroupInfo groupInfo) throws RemoteException {
        try {
            Chat_GroupDAO  chat_GroupDAO = new Chat_GroupDAO();
            Chat_Group chat_Group = new Chat_Group();
            chat_Group.setName(groupInfo.getGroupName());
            chat_Group.setOwnerPhoneNumber(groupInfo.getOwnerPhoneNumber());
            chat_Group.setPicture(groupInfo.getGroupImage());
            chat_GroupDAO.persist(chat_Group);
            
            Chat_Group_UsersDAO chat_Group_UsersDAO = new Chat_Group_UsersDAO();
            
            groupInfo.getMembersPhones().forEach((member) -> {
                try {
                    Chat_Group_Users chat_Group_Users = new Chat_Group_Users();
                    chat_Group_Users.setGroupId(chat_GroupDAO.getLastInsertedGroupId());
                    chat_Group_Users.setUserPhoneNumber(member);
                    chat_Group_UsersDAO.persist(chat_Group_Users);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AddNewGroupServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            
            Chat_Group_Users chat_Group_Users = new Chat_Group_Users();
            chat_Group_Users.setGroupId(chat_GroupDAO.getLastInsertedGroupId());
            chat_Group_Users.setUserPhoneNumber(groupInfo.getOwnerPhoneNumber());
            chat_Group_UsersDAO.persist(chat_Group_Users);
            
        } catch (SQLException ex) {
            Logger.getLogger(AddNewGroupServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
