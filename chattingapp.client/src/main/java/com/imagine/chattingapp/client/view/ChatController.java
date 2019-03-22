package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.imagine.chattingapp.client.control.EzzatChatBot;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.control.ReceiveFileServiceImpl;
import com.imagine.chattingapp.client.Model.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.client.utilities.ChatSessionToXML;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import com.imagine.chattingapp.common.dto.AnnounceNotification;
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
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.entity.User_Status;
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
import com.imagine.chattingapp.common.serverservices.UserStatusService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class ChatController implements Initializable {

    @FXML
    private Circle profileImageView;
    @FXML
    private Label lblUserName;
    @FXML
    private JFXListView<Contact> lstContacts;
    @FXML
    private JFXButton btnSaveChat;
    @FXML
    private JFXComboBox<User_Status> statusCombo;
    @FXML
    private FontAwesomeIconView sendAttachement;
    @FXML
    private FontAwesomeIconView notifications;
    @FXML
    private MenuButton viewNotification;
    @FXML
    private FontAwesomeIconView editProfile;
    @FXML
    private FontAwesomeIconView logoutIcon;
    @FXML
    private JFXButton btnHide;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private FontAwesomeIconView btnFriendRequest;
    @FXML
    private JFXToggleButton chatBotCheck;
    @FXML
    private WebView webView;
    @FXML
    private FontAwesomeIconView btnAddGroup;

    String htmlAll = "";
    Optional<ButtonType> result;

    WebEngine webEngine;
    FriendContact currentSelectedFriendContact;
    GroupContact currentSelectedGroupContact;
    Boolean friendOrGroupContact;
    ChatSession currentChatSession;

    EzzatChatBot chat;

    private MainController mainController;
    private LightUser lightUser;
    private LoginUser loginUser;
    private ChatService chatService;
    private AudioClip onlineSound;
    private AudioClip offlineSound;

    private OneToOneMessage oneToOneMessage;
    private GroupMessage groupMessage;

    Map<String, ChatSession> chatSessionsMap;

    byte[] defaultUserImage;
    byte[] defaultGroupImage;
    File choosenFile;

    WebView view;

    public ChatController(MainController mainController, LightUser lightUser, LoginUser loginUser) {
        try {
            this.mainController = mainController;
            this.lightUser = lightUser;
            this.loginUser = loginUser;
            chatSessionsMap = new TreeMap<>();
            onlineSound = new AudioClip(getClass().getResource("/Online.mp3").toString());//new File("target/classes/Online.mp3").toURI().toString());
            offlineSound = new AudioClip(getClass().getResource("/Offline.mp3").toString());//new File("target/classes/Offline.mp3").toURI().toString());
            InputStream defaultPersonFileStream = getClass().getResourceAsStream("/DefaultPersonIcon.png");
            InputStream defaultGroupFileStream = getClass().getResourceAsStream("/DefaultGroupIcon.png");

            defaultUserImage = new byte[(int) defaultPersonFileStream.available()];
            defaultPersonFileStream.read(defaultUserImage);

            defaultGroupImage = new byte[(int) defaultGroupFileStream.available()];
            defaultGroupFileStream.read(defaultGroupImage);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
        }

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

            view = (WebView) ((GridPane) ((HTMLEditorSkin) htmlEditor.getSkin()).getChildren().get(0)).getChildren().get(2);

            ContactsService contactsService = (ContactsService) ServiceLocator.getService("ContactsService");

            List<Contact> contacts = contactsService.getContacts(lightUser.getPhoneNumber());

            UserStatusService userStatusService = (UserStatusService) ServiceLocator.getService("UserStatusService");
            chatService = (ChatService) ServiceLocator.getService("ChatService");
            
            List<User_Status> statuses = userStatusService.getUserStatus();

            statusCombo.getItems().addAll(statuses);

            statusCombo.setConverter(new StringConverter<User_Status>() {
                @Override
                public String toString(User_Status status) {
                    return status.getDescription();
                }

                @Override
                public User_Status fromString(String string) {
                    return statusCombo.getItems().stream().filter(ap
                            -> ap.getDescription().equals(string)).findFirst().orElse(null);
                }
            });

            statusCombo.getSelectionModel().select(userStatusService.getStatusByPhone(lightUser.getPhoneNumber()).getId());

            contacts.forEach((contact) -> {
                lstContacts.getItems().add(contact);
            });

            if (lightUser.getImage() != null) {
                profileImageView.setFill(new ImagePattern(new Image(new ByteArrayInputStream(lightUser.getImage()))));
            } else {
                profileImageView.setFill(new ImagePattern(new Image(new ByteArrayInputStream(defaultUserImage))));
            }
            lblUserName.setText(lightUser.getName());

            lstContacts.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
                @Override
                public ListCell<Contact> call(ListView<Contact> param) {
                    return new ContactCell();
                }
            });

            lstContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, currentContact) -> {

                Platform.runLater(() -> {
                    webView.getEngine().loadContent("");
                });

                htmlAll = "";
                if (currentContact instanceof FriendContact) {
                    currentSelectedFriendContact = (FriendContact) currentContact;
                    friendOrGroupContact = true;
                    fillChatArea(currentSelectedFriendContact.getPhoneNumber());
                    if (currentSelectedFriendContact.getNotified()) {
                        currentSelectedFriendContact.setNotified(false);
                        lstContacts.refresh();
                    }
                    if ((currentSelectedFriendContact).getStatus() == null) {
                        htmlEditor.setDisable(true);
                    } else {
                        htmlEditor.setDisable(false);
                    }
                } else {
                    currentSelectedGroupContact = (GroupContact) currentContact;
                    friendOrGroupContact = false;
                    if (currentSelectedGroupContact.getNotified()) {
                        currentSelectedGroupContact.setNotified(false);
                        lstContacts.refresh();
                    }
                    fillChatArea(String.valueOf(currentSelectedGroupContact.getGroupId()));
                    htmlEditor.setDisable(false);
                }
            });
            btnSaveChat.setOnAction((event) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select text file");
                File choosenFile = fileChooser.showSaveDialog(mainController.getPrimaryStage());

                if (choosenFile != null) {
                    ChatSessionToXML.ChatSessionToXML(currentChatSession, lightUser.getName(), choosenFile);
                }

            });

        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void statusComboOnAction(ActionEvent event) {
        try {
            User_Status status = statusCombo.getSelectionModel().getSelectedItem();
            UserStatusService userStatusService = (UserStatusService) ServiceLocator.getService("UserStatusService");
            userStatusService.changeStatus(lightUser.getPhoneNumber(), status.getId());
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void notificationsOnMouseClicked(MouseEvent event) {

    }

    @FXML
    private void chatBotCheckOnAction(ActionEvent event) {
        if (chatBotCheck.isSelected()) {
            chat = new EzzatChatBot();
        } else {
            chat = null;
        }
    }

    private String getPTagOnly(String html) {
        int indexOfPTagBegin = html.indexOf("<p>");
        int indexOfPTagEnd = html.indexOf("</p>");
        indexOfPTagEnd += 3;

        return html.substring(indexOfPTagBegin, indexOfPTagEnd + 1);
    }

    private void sendMessage() {
        if (friendOrGroupContact == null) {
            Alert receiveFileAlert = new Alert(Alert.AlertType.INFORMATION, "Please, select a friend");
            receiveFileAlert.showAndWait();
        } else if (friendOrGroupContact && !htmlEditor.getHtmlText().isEmpty()) {

            List<Message> previousMessagesList = createOrAppendChatSession(currentSelectedFriendContact.getPhoneNumber());

            String htmlEditorText = htmlEditor.getHtmlText();
            System.out.println(htmlEditorText);

            String messageTxt = getPTagOnly(htmlEditorText);
            System.out.println(messageTxt);

            if (lightUser.getImage() == null) {
                appendDivToWebView(messageTxt, defaultUserImage, true);
            } else {
                appendDivToWebView(messageTxt, lightUser.getImage(), true);
            }

            oneToOneMessage = new OneToOneMessage();
            oneToOneMessage.setMessage(messageTxt);
            oneToOneMessage.setSenderPhone(lightUser.getPhoneNumber());
            oneToOneMessage.setReceiverPhone(currentSelectedFriendContact.getPhoneNumber());

            previousMessagesList.add(oneToOneMessage);

            try {
                chatService.sendMessage(oneToOneMessage);

            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            List<Message> previousMessagesList = createOrAppendChatSession(String.valueOf(currentSelectedGroupContact.getGroupId()));

            String messageTxt = getPTagOnly(htmlEditor.getHtmlText().trim());
            System.out.println(messageTxt);

            if (lightUser.getImage() == null) {
                appendDivToWebView(messageTxt, defaultUserImage, true);
            } else {
                appendDivToWebView(messageTxt, lightUser.getImage(), true);
            }

            groupMessage = new GroupMessage();
            groupMessage.setMessage(messageTxt);
            groupMessage.setReceiverGroup(currentSelectedGroupContact.getGroupId());
            groupMessage.setSenderUser(lightUser);

            previousMessagesList.add(groupMessage);

            try {

                chatService.sendMessage(groupMessage);
            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        view.getEngine().executeScript("var list = document.getElementsByTagName(\"BODY\")[0];\n"
                + "            while (list.hasChildNodes()) {\n"
                + "               list.removeChild(list.firstChild);\n"
                + "            }");

        webEngine.executeScript("window.scrollTo(0, document.body.scrollHeight);");

    }

    @FXML
    private void btnBackAction(ActionEvent event) {
        try {
            mainController.chatController = null;
            mainController.switchToLoginScene();

            LoginLogoutService loginLogoutService = (LoginLogoutService) ServiceLocator.getService("LoginLogoutService");
            loginLogoutService.logout(loginUser);

        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void sendAttachementOnMouseClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File To Send");
        File choosenFile = fileChooser.showOpenDialog(mainController.getPrimaryStage());

        if (choosenFile != null) {
            ClientSendFileServiceP2P clientSendFile = (ClientSendFileServiceP2P) ServiceLocator.getService("SendFileService");
            SimpleRemoteInputStream remoteInputStream = null;
            if (clientSendFile != null) {
                try {
                    ReceiveFileService receiveFileService = clientSendFile.getReceiveFileService(currentSelectedFriendContact.getPhoneNumber(), lightUser.getName(), choosenFile.getName());
                    if (receiveFileService != null) {
                        String fileName = choosenFile.getName();
                        String extention = fileName.substring(fileName.lastIndexOf("."));
                        remoteInputStream = new SimpleRemoteInputStream(new FileInputStream(choosenFile));

                        receiveFileService.sendFile(remoteInputStream.export(), extention, fileName, choosenFile.length());
                    } else {
                        Alert receiveFileAlert = new Alert(Alert.AlertType.INFORMATION, "Your friend rejected to Receive!");
                        receiveFileAlert.showAndWait();
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (remoteInputStream != null) {
                        remoteInputStream.close();
                    }
                }
            }
        } else {
            //Should Handle Not Choosing any Files
        }
    }

    @FXML
    private void htmlEditorOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendMessage();
        }
    }

    void appendDivToWebView(String message, byte[] image, boolean asSender) {
        String base64Encoded = "";

        base64Encoded = Base64.getEncoder().encodeToString(image);

        String beginningTag = "";
        String imageTag = "";
        String restOfTags = "";
        if (asSender) {
            beginningTag = "<div class=\"container\" style = >\n";
            imageTag = "  <img class = \"right\" src=\"data:image/jpeg;base64,%1$s\" ";
            restOfTags = message + "\n"
                    + "  <span class=\"time-right\">11:00</span>\n"
                    + "</div>";
        } else {
            beginningTag = "<div class=\"container darker\" style = >\n";
            imageTag = "  <img src=\"data:image/jpeg;base64,%1$s\" ";
            restOfTags = message + "\n"
                    + "  <span class=\"time-left\">11:00</span>\n"
                    + "</div>";
        }

        String encodedBase64Tag = String.format(imageTag, base64Encoded);

        htmlAll += beginningTag + encodedBase64Tag + restOfTags;

        Platform.runLater(() -> {
            webEngine.loadContent(htmlAll);
        });
    }

    String extractTextFromHtml(String htmlText) {
        return htmlText.replaceAll("\\<.*?>", "");
    }

    public void receive(Message message) {
        Contact tempContact = null;
        List<Message> previousMessagesList;
        if (message instanceof OneToOneMessage) {
            oneToOneMessage = (OneToOneMessage) message;
            previousMessagesList = createOrAppendChatSession(oneToOneMessage.getSenderPhone());

            if (chatBotCheck.isSelected()) {
                lstContacts.getSelectionModel().select(getContactById(oneToOneMessage.getSenderPhone()));
                try {

                    previousMessagesList.add(oneToOneMessage);

                    String messageTxt = oneToOneMessage.getMessage();

                    if (currentSelectedFriendContact.getImage() == null) {
                        appendDivToWebView(messageTxt, defaultUserImage, false);

                    } else {
                        appendDivToWebView(messageTxt, currentSelectedFriendContact.getImage(), false);
                    }

                    String nohtml = extractTextFromHtml(messageTxt);
                    System.out.println(nohtml);

                    String chatBotResponse = chat.processUserInput(nohtml);
                    String htmlResponse = "<p>" + chatBotResponse + "</p>";

                    if (lightUser.getImage() == null) {
                        appendDivToWebView(htmlResponse, defaultUserImage, true);
                    } else {
                        appendDivToWebView(htmlResponse, lightUser.getImage(), true);
                    }

                    oneToOneMessage = new OneToOneMessage();
                    oneToOneMessage.setMessage(htmlResponse);
                    oneToOneMessage.setSenderPhone(lightUser.getPhoneNumber());
                    oneToOneMessage.setReceiverPhone(currentSelectedFriendContact.getPhoneNumber());

                    chatService.sendMessage(oneToOneMessage);

                    previousMessagesList.add(oneToOneMessage);
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (friendOrGroupContact != null && friendOrGroupContact) {

                    if (currentSelectedFriendContact.getPhoneNumber().equals(oneToOneMessage.getSenderPhone())) {

                        String messageTxt = oneToOneMessage.getMessage();
                        System.out.println(messageTxt);

                        if (currentSelectedFriendContact.getImage() == null) {
                            appendDivToWebView(messageTxt, defaultUserImage, false);

                        } else {
                            appendDivToWebView(messageTxt, currentSelectedFriendContact.getImage(), false);
                        }

                        previousMessagesList.add(oneToOneMessage);

                    } else {
                        FriendContact friendContact = (FriendContact) getContactById(oneToOneMessage.getSenderPhone());
                        friendContact.setNotified(true);
                        lstContacts.refresh();
                        previousMessagesList.add(oneToOneMessage);
                    }
                } else {
                    FriendContact friendContact = (FriendContact) getContactById(oneToOneMessage.getSenderPhone());
                    friendContact.setNotified(true);
                    lstContacts.refresh();
                    previousMessagesList.add(oneToOneMessage);
                }
            }
        } else {
            groupMessage = (GroupMessage) message;
            previousMessagesList = createOrAppendChatSession(String.valueOf(groupMessage.getReceiverGroup()));
            previousMessagesList.add(groupMessage);
            if (friendOrGroupContact != null && !friendOrGroupContact) {
                if (currentSelectedGroupContact.getGroupId() == groupMessage.getReceiverGroup()) {
                    if (groupMessage.getSenderUser().getImage() != null) {
                        appendDivToWebView(groupMessage.getMessage(), groupMessage.getSenderUser().getImage(), false);
                    } else {
                        appendDivToWebView(groupMessage.getMessage(), defaultUserImage, false);
                    }
                } else {
                    GroupContact groupContact = (GroupContact) getContactById(String.valueOf(groupMessage.getReceiverGroup()));
                    groupContact.setNotified(true);
                    lstContacts.refresh();
                }
            } else {
                GroupContact groupContact = (GroupContact) getContactById(String.valueOf(groupMessage.getReceiverGroup()));
                groupContact.setNotified(true);
                lstContacts.refresh();
            }

        }
    }

    private List<Message> createOrAppendChatSession(String sessionId) {
        currentChatSession = chatSessionsMap.get(sessionId);
        if (currentChatSession != null) {
            return currentChatSession.getMessagesList();
        } else {
            currentChatSession = new ChatSession();
            currentChatSession.setSessionId(sessionId);
            Contact tempContact = getContactById(sessionId);
            if (tempContact instanceof FriendContact) {
                currentChatSession.setOneOrGroupFlag(true);
                currentChatSession.setName(((FriendContact) tempContact).getName());
                currentChatSession.setPicture(((FriendContact) tempContact).getImage());
            } else {
                currentChatSession.setOneOrGroupFlag(false);
                currentChatSession.setName(((GroupContact) tempContact).getName());
                currentChatSession.setPicture(((GroupContact) tempContact).getImage());
            }
            currentChatSession.setMessagesList(new ArrayList<>());
            chatSessionsMap.put(sessionId, currentChatSession);
            return currentChatSession.getMessagesList();
        }
    }

    private void fillChatArea(String sessionId) {
        ChatSession chatSession = chatSessionsMap.get(sessionId);

        htmlAll = "";
        if (chatSession != null) {
            List<Message> previousMessagesList = chatSession.getMessagesList();
            if (!previousMessagesList.isEmpty()) {
                if (previousMessagesList.get(0) instanceof OneToOneMessage) {
                    previousMessagesList.forEach((message) -> {
                        oneToOneMessage = (OneToOneMessage) message;
                        if (oneToOneMessage.getSenderPhone().equals(lightUser.getPhoneNumber())) {

                            if (lightUser.getImage() != null) {
                                appendDivToWebView(oneToOneMessage.getMessage(), lightUser.getImage(), true);
                            } else {
                                appendDivToWebView(oneToOneMessage.getMessage(), defaultUserImage, true);
                            }

                        } else {

                            if (currentSelectedFriendContact.getImage() != null) {
                                appendDivToWebView(oneToOneMessage.getMessage(), currentSelectedFriendContact.getImage(), false);
                            } else {
                                appendDivToWebView(oneToOneMessage.getMessage(), defaultUserImage, false);
                            }
                        }
                    });
                } else {

                    previousMessagesList.forEach((message) -> {
                        groupMessage = (GroupMessage) message;
                        if (groupMessage.getSenderUser().getPhoneNumber().equals(lightUser.getPhoneNumber())) {

                            if (lightUser.getImage() != null) {
                                appendDivToWebView(groupMessage.getMessage(), lightUser.getImage(), true);
                            } else {
                                appendDivToWebView(groupMessage.getMessage(), defaultUserImage, true);
                            }
                        } else {

                            if (groupMessage.getSenderUser().getImage() != null) {
                                appendDivToWebView(groupMessage.getMessage(), groupMessage.getSenderUser().getImage(), false);
                            } else {
                                appendDivToWebView(groupMessage.getMessage(), defaultUserImage, false);
                            }
                        }
                    });
                }
                Platform.runLater(() -> {
                    webEngine.loadContent(htmlAll);
                });
            }

        }
    }

    private Contact getContactById(String id) {
        Contact tempContact = null;
        for (Contact item : lstContacts.getItems()) {
            if (item instanceof FriendContact) {
                if (((FriendContact) item).getPhoneNumber().equals(id)) {
                    tempContact = item;
                    break;
                }
            } else {
                if (((GroupContact) item).getGroupId() == Integer.parseInt(id)) {
                    tempContact = item;
                    break;
                }
            }
        }
        return tempContact;
    }

    public void handleNotification(Notification notification) {
        if (notification instanceof ContactNotification) {
            ContactNotification contactNotification = (ContactNotification) notification;
            Contact contact = getContactById(contactNotification.getContactPhoneNumber());
            if (contact != null) {
                FriendContact friendContact = (FriendContact) contact;

                friendContact.setStatusDescription(contactNotification.getNewStatus());
                Platform.runLater(() -> {
                    Notifications.create()
                            .title("Chat App")
                            .text("Your Friend " + contactNotification.getContactName()
                                    + " is " + contactNotification.getNewStatus() + "!")
                            .showInformation();
                });

                if (contactNotification.getStatusId() != null) {
                    onlineSound.play();
                    friendContact.setStatus(contactNotification.getStatusId());
                    lstContacts.refresh();
                } else {
                    offlineSound.play();
                    friendContact.setStatus(null);
                    lstContacts.refresh();
                }
            }
        }
    }

    private List<GroupMember> mapToGroupMember() {
        List<GroupMember> membersList = new ArrayList<>();
        lstContacts.getItems().forEach((contact) -> {
            if (contact instanceof FriendContact) {
                FriendContact friendContact = (FriendContact) contact;

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

    public ReceiveFileService showReceiveFileRequest(String senderName, String fileName) {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Alert receiveFileAlert = new Alert(Alert.AlertType.CONFIRMATION, "Your friend " + senderName + " wants to Send"
                    + fileName + " \nDo you want to Accept?");
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

    public void receiveFile(RemoteInputStream remoteInputStream, String ext, String fileName, double fileLength) {

        InputStream istream = null;
        try {

            final CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select File To Send");
                fileChooser.setInitialFileName(fileName);
                choosenFile = fileChooser.showSaveDialog(mainController.getPrimaryStage());
                latch.countDown();
            });

            try {
                latch.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (choosenFile != null) {
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

    @FXML
    private void editProfileOnMouseClicked(MouseEvent event) {
        User updateUser = new User();
        updateUser.setName(lightUser.getName());
        updateUser.setPhoneNumber(lightUser.getPhoneNumber());
        updateUser.setPicture(lightUser.getImage());
        updateUser.setPassword(loginUser.getPassword());
        
        mainController.switchToUpdateProfileScene(updateUser);
    }

    @FXML
    private void logoutIconOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void btnFriendRequestMouseClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            SendFriendRequestController contactController = new SendFriendRequestController(this, loginUser);
            loader.setController(contactController);
            Parent root = loader.load(getClass().getResource("/SendFriendRequestDesign.fxml").openStream());

            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(mainController.getPrimaryStage().getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.showAndWait();
            });
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAddGroupMouseClicked(MouseEvent event) {
        Stage addNewGroupStage = new Stage();
        List<GroupMember> groupMembers = mapToGroupMember();
        GroupMember groupMember = new GroupMember();

        try {
            FXMLLoader loader = new FXMLLoader();
            AddNewGroupController addNewGroupController = new AddNewGroupController(groupMembers, addNewGroupStage, loginUser.getPhoneNumber());
            loader.setController(addNewGroupController);
            Parent root = loader.load(getClass().getResource("/AddNewGroupDesign.fxml").openStream());

            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(mainController.getPrimaryStage().getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.showAndWait();
            });
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnHideOnAction(ActionEvent event) {

    }
    
    public void receiveAnnouncement(Notification notification) {
        AnnounceNotification announceNotification = (AnnounceNotification)notification;
                Platform.runLater(() -> {
                    Notifications.create()
                        .title("Chat App")
                        .text("Server Says:\n" + announceNotification.getMessage())
                        .showInformation();
                });
                offlineSound.play();
}
}
