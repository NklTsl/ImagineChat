package com.imagine.chattingapp.client.control;


import com.imagine.chattingapp.client.view.LoginController;
import com.imagine.chattingapp.client.view.RegisterController;
import com.imagine.chattingapp.client.view.RegisterWindow;
import com.imagine.chattingapp.client.view.UpdateProfileWindow;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {
    
    LoginController loginController = null;
    //ChatController chatController = null;
    RegisterController registerController = null;
    UpdateProfileController updateProfileController = null;
    
    
    Scene loginScene = null;
    Scene chatScene = null;
    Scene registerScene = null;
    Scene updateProfileScene = null;
    
    Stage primaryStage = null;
    
    public MainController() {
        
//        loginController = new LoginController(this);
//        loginScene = loginController.getLoginScene();
//        
//        chatController = new ChatController(this);
//        chatScene = chatController.getChatScene();
//        
//        registerController = new RegisterController(this);
//        registerScene = registerController.getRegisterScene();
//        
//        updateProfileController = new UpdateProfileController(this);
//        updateProfileScene = updateProfileController.getUpdateProfileScene();
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        switchToLoginScene();
        primaryStage.show();
        
        
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
    void switchToChatScene() {
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
    void switchToRegisterScene() {
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
    
    void switchToUpdateProfileScene() {
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
}
