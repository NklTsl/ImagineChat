/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.client.dal.ServerService;
import com.imagine.chattingapp.client.view.LoginWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;

/**
 *
 * @author Mahmoud Shereif
 */
public class LoginController {

    Scene loginScene;
    LoginWindow loginWindow;
    MainController mainController;
    ServerService serverService;
    LoginController(MainController mainController, ServerService serverService) {
        loginWindow = new LoginWindow(this);
        loginScene = new Scene(loginWindow, 600, 600);
        this.mainController = mainController;
        this.serverService = serverService;
    }

    Scene getLoginScene() {
        return loginScene;
    }

    public void login(String phoneNumber, String password) {
        try {
            serverService.login(phoneNumber, password);
        } catch (RemoteException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
