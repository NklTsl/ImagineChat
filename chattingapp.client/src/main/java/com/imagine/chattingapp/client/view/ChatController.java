package com.imagine.chattingapp.client.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.imagine.chattingapp.client.control.MainController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Menna Helmy
 */
public class ChatController implements Initializable {

    @FXML
    private Label addContactlbl;
    @FXML
    private Label logoutLbl;
    @FXML
    private Label notificationsLbl;
    @FXML
    private Label updateProfileLbl;
    @FXML
    private Label btnBack;
    @FXML
    private TreeView<?> contactsTreeView;
    @FXML
    private TextArea msgTextArea;
    @FXML
    private ScrollPane msgScrollPane;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label attachmentLbl;
    @FXML
    private Label sendLbl;
    Image addContactImg;
    Image notificationImg;
    Image logoutImg;
    Image updateProfileImg;
    Image attachementImg;
    Image sendImg;

    MainController mainController = null;

    public ChatController(MainController mainController) {

        this.mainController = mainController;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setBackLabelImage();
        setAddContactLabelImage();
        setAttachmentLabelImage();
        setNotificationLabelImage();
        setSendLabelImage();
        setlogoutLabelImage();
        setUpdateProfileLabelImage();
        
    }
    @FXML
    public void viewAddContactWindow(){
    
        
        mainController.switchToAddContactPopUp();
    }
    private void setBackLabelImage(){
        
        ImageView image;
        try {
            image = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\left-arrow.png")));
            image.setFitHeight(20);
            image.setFitWidth(20);
            image.setPreserveRatio(true);
            btnBack.setGraphic(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        }
    
    private void setAddContactLabelImage(){
    
        ImageView image;
        try {
            image = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\employee.png")));
            image.setFitHeight(20);
            image.setFitWidth(20);
            addContactlbl.setGraphic(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setlogoutLabelImage(){
    
        try {
            ImageView image = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\exit.png")));
            image.setFitHeight(20);
            image.setFitWidth(20);
            logoutLbl.setGraphic(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setUpdateProfileLabelImage(){
    
        try {
            ImageView image = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\edit.png")));
            image.setFitHeight(20);
            image.setFitWidth(20);
            updateProfileLbl.setGraphic(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setSendLabelImage(){
    
        try {
            ImageView imageSend = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\paper-plane.png")));
            imageSend.setFitHeight(20);
            imageSend.setFitWidth(20);
            sendLbl.setGraphic(imageSend);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setNotificationLabelImage(){
    try {
            ImageView image = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\bell.png")));
            image.setFitHeight(20);
            image.setFitWidth(20);
            notificationsLbl.setGraphic(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAttachmentLabelImage(){
    
        try {
 
            ImageView imageAttach = new ImageView(
                    new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\attachment.png")));
            imageAttach.setFitHeight(20);
            imageAttach.setFitWidth(20);
            attachmentLbl.setGraphic(imageAttach);
  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void viewAddHintMsg(){
        addContactlbl.setTooltip(new Tooltip("Add a new Friend"));
    }
    @FXML
    public void viewBackHint(){
        btnBack.setTooltip(new Tooltip("Back"));
    }
    @FXML
    public void viewNotifHint(){
        notificationsLbl.setTooltip(new Tooltip("Notifications"));
    }
    @FXML
    public void viewUpdateHint(){
        updateProfileLbl.setTooltip(new Tooltip("Update Profile"));
    }
    @FXML
    public void viewSendHint(){
        sendLbl.setTooltip(new Tooltip("Send Text message"));
    }
    @FXML
    public void viewAttachHint(){
        attachmentLbl.setTooltip(new Tooltip("Send an Attachment"));
    }
    @FXML
    public void viewLogoutHint(){
        logoutLbl.setTooltip(new Tooltip("Logout"));
    }
}
