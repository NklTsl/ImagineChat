package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.011417444
*/

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.control.ReceiveFileServiceImpl;
import com.imagine.chattingapp.client.control.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import com.imagine.chattingapp.common.dto.ChatSession;
import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.ContactNotification;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.GroupContact;
import com.imagine.chattingapp.common.dto.GroupMember;
import com.imagine.chattingapp.common.dto.GroupMessage;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.dto.Notification;
import com.imagine.chattingapp.common.dto.OneToOneMessage;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.serverservices.ChatService;
import com.imagine.chattingapp.common.serverservices.ClientSendFileServiceP2P;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import java.io.ByteArrayInputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import com.imagine.chattingapp.common.serverservices.LoginLogoutService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class ChatController implements Initializable {
    
    @FXML
    private ImageView viewProfileImage;
    @FXML
    private Label lblUserName;
    @FXML
    private ListView<Contact> lstContacts;
    @FXML
    private Button btnBack;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private WebView webView;
    @FXML
    private Button btnAddGroup;
    @FXML
    private Button btnFriendRequest;
    @FXML
    private Button btnSendFile;
    
    boolean messageFlag = true;
    
    String htmlAll;
    Optional<ButtonType> result;
    
    WebEngine webEngine;
    FriendContact currentSelectedFriendContact;
    GroupContact  currentSelectedGroupContact;
    Boolean friendOrGroupContact;
    ChatSession currentChatSession;
    
    private MainController mainController;
    private LightUser lightUser;
    private LoginUser loginUser;
    private ChatService chatService;
    private AudioClip onlineSound;
    private AudioClip offlineSound;
    
    private OneToOneMessage oneToOneMessage;
    private GroupMessage groupMessage;
    
    Map<String, ChatSession> chatSessionsMap;
    
    
    File choosenFile;
    
    public ChatController(MainController mainController, LightUser lightUser, LoginUser loginUser) {
        this.mainController = mainController;
        this.lightUser = lightUser;
        this.loginUser = loginUser;
        chatSessionsMap = new TreeMap<>();
        onlineSound = new AudioClip(new File("target/classes/Online.mp3").toURI().toString());
        offlineSound = new AudioClip(new File("target/classes/Offline.mp3").toURI().toString());
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            webEngine = webView.getEngine();
            //webView.setDisable(true);
            webEngine.setUserStyleSheetLocation(getClass().getResource("/webView.css").toString());
            
            
            /*File imgfile = new File("target/classes/DefaultPerson.png");
            
            
            FileInputStream fin = new FileInputStream(imgfile);
            byte[] image = new byte[(int)imgfile.length()];
            fin.read(image);
            
            
            String base64Encoded = Base64.getEncoder().encodeToString(image);
            String beginningTag = "<div class=\"container\">\n" ;
            String imageTag = "  <img src=\"data:image/jpeg;base64,%1$s\" ";
            String restOfTags =
            "  <p>Hello. How are you today?</p>\n" +
            "  <span class=\"time-right\">11:00</span>\n" +
            "</div>";
            
            String encodedBase64Tag = String.format(imageTag, base64Encoded);
            
            String finalHTML = beginningTag + encodedBase64Tag + restOfTags;
            
            //System.out.println(base64Encoded);
            
            webEngine.loadContent(finalHTML);*/
            //System.err.println(html);
            
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            ContactsService contactsService = (ContactsService) registry.lookup("ContactsService");
            List<Contact> contacts = contactsService.getContacts(lightUser.getPhoneNumber());
            chatService = (ChatService) registry.lookup("ChatService");
            
            contacts.forEach((contact) -> {
                lstContacts.getItems().add(contact);
            });
            
            if(lightUser.getImage() != null)
                viewProfileImage.setImage(new Image(new ByteArrayInputStream(lightUser.getImage())));
            lblUserName.setText(lightUser.getName());
            
            lstContacts.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
                @Override
                public ListCell<Contact> call(ListView<Contact> param) {
                    return new ContactCell();
                }
            });
            
            lstContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, currentContact) -> {
                
                if(currentContact instanceof FriendContact){
                    currentSelectedFriendContact = (FriendContact)currentContact;
                    friendOrGroupContact = true;
                    fillChatArea(currentSelectedFriendContact.getPhoneNumber());
                    if(currentSelectedFriendContact.getNotified())
                    {
                        currentSelectedFriendContact.setNotified(false);
                        lstContacts.refresh();
                    }
                    if((currentSelectedFriendContact).getStatus() == null)
                    {
                        htmlEditor.setDisable(true);
                    }
                    else
                    {
                        htmlEditor.setDisable(false);
                    }
                }
                else
                {
                    currentSelectedGroupContact = (GroupContact)currentContact;
                    friendOrGroupContact = false;
                    if(currentSelectedGroupContact.getNotified())
                    {
                        currentSelectedGroupContact.setNotified(false);
                        lstContacts.refresh();
                    }
                    fillChatArea(String.valueOf(currentSelectedGroupContact.getGroupId()));
                    htmlEditor.setDisable(false);
                }
                
            });
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) {
        try {
            mainController.chatController = null;
            mainController.switchToLoginScene();
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            LoginLogoutService loginLogoutService = (LoginLogoutService) registry.lookup("LoginLogoutService");
            loginLogoutService.logout(loginUser);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnSendFileOnAction(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File To Send");
        File choosenFile = fileChooser.showOpenDialog(mainController.getPrimaryStage());
        
        if(choosenFile != null)
        {
            ClientSendFileServiceP2P clientSendFile = (ClientSendFileServiceP2P)ServiceLocator.getService("SendFileService");
            SimpleRemoteInputStream remoteInputStream = null;
            if(clientSendFile != null){
                try {
                    ReceiveFileService receiveFileService = clientSendFile.getReceiveFileService(currentSelectedFriendContact.getPhoneNumber(), lightUser.getName());
                    if(receiveFileService != null){
                        String fileName = choosenFile.getName();
                        String extention  = fileName.substring(fileName.lastIndexOf("."));
                        remoteInputStream = new SimpleRemoteInputStream(new FileInputStream(choosenFile));
                        receiveFileService.sendFile(remoteInputStream.export(), extention, fileName);
                    }else{
                        Alert receiveFileAlert = new Alert(Alert.AlertType.INFORMATION, "Your friend rejected to Receive!");
                        receiveFileAlert.showAndWait();
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if(remoteInputStream != null)
                        remoteInputStream.close();
                }
            }
        }else
        {
            //Should Handle Not Choosing any Files
        }
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
        FileInputStream fin = null;
        try {
            //webEngine.loadContent(htmlEditor.getHtmlText());
            System.out.println(htmlEditor.getHtmlText());
            File imgfile = new File("target/classes/DefaultPerson.png");
            fin = new FileInputStream(imgfile);
            byte[] image = new byte[(int)imgfile.length()];
            fin.read(image);
            if(messageFlag)
            {
                String base64Encoded = Base64.getEncoder().encodeToString(image);
                String beginningTag = "<div class=\"container\">\n" ;
                String imageTag = "  <img class = \"right\" src=\"data:image/jpeg;base64,%1$s\" ";
                String restOfTags =
                        "  <p>Hello. How are you today?</p>\n" +
                        "  <span class=\"time-right\">11:00</span>\n" +
                        "</div>";
                
                String encodedBase64Tag = String.format(imageTag, base64Encoded);
                
                //String finalHTML = beginningTag + encodedBase64Tag + restOfTags;
                htmlAll += beginningTag + encodedBase64Tag + restOfTags;
                //System.out.println(base64Encoded);
                
                webEngine.loadContent(htmlAll);
                
                messageFlag = false;
            }
            else
            {
                String base64Encoded = Base64.getEncoder().encodeToString(image);
                String beginningTag = "<div class=\"container darker\">\n" ;
                String imageTag = "  <img src=\"data:image/jpeg;base64,%1$s\" ";
                String restOfTags =
                        "  <p>Hello. How are you today?</p>\n" +
                        "  <span class=\"time-left\">11:00</span>\n" +
                        "</div>";
                
                String encodedBase64Tag = String.format(imageTag, base64Encoded);
                
                //String finalHTML = beginningTag + encodedBase64Tag + restOfTags;
                
                htmlAll += beginningTag + encodedBase64Tag + restOfTags;
                //System.out.println(base64Encoded);
                
                webEngine.loadContent(htmlAll);
                messageFlag = true;
            }
            //webEngine.loadContent(htmlEditor.getHtmlText());//txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
            htmlEditor.setHtmlText("<html><head></head><body>");
            if(friendOrGroupContact == null){
                //should Select a contact to send
            }
            else if(friendOrGroupContact && !htmlEditor.getHtmlText().isEmpty()){
                oneToOneMessage = new OneToOneMessage();
                oneToOneMessage.setMessage(htmlEditor.getHtmlText().toString().trim());
                oneToOneMessage.setSenderPhone(lightUser.getPhoneNumber());
                oneToOneMessage.setReceiverPhone(currentSelectedFriendContact.getPhoneNumber());
                
                try {
                    chatService.sendMessage(oneToOneMessage);
                    
                    
                    List<Message> previousMessagesList = createOrAppendChatSession(currentSelectedFriendContact.getPhoneNumber());
                    previousMessagesList.add(oneToOneMessage);
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }  catch (IOException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                groupMessage = new GroupMessage();
                groupMessage.setMessage(htmlEditor.getHtmlText().toString().trim());
                groupMessage.setReceiverGroup(currentSelectedGroupContact.getGroupId());
                groupMessage.setSenderUser(lightUser);
                
                try {
                    chatService.sendMessage(groupMessage);
                    htmlEditor.setHtmlText("");
                    List<Message> previousMessagesList = createOrAppendChatSession(String.valueOf(currentSelectedGroupContact.getGroupId()));
                    previousMessagesList.add(groupMessage);
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
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
            if(friendOrGroupContact != null && friendOrGroupContact)
            {
                if(currentSelectedFriendContact.getPhoneNumber().equals(oneToOneMessage.getSenderPhone()))
                {
                    //txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
                }
                else
                {
                    FriendContact friendContact = (FriendContact)getContactById(oneToOneMessage.getSenderPhone());
                    friendContact.setNotified(true);
                    lstContacts.refresh();
                }
            }
            else
            {
                FriendContact friendContact = (FriendContact)getContactById(oneToOneMessage.getSenderPhone());
                friendContact.setNotified(true);
                lstContacts.refresh();
            }
            
            
        }
        else
        {
            groupMessage = (GroupMessage)message;
            previousMessagesList = createOrAppendChatSession(String.valueOf(groupMessage.getReceiverGroup()));
            previousMessagesList.add(groupMessage);
            if(friendOrGroupContact != null && !friendOrGroupContact)
            {
                if(currentSelectedGroupContact.getGroupId() == groupMessage.getReceiverGroup())
                {
                    //txtChatArea.appendText(groupMessage.getSenderUser().getName()+ ":" + groupMessage.getMessage() + "\n");
                }
                else
                {
                    GroupContact groupContact = (GroupContact)getContactById(String.valueOf(groupMessage.getReceiverGroup()));
                    groupContact.setNotified(true);
                    lstContacts.refresh();
                }
            }
            else
            {
                GroupContact groupContact = (GroupContact)getContactById(String.valueOf(groupMessage.getReceiverGroup()));
                groupContact.setNotified(true);
                lstContacts.refresh();
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
        //txtChatArea.clear();
        if(chatSession != null)
        {
            List<Message> previousMessagesList = chatSession.getMessagesList();
            previousMessagesList.forEach((message) -> {
                if(friendOrGroupContact)
                {
                    oneToOneMessage = (OneToOneMessage) message;
                    //txtChatArea.appendText(oneToOneMessage.getSenderPhone() + ":" + oneToOneMessage.getMessage() + "\n");
                }
                else
                {
                    groupMessage = (GroupMessage) message;
                    //txtChatArea.appendText(groupMessage.getSenderUser().getPhoneNumber()+ ":" + groupMessage.getMessage() + "\n");
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
                    break;
                }
            }
            else
            {
                if(((GroupContact)item).getGroupId() == Integer.parseInt(id))
                {
                    tempContact = item;
                    break;
                }
            }
        }
        return tempContact;
    }
    
    public void handleNotification(Notification notification) {
        if(notification instanceof ContactNotification)
        {
            ContactNotification contactNotification = (ContactNotification) notification;
            Contact contact = getContactById(contactNotification.getContactPhoneNumber());
            if(contact != null)
            {
                FriendContact friendContact = (FriendContact)contact;
                
                friendContact.setStatusDescription(contactNotification.getNewStatus());
                Platform.runLater(() -> {
                    Notifications.create()
                            .title("Chat App")
                            .text("Your Friend " + contactNotification.getContactName()
                                    + " is " + contactNotification.getNewStatus() + "!")
                            .showInformation();
                });
                
                if(contactNotification.getStatusId() == 0)
                {
                    onlineSound.play();
                    friendContact.setStatus((byte)0);
                    lstContacts.refresh();
                }
                else
                {
                    offlineSound.play();
                    friendContact.setStatus(null);
                    lstContacts.refresh();
                }
                
                
            }
        }
    }
    
    @FXML
    private void btnAddGroupOnAction(ActionEvent event) {
        Stage addNewGroupStage = new Stage();
        List<GroupMember> groupMembers = mapToGroupMember();
        
        try {
            FXMLLoader loader = new FXMLLoader();
            AddNewGroupController addNewGroupController = new AddNewGroupController(groupMembers, addNewGroupStage, loginUser.getPhoneNumber());
            loader.setController(addNewGroupController);
            Parent root = loader.load(getClass().getResource("/AddNewGroupDesign.fxml").openStream());
            
            Scene scene = new Scene(root);
            
            addNewGroupStage.setTitle("Login");
            addNewGroupStage.setScene(scene);
            addNewGroupStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private List<GroupMember> mapToGroupMember(){
        List<GroupMember> membersList = new ArrayList<>();
        lstContacts.getItems().forEach((contact) -> {
            if(contact instanceof FriendContact)
            {
                FriendContact friendContact = (FriendContact)contact;
                
                GroupMember groupMember = new GroupMember();
                groupMember.setPhoneNumber(friendContact.getPhoneNumber());
                groupMember.setName(friendContact.getName());
                groupMember.setImage(friendContact.getImage());
                groupMember.setChecked(false);
                membersList.add(groupMember);
            }
        });
        
        return membersList;
        
    }
    @FXML
    public void btnFriendRequestOnAction(){
        mainController.switchToAddContactPopUp();
        
    }
    
    public ReceiveFileService showReceiveFileRequest(String senderName) {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(()->{
            Alert receiveFileAlert = new Alert(Alert.AlertType.CONFIRMATION, "Your friend "+senderName+" Want to Send a File"
                    + " \nDo you want to Accept?");
            result = receiveFileAlert.showAndWait();
            latch.countDown();
        });
        
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ReceiveFileService receiveFileService = null;
        //could be done without checking the OK
        if (result.get() == ButtonType.OK) {
            try {
                receiveFileService = new ReceiveFileServiceImpl(mainController);
            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return receiveFileService;
    }
    
    public void receiveFile(RemoteInputStream remoteInputStream, String ext, String fileName) {
        InputStream istream = null;
        try {
            
            final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(()->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File To Send");
            choosenFile = fileChooser.showSaveDialog(mainController.getPrimaryStage());
            latch.countDown();
        });
        
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
            if(choosenFile != null)
            {
                istream = RemoteInputStreamClient.wrap(remoteInputStream);
                FileOutputStream ostream = null;
                try {
                    
                    ostream = new FileOutputStream(choosenFile);
                    System.out.println("Writing file " + choosenFile);
                    
                    byte[] buf = new byte[1024 * 1024];
                    
                    int bytesRead = 0;
                    while ((bytesRead = istream.read(buf)) >= 0) {
                        ostream.write(buf, 0, bytesRead);
                    }
                    ostream.flush();
                    
                    System.out.println("Finished writing file " + choosenFile);
                    
                } finally {
                    try {
                        if (istream != null) {
                            istream.close();
                        }
                    } finally {
                        if (ostream != null) {
                            ostream.close();
                        }
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                istream.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}