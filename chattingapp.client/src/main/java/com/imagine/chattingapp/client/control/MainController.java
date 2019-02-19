package com.imagine.chattingapp.client.control;


import com.imagine.chattingapp.client.view.AddContactController;
import com.imagine.chattingapp.client.view.ChatController;
import com.imagine.chattingapp.client.view.LoginController;
import com.imagine.chattingapp.client.view.RegisterController;
import com.imagine.chattingapp.client.view.RegisterWindow;
import com.imagine.chattingapp.client.view.UpdateProfileWindow;
import com.imagine.chattingapp.common.entity.User;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MainController extends Application {
    
    LoginController loginController = null;
    ChatController chatController = null;
    RegisterController registerController = null;
    UpdateProfileController updateProfileController = null;
    
    
    Scene loginScene = null;
    Scene chatScene = null;
    Scene registerScene = null;
    Scene updateProfileScene = null;
    
    public Stage primaryStage = null;
    
    
    
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
    
   
    
    void switchToLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loginController = new LoginController(this);
            loader.setController(loginController);
            Parent root = loader.load(getClass().getResource("/LoginDesign.fxml").openStream());
            
            Scene scene = new Scene(root);
            this.primaryStage.setMaximized(false);
            
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void switchToChatScene(User user) {
        try {
            
            FXMLLoader loader = new FXMLLoader();
            chatController = new ChatController(this);
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

    public void switchToAddContactPopUp() {
        try {
            FXMLLoader loader = new FXMLLoader();
            AddContactController contactController = new AddContactController(this);
            loader.setController(contactController);
            Parent root = loader.load(getClass().getResource("/AddContactDesign.fxml").openStream());
            
            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(primaryStage.getScene().getWindow());
                Scene scene =  new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    
}
