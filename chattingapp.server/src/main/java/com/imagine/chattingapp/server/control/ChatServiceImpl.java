/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.GroupMessage;
import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.dto.OneToOneMessage;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.ChatService;
import com.imagine.chattingapp.server.dal.dao.Chat_Group_UsersDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud Shereif
 */
public class ChatServiceImpl extends UnicastRemoteObject implements ChatService{
    
    public ChatServiceImpl() throws RemoteException{
    }
    
    
    @Override
    public void sendMessage(Message message) throws RemoteException{
        if(message instanceof OneToOneMessage)
        {
            OneToOneMessage oneToOneMessage = (OneToOneMessage)message;
            ClientService clientService = MainController.getClientService(oneToOneMessage.getReceiverPhone());
            clientService.receive(oneToOneMessage);
        }
        else
        {
            try {
                GroupMessage groupMessage = (GroupMessage)message;
                Chat_Group_UsersDAO chatGroupUsersDAO = new Chat_Group_UsersDAO();
                ClientService clientService = null;
                
                List<String> chatGroupUsersPhones = chatGroupUsersDAO.getPhonesByGroupId(String.valueOf(groupMessage.getReceiverGroup()));
                for(String userPhone: chatGroupUsersPhones)
                {
                    if(!userPhone.equals(groupMessage.getSenderUser().getPhoneNumber()))
                    {
                        clientService = MainController.getClientService(userPhone);
                        if(clientService != null)
                            clientService.receive(groupMessage);
                    }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ChatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
