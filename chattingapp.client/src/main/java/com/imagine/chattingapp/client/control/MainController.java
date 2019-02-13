package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.client.dal.ClientService;
import com.imagine.chattingapp.client.dal.ServerService;
import com.imagine.chattingapp.client.dal.entity.User;
import com.imagine.chattingapp.client.view.LoginWindow;
import com.imagine.chattingapp.client.view.ChatWindow;
import com.imagine.chattingapp.client.view.RegisterWindow;
import com.imagine.chattingapp.client.view.UpdateProfileWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {

    ServerService serverService;
    ClientService clientService;
    
    LoginController loginController = null;
    ChatController chatController = null;
    RegisterController registerController = null;
    UpdateProfileController updateProfileController = null;
    
    
    Scene loginScene = null;
    Scene chatScene = null;
    Scene registerScene = null;
    Scene updateProfileScene = null;
    
    Stage primaryStage = null;
    
    public MainController() {
//        serverService = new ServerService();
//        clientService = new ClientService();
        
        loginController = new LoginController(this, serverService);
        loginScene = loginController.getLoginScene();
        
        chatController = new ChatController(this, serverService);
        chatScene = chatController.getChatScene();      
        
        registerController = new RegisterController(this, serverService);
        registerScene = registerController.getRegisterScene();
        
        updateProfileController = new UpdateProfileController(this, serverService);
        updateProfileScene = updateProfileController.getUpdateProfileScene();
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        

    }

    public static void main(String[] args) {
        launch(args);
    }

    void login() {
//        try {
//            serverService.login(clientService);
//        } catch (RemoteException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    void logout() {
//        try {
//            serverService.logout(clientService);
//        } catch (RemoteException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    void switchToLoginScene() {
        primaryStage.setScene(loginScene);
    }
    void switchToChatScene() {
        primaryStage.setScene(chatScene);
    }
    void switchToRegisterScene() {
        primaryStage.setScene(registerScene);
    }
    
    void switchToUpdateProfileScene() {
        primaryStage.setScene(updateProfileScene);
    }
}
