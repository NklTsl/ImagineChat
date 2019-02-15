/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.ClientServiceImpl;
import com.imagine.chattingapp.common.validation.Validation;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.serverservices.LoginService;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LoginController implements Initializable {

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
    
    private MainController mainController;
    private ClientService clientService;
    private LoginUser loginUser;

    public LoginController(MainController mainController) {
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
    private void btnLoginAction(ActionEvent event) {
        String phone = txtPhone.getText();
        String password = txtPassword.getText(); 
        if(phone.isEmpty() || password.isEmpty())
        {
            
        }
        else if(Validation.validatePhone(phone) && Validation.validatePassword(password))
        {
            try {
                clientService = new ClientServiceImpl();
                loginUser = new LoginUser();
                loginUser.setPhoneNumber(phone);
                loginUser.setPassword(password);
                
                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
                LoginService loginService = (LoginService) registry.lookup("LoginService");
                loginService.login(loginUser, clientService);
                        } catch (RemoteException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AccessException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        try {
            mainController.stop();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
