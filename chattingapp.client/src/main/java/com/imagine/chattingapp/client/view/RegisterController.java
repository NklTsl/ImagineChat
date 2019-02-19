/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.control.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class RegisterController implements Initializable {

    @FXML
    private Label welcomeMessage;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPicture;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblConfirmPassword;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblBirthDate;
    @FXML
    private Label lblBio;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCountry;
    @FXML
    private DatePicker pickerBirthDate;
    @FXML
    private TextField txtBio;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private ComboBox<?> comboGender;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtPhotoPath;

    
    private RegisterService registerService;
    private MainController mainController;
    User user;
           

    public RegisterController(MainController mainController) {
        this.mainController = mainController;
    }

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnRegisterOnAction(ActionEvent event) {
        try {
            registerService = (RegisterService)ServiceLocator.getService("Register");
            
            user = new User();
            user.setPhoneNumber("1");
            user.setName("Mahmoud");
            user.setEmail("mnet30@yahoo.com");
            user.setPicture(new byte[5]);
            user.setPassword("Mahmoud");
            user.setDateOfBirth(new Date(1990, 1, 1).getTime());
            user.setGender(true);
            user.setBiography("ITI");
            user.setCountryID((byte)1);
            user.setStatusID((byte)1);
            
            
            registerService.register(user);
            
        } catch (RemoteException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        
    }

    public Scene getRegisterScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
