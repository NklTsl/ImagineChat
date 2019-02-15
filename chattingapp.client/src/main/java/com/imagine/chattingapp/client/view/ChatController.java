package com.imagine.chattingapp.client.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.imagine.chattingapp.client.control.MainController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class ChatController implements Initializable {

    @FXML
    private ListView<?> lstContacts;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtMessage;

    public ChatController(MainController aThis) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnBackAction(ActionEvent event) {
    }

    @FXML
    private void txtMessageKeyPressed(KeyEvent event) {
    }
    
}
