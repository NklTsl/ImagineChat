/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class LoginDesignController implements Initializable {

    @FXML
    private Label welcomeMessage;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnLoginAction(ActionEvent event) {
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
    }
    
}
