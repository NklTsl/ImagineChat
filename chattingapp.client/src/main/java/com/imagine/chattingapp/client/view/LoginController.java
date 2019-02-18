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
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.LoginUser;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import com.imagine.chattingapp.common.serverservices.LoginLogoutService;

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
    @FXML
    private Text lblWrong;
    @FXML
    private Button btnRegister;
    
    private MainController mainController;
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
        lblWrong.setFill(Color.RED);
    }    

    @FXML
    private void btnLoginAction(ActionEvent event) {
        String phone = txtPhone.getText();
        String password = txtPassword.getText(); 
        if(phone.isEmpty() || password.isEmpty())
        {
            lblWrong.setText("Empty user name or password");
            
        }
        else if(Validation.validatePhone(phone))
        {
            
            try {
                loginUser = new LoginUser();
                loginUser.setPhoneNumber(phone);
                loginUser.setPassword(password);
                
                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
                LoginLogoutService loginService = (LoginLogoutService) registry.lookup("LoginLogoutService");
                LightUser lightUser = loginService.login(loginUser, mainController.getClientService());
                
                if(lightUser != null)
                {
                    mainController.loginUser = loginUser;
                    mainController.switchToChatScene(lightUser, loginUser);
                }
                else
                {
                    lblWrong.setText("Wrong user name or password");
                }
                
            } catch (RemoteException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION,"Server Not Connected");
                successAlert.showAndWait();
            } catch (NotBoundException ex) {
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
    @FXML
    private void btnRegisterAction(ActionEvent event) {
        try {
            mainController.switchToRegisterScene();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
