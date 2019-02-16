package com.imagine.chattingapp.client.control;


import com.imagine.chattingapp.client.view.ChatController;
import com.imagine.chattingapp.client.view.LoginController;
import com.imagine.chattingapp.client.view.RegisterController;
import com.imagine.chattingapp.client.view.RegisterWindow;
import com.imagine.chattingapp.client.view.UpdateProfileWindow;
import com.imagine.chattingapp.common.customobj.Contact;
import com.imagine.chattingapp.common.customobj.FriendContact;
import com.imagine.chattingapp.common.customobj.LightUser;
import com.imagine.chattingapp.common.entity.User;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {
    
    
    ClientServiceImpl clientService = null;
    
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
        try {
            clientService = new ClientServiceImpl(this);
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        switchToLoginScene();
        primaryStage.show();
        
        
    }
    @Override
    public void stop(){
        Platform.exit();
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
    
    public void switchToLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loginController = new LoginController(this);
            loader.setController(loginController);
            Parent root = loader.load(getClass().getResource("/LoginDesign.fxml").openStream());
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToChatScene(LightUser lightUser) {
        try {
            
            FXMLLoader loader = new FXMLLoader();
            chatController = new ChatController(this, lightUser);
            loader.setController(chatController);
            Parent root = loader.load(getClass().getResource("/ChatDesign.fxml").openStream());
            
            Platform.runLater(() -> {
                chatScene = new Scene(root);
                primaryStage.setTitle("Chat");
                primaryStage.setScene(chatScene);
            });
            

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToRegisterScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loginController = new LoginController(this);
            loader.setController(loginController);
            Parent root = loader.load(getClass().getResource("/LoginDesign.fxml").openStream());
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void switchToUpdateProfileScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loginController = new LoginController(this);
            loader.setController(loginController);
            Parent root = loader.load(getClass().getResource("/LoginDesign.fxml").openStream());
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ClientServiceImpl getClientService(){
        return clientService;
    }
    public ChatController getChatController()
    {
        return chatController;
    }
}
