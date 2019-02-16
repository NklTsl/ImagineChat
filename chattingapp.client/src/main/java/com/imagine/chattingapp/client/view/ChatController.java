package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.common.customobj.ChatSession;
import com.imagine.chattingapp.common.customobj.Contact;
import com.imagine.chattingapp.common.customobj.FriendContact;
import com.imagine.chattingapp.common.customobj.GroupContact;
import com.imagine.chattingapp.common.customobj.GroupMessage;
import com.imagine.chattingapp.common.customobj.LightUser;
import com.imagine.chattingapp.common.customobj.Message;
import com.imagine.chattingapp.common.customobj.OneToOneMessage;
import com.imagine.chattingapp.common.serverservices.ChatService;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import com.imagine.chattingapp.common.serverservices.LoginService;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class ChatController implements Initializable {
    
    @FXML
    private ListView<Contact> lstContacts;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtMessage;
    @FXML
    private TextArea txtChatArea;
    
    
    FriendContact currentSelectedFriendContact;
    GroupContact  currentSelectedGroupContact;
    Boolean friendOrGroupContact;
    ChatSession currentChatSession;
    
    private MainController mainController;
    private LightUser lightUser;
    private ChatService chatService;
    
    private OneToOneMessage oneToOneMessage;
    private GroupMessage groupMessage;
    
    Map<String, ChatSession> chatSessionsMap;
    
    public ChatController(MainController mainController, LightUser lightUser) {
        this.mainController = mainController;
        this.lightUser = lightUser;
        chatSessionsMap = new TreeMap<>();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            ContactsService contactsService = (ContactsService) registry.lookup("ContactsService");
            List<Contact> contacts = contactsService.getContacts(lightUser.getPhoneNumber());
            chatService = (ChatService) registry.lookup("ChatService");
            
            contacts.forEach((contact) -> {
                lstContacts.getItems().add(contact);
            });
            
            lstContacts.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
                @Override
                public ListCell<Contact> call(ListView<Contact> param) {
                    return new ListCell<Contact>(){
                        @Override
                        protected void updateItem(Contact contact, boolean empty) {
                            super.updateItem(contact, empty);
                            if(!empty)
                            {
                                if(contact instanceof FriendContact)
                                    this.setText(((FriendContact)contact).getName());
                                else
                                    this.setText(((GroupContact)contact).getName());
                            }
                        }
                        
                    };
                }
            });
            
            lstContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, currentContact) -> {
                
                if(currentContact instanceof FriendContact){
                    currentSelectedFriendContact = (FriendContact)currentContact;
                    friendOrGroupContact = true;
                    fillChatArea(currentSelectedFriendContact.getPhoneNumber());
                    if((currentSelectedFriendContact).getStatus() == null)
                    {
                        //txtMessage.setDisable(true);
                    }
                    else
                    {
                        //txtMessage.setDisable(false);
                    }
                }
                else
                {
                    currentSelectedGroupContact = (GroupContact)currentContact;
                    friendOrGroupContact = false;
                    fillChatArea(String.valueOf(currentSelectedGroupContact.getGroupId()));
                    txtMessage.setDisable(false);
                }
                
            });
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) {
        
    }
    
    @FXML
    private void txtMessageKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
        {
            sendMessage();
        }
        
    }
    
    private void sendMessage()
    {
        if(friendOrGroupContact == null){
            //should Select a contact to send
        }
        else if(friendOrGroupContact && !txtMessage.getText().isEmpty()){
            oneToOneMessage = new OneToOneMessage();
            oneToOneMessage.setMessage(txtMessage.getText().toString().trim());
            oneToOneMessage.setSenderPhone(lightUser.getPhoneNumber());
            oneToOneMessage.setReceiverPhone(currentSelectedFriendContact.getPhoneNumber());
            
            try {
                chatService.sendMessage(oneToOneMessage);
                txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
                txtMessage.clear();
                
                List<Message> previousMessagesList = createOrAppendChatSession(currentSelectedFriendContact.getPhoneNumber());
                previousMessagesList.add(oneToOneMessage);
            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            groupMessage = new GroupMessage();
            groupMessage.setMessage(txtMessage.getText().toString().trim());
            groupMessage.setReceiverGroup(currentSelectedGroupContact.getGroupId());
            groupMessage.setSenderUser(lightUser);
            
            try {
                chatService.sendMessage(groupMessage);
                txtMessage.clear();
                List<Message> previousMessagesList = createOrAppendChatSession(String.valueOf(currentSelectedGroupContact.getGroupId()));
                previousMessagesList.add(groupMessage);
            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void receive(Message message)
    {
        Contact tempContact = null;
        List<Message> previousMessagesList;
        if(message instanceof OneToOneMessage)
        {
            oneToOneMessage = (OneToOneMessage)message;
            previousMessagesList = createOrAppendChatSession(oneToOneMessage.getSenderPhone());
            previousMessagesList.add(oneToOneMessage);
            if(currentSelectedFriendContact != null)
            {
                if(currentSelectedFriendContact.getPhoneNumber().equals(oneToOneMessage.getSenderPhone()))
                {
                    txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
                }
                else
                {
                    //TODO Notification for receiving message
                }
            }
            
            
        }
        else
        {
            groupMessage = (GroupMessage)message;
            previousMessagesList = createOrAppendChatSession(String.valueOf(groupMessage.getReceiverGroup()));
            previousMessagesList.add(groupMessage);
            if(currentSelectedGroupContact != null)
            {
                if(currentSelectedGroupContact.getGroupId() == groupMessage.getReceiverGroup())
                {
                    txtChatArea.appendText(groupMessage.getSenderUser().getName()+ ":" + groupMessage.getMessage() + "\n");
                }
                else
                {
                    //TODO Notification for receiving message
                }
            }
            
        }
    }
    
    
    private List<Message> createOrAppendChatSession(String sessionId)
    {
        currentChatSession = chatSessionsMap.get(sessionId);
        if(currentChatSession != null)
        {
            return currentChatSession.getMessagesList();
        }
        else
        {
            currentChatSession = new ChatSession();
            currentChatSession.setSessionId(sessionId);
            Contact tempContact = getContactById(sessionId);
            if(tempContact instanceof FriendContact)
            {
                currentChatSession.setOneOrGroupFlag(true);
                currentChatSession.setName(((FriendContact)tempContact).getName());
                currentChatSession.setPicture(((FriendContact)tempContact).getImage());
            }
            else
            {
                currentChatSession.setOneOrGroupFlag(false);
                currentChatSession.setName(((GroupContact)tempContact).getName());
                currentChatSession.setPicture(((GroupContact)tempContact).getImage());
            }
            currentChatSession.setMessagesList(new ArrayList<>());
            chatSessionsMap.put(sessionId, currentChatSession);
            return currentChatSession.getMessagesList();
        }
    }
    
    private void fillChatArea(String sessionId)
    {
        ChatSession chatSession = chatSessionsMap.get(sessionId);
        txtChatArea.clear();
        if(chatSession != null)
        {
            List<Message> previousMessagesList = chatSession.getMessagesList();
            previousMessagesList.forEach((message) -> {
                if(friendOrGroupContact)
                {
                    oneToOneMessage = (OneToOneMessage) message;
                    txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
                }
                else
                {
                    groupMessage = (GroupMessage) message;
                    txtChatArea.appendText(groupMessage.getSenderUser().getPhoneNumber()+ ":" + groupMessage.getMessage() + "\n");
                }
            });
        }
    }
    
    private Contact getContactById(String id)
    {
        Contact tempContact = null;
        for(Contact item:lstContacts.getItems()){
            if(item instanceof FriendContact)
            {
                if(((FriendContact)item).getPhoneNumber().equals(id))
                {
                    tempContact = item;
                }
            }
            else
            {
                if(((GroupContact)item).getGroupId() == Integer.parseInt(id))
                {
                    tempContact = item;
                }
            }
        }
        return tempContact;
    }
}
