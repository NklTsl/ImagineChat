package com.imagine.chattingapp.client.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.imagine.chattingapp.client.control.MainController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
    Image addContactImg ;
    Image notificationImg ;
    Image logoutImg ;
    Image updateProfileImg;
    Image attachementImg ;
    Image sendImg ;
    
    MainController mainController = null;
    
    public ChatController(MainController mainController){
    
        this.mainController = mainController;
       
        
        
    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
//         addContactImg = new Image(getClass().getResourceAsStream("/employee.png"));
//        //addContactlbl= new Label();
//        addContactlbl.setGraphic(new ImageView(addContactImg));
    }    
    
}
