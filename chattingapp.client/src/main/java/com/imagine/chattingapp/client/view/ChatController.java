package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import com.imagine.chattingapp.client.control.MainController;
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
import java.util.List;
import java.util.ResourceBundle;
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
    
    
    String currentSelectedContact;
    
    private MainController mainController;
    private LightUser lightUser;
    private ChatService chatService;
    
    private OneToOneMessage oneToOneMessage;
    private GroupMessage groupMessage;
    
    public ChatController(MainController mainController, LightUser lightUser) {
        this.mainController = mainController;
        this.lightUser = lightUser;
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
                
                if(currentContact instanceof FriendContact)
                {
                    currentSelectedContact = ((FriendContact)currentContact).getPhoneNumber();
                    if(currentContact instanceof FriendContact){
                        if(((FriendContact)currentContact).getStatus() == null)
                        {
                            txtMessage.setDisable(true);
                        }
                        else
                        {
                            txtMessage.setDisable(false);
                        }
                    }
                }
                else
                {
                    int groupId = ((GroupContact)currentContact).getGroupId();
                    currentSelectedContact = String.valueOf(groupId);
                }
                txtChatArea.clear();
                
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
            if(currentSelectedContact!=null){
                if(lstContacts.getSelectionModel().getSelectedItem() instanceof FriendContact)
                {
                    oneToOneMessage = new OneToOneMessage();
                    oneToOneMessage.setMessage(txtMessage.getText().toString().trim());
                    oneToOneMessage.setSenderPhone(lightUser.getPhoneNumber());
                    oneToOneMessage.setReceiverPhone(currentSelectedContact);
                    
                    try {
                        chatService.sendMessage(oneToOneMessage);
                        txtMessage.clear();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    groupMessage = new GroupMessage();
                    groupMessage.setMessage(txtMessage.getText().toString().trim());
                    groupMessage.setReceiverGroup(Integer.parseInt(currentSelectedContact));
                    groupMessage.setSenderUser(lightUser);
                    
                    try {
                        chatService.sendMessage(groupMessage);
                        txtMessage.clear();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public void receive(Message message)
    {
        if(message instanceof OneToOneMessage)
        {
            oneToOneMessage = (OneToOneMessage)message;
            txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
            
        }
        else
        {
            groupMessage = (GroupMessage)message;
            txtChatArea.appendText(groupMessage.getSenderUser().getName()+ ":" + groupMessage.getMessage() + "\n");
        }
        
    }
    
}
