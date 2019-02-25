package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.client.view.UpdateProfileController;
import com.imagine.chattingapp.client.view.ChatController;
import com.imagine.chattingapp.client.view.LoginController;
import com.imagine.chattingapp.client.view.RegisterController;
import com.imagine.chattingapp.client.view.RegisterWindow;
import com.imagine.chattingapp.client.view.SendFriendRequestController;
import com.imagine.chattingapp.client.view.UpdateProfileWindow;
import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.LoginLogoutService;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController extends Application {

    ClientServiceImpl clientService = null;

    LoginController loginController = null;
    public ChatController chatController = null;
    RegisterController registerController = null;
    UpdateProfileController updateProfileController = null;
    public LoginUser loginUser;

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
        primaryStage.setOnCloseRequest((event) -> {
            try {
                if (chatController != null) {
                    Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
                    LoginLogoutService loginService = (LoginLogoutService) registry.lookup("LoginLogoutService");
                    loginService.logout(loginUser);
                }

                System.exit(0);
            } catch (RemoteException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @Override
    public void stop() {
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

    public void switchToChatScene(LightUser lightUser, LoginUser loginUser) {
        try {

            FXMLLoader loader = new FXMLLoader();
            chatController = new ChatController(this, lightUser, loginUser);
            loader.setController(chatController);
            Parent root = loader.load(getClass().getResource("/ChatDesignFinal.fxml").openStream());
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
            registerController = new RegisterController(this, primaryStage);
            loader.setController(registerController);
            Parent root = loader.load(getClass().getResource("/RegisterDesign.fxml").openStream());

            Scene scene = new Scene(root);
            root.getStylesheets().add("/regCss.css");

            primaryStage.setTitle("Register");
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

    public ClientServiceImpl getClientService() {
        return clientService;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
}
