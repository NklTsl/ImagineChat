package com.imagine.chattingapp.server.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.imagine.chattingapp.server.control.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

public class AnnouncementController implements Initializable {
    
    @FXML
    private JFXTextField txtMessage;
    @FXML
    private JFXButton btnSend;
    private MainController mainController;
            
    public AnnouncementController(MainController mainController){
        this.mainController = mainController;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtMessage.setOnKeyPressed((event)->{
            if(event.getCode().equals(KeyCode.ENTER)){
                SendMessage();
            }
        });
        btnSend.setOnAction((event)->{
           SendMessage();
        });
    }    
    private void SendMessage(){
         if(!txtMessage.getText().isEmpty()){
                mainController.SendAnnouncement(txtMessage.getText());
                txtMessage.clear();
            }else{
                Alert emptyAlert = new Alert(Alert.AlertType.INFORMATION, "Enter Your Announcement!");
                emptyAlert.showAndWait();    
            }
    }
}
