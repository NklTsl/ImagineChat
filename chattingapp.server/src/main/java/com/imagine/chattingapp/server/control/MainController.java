/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.control;

import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.AnnounceNotification;
import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.ContactNotification;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.entity.User_Status;
import com.imagine.chattingapp.common.serverservices.UpdateProfileService;
import com.imagine.chattingapp.server.dal.dao.ContactsDAO;
import com.imagine.chattingapp.server.dal.dao.UserDAO;
import com.imagine.chattingapp.server.dal.dao.User_StatusDAO;
import com.imagine.chattingapp.server.view.RegisterController;
import com.imagine.chattingapp.server.view.ServerDesignViewController;
import com.imagine.chattingapp.server.view.StatisticsViewController;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Mahmoud Shereif
 */
public class MainController extends Application {

    StatisticsViewController statisticsViewController;
    ServerDesignViewController mainViewController;
    private Registry registry;
    private LoginServiceImpl loginServiceImpl;
    private ContactsServiceImpl contactsServiceImpl;
    private ChatServiceImpl chatServiceImpl;
    private RegisterServiceImpl registerServiceImpl;
    private RegisterController registerController;
    private AddNewGroupServiceImpl addNewGroupServiceImpl;
    private FriendRequestImpl sendFriendRequest;
    private ClientSendFileServiceP2PImpl clientSendFileServiceP2PImpl;
    private GetNameByPhoneServiceImpl getNameByPhoneServiceImpl;
    private UserStatusServiceImpl userStatusServiceImpl;  
    private UpdateProfileServiceImpl updateProfileServiceImpl;
    private Stage primaryStage;
    private static Map<String, ClientService> onlineClients = new TreeMap<>();
    public static Session session;

    public static void main(String[] args) {
        launch(args);

    }

    public static void registerClient(String phoneNumber, ClientService clientService) {
        try {
            MainController.onlineClients.put(phoneNumber, clientService);

            ContactNotification contactNotification = new ContactNotification();
            UserDAO userDAO = new UserDAO();
            List<Object> primaryKeys = new ArrayList<>();
            primaryKeys.add(phoneNumber);

            User user = userDAO.getByPrimaryKey(primaryKeys);

            contactNotification.setContactPhoneNumber(phoneNumber);
            contactNotification.setNewStatus("Online");
            contactNotification.setStatusId((byte) 0);
            contactNotification.setContactName(user.getName());

            ContactsDAO contactsDao = new ContactsDAO();
            List<Contact> contactsList = contactsDao.getFriendContacts(phoneNumber);

            contactsList.forEach((contact) -> {
                ClientService clientServiceNotify = onlineClients.get(((FriendContact) contact).getPhoneNumber());
                if (clientServiceNotify != null) {
                    try {
                        clientServiceNotify.notify(contactNotification);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void unregisterClient(String phoneNumber) {
        try {
            MainController.onlineClients.remove(phoneNumber);
            ContactNotification contactNotification = new ContactNotification();
            UserDAO userDAO = new UserDAO();
            List<Object> primaryKeys = new ArrayList<>();
            primaryKeys.add(phoneNumber);

            User user = userDAO.getByPrimaryKey(primaryKeys);

            contactNotification.setContactPhoneNumber(phoneNumber);
            contactNotification.setNewStatus("Offline");
            contactNotification.setStatusId((byte) 1);
            contactNotification.setContactName(user.getName());

            ContactsDAO contactsDao = new ContactsDAO();
            List<Contact> contactsList = contactsDao.getFriendContacts(phoneNumber);

            contactsList.forEach((contact) -> {
                ClientService clientService = onlineClients.get(((FriendContact) contact).getPhoneNumber());
                if (clientService != null) {
                    try {
                        clientService.notify(contactNotification);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ClientService getClientService(String phoneNumber) {
        return onlineClients.get(phoneNumber);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);
        
        SessionFactory sessionFactory = new Configuration().configure(getClass().getResource("/cfg/hibernate.cfg.xml")).buildSessionFactory();
        session = sessionFactory.openSession();
        
        
        
        try {
            loginServiceImpl = new LoginServiceImpl();
            contactsServiceImpl = new ContactsServiceImpl();
            chatServiceImpl = new ChatServiceImpl();
            registerServiceImpl = new RegisterServiceImpl();
            addNewGroupServiceImpl = new AddNewGroupServiceImpl();
            sendFriendRequest = new FriendRequestImpl();
            clientSendFileServiceP2PImpl = new ClientSendFileServiceP2PImpl();
            getNameByPhoneServiceImpl = new GetNameByPhoneServiceImpl();
            userStatusServiceImpl = new UserStatusServiceImpl();
            updateProfileServiceImpl = new UpdateProfileServiceImpl();
            
            
            registry = LocateRegistry.createRegistry(2000);
            registry.rebind("LoginLogoutService", loginServiceImpl);
            registry.rebind("ContactsService", contactsServiceImpl);
            registry.rebind("ChatService", chatServiceImpl);
            registry.rebind("RegisterService", registerServiceImpl);
            registry.rebind("AddNewGroupService", addNewGroupServiceImpl);
            registry.rebind("AddContactService", sendFriendRequest);
            registry.rebind("SendFileService", clientSendFileServiceP2PImpl);
            registry.rebind("GetNameByPhoneService", getNameByPhoneServiceImpl);
            registry.rebind("UserStatusService", userStatusServiceImpl);
            registry.rebind("UpdateProfileService", updateProfileServiceImpl);
            
            
            
            System.out.println("Server Initially Started");
            switchToMainView();

            primaryStage.setOnCloseRequest((event) -> {
                System.exit(0);
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public void startService() {
        try {
            registry = LocateRegistry.getRegistry(2000);
            registry.rebind("LoginLogoutService", loginServiceImpl);
            registry.rebind("ContactsService", contactsServiceImpl);
            registry.rebind("ChatService", chatServiceImpl);
            registry.rebind("RegisterService", registerServiceImpl);
            System.out.println("Server  Started Again");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerDesignViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void stopService() {
        try {
            registry = LocateRegistry.getRegistry(2000);
            registry.unbind("LoginLogoutService");
            registry.unbind("ContactsService");
            registry.unbind("ChatService");
            registry.unbind("RegisterService");
            System.out.println("Server  Stopped");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerDesignViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerDesignViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToRegisterScence() {
        try {
            FXMLLoader loader = new FXMLLoader();
            registerController = new RegisterController(this, primaryStage);
            loader.setController(registerController);
            Parent root = loader.load(getClass().getResource("/RegisterDesign.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("Register New User");
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void switchToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            statisticsViewController = new StatisticsViewController(this);
            loader.setController(statisticsViewController);
            Parent root = loader.load(getClass().getResource("/StatisticsView.fxml").openStream());
            Scene scene = new Scene(root);

            primaryStage.setTitle("Server");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void SendAnnouncement(String message) {
        onlineClients.forEach((clientPhone, clientService) -> {
                AnnounceNotification announceNotification = new AnnounceNotification();
                announceNotification.setMessage(message);
                try{
                    clientService.receiveAnnounce(announceNotification);
                }catch(RemoteException remoteException){
                    remoteException.printStackTrace();
                }
        });

    }

    static void notifiyStatusChange(String phoneNumber, byte newStatus) {
        try {
            ContactNotification contactNotification = new ContactNotification();
            UserDAO userDAO = new UserDAO();
            List<Object> primaryKeys = new ArrayList<>();
            primaryKeys.add(phoneNumber);

            User_StatusDAO statusDAO = new User_StatusDAO();
            List<Object> primaryKeysStatus = new ArrayList<>();
            primaryKeysStatus.add(newStatus);
            User_Status user_Status = statusDAO.getByPrimaryKey(primaryKeysStatus);

            User user = userDAO.getByPrimaryKey(primaryKeys);

            contactNotification.setContactPhoneNumber(phoneNumber);
            contactNotification.setNewStatus(user_Status.getDescription());
            contactNotification.setStatusId(newStatus);
            contactNotification.setContactName(user.getName());

            ContactsDAO contactsDao = new ContactsDAO();
            List<Contact> contactsList = contactsDao.getFriendContacts(phoneNumber);

            contactsList.forEach((contact) -> {
                ClientService clientService = onlineClients.get(((FriendContact) contact).getPhoneNumber());
                if (clientService != null) {
                    try {
                        clientService.notify(contactNotification);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
