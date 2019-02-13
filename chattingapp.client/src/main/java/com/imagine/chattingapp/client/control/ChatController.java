/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.control;

import com.imagine.chattingapp.client.dal.ServerService;
import com.imagine.chattingapp.client.view.ChatWindow;
import com.imagine.chattingapp.client.view.LoginWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;

/**
 *
 * @author Mahmoud Shereif
 */
public class ChatController {
    Scene chatScene;
    ChatWindow chatWindow;
    MainController mainController;
    ServerService serverService;
    ChatController(MainController mainController, ServerService serverService) {
        chatWindow = new ChatWindow(this);
        chatScene = new Scene(chatWindow, 600, 600);
        this.serverService = serverService;
        this.mainController = mainController;
    }

    Scene getChatScene() {
        return chatScene;
    }

    public void sendOneToOneMessage(String message, String phoneNumber) {
        try {
            serverService.oneToOneMessage(message, phoneNumber);
        } catch (RemoteException ex) {
            //TODO popupWindow to tell the user that the message failed to sent
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregister() {
        mainController.logout();
    }

    public void switchToLoginScene() {
        mainController.switchToLoginScene();
    }

    public void receiveMessage(String message) {
        chatWindow.appendMessage(message);
    }
    
}
