/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.Model.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.client.control.ClientServiceImpl;
import com.imagine.chattingapp.common.validation.Validation;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.serverservices.ContactsService;
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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private Label lblWrong;
    @FXML
    private Hyperlink lnkRegister;

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
        FileInputStream fileInStream = null;
        try {
            // TODO
            fileInStream = new FileInputStream("remember.properties");
            Properties currentConnectionProperties = new Properties();
            currentConnectionProperties.load(fileInStream);
            if (!currentConnectionProperties.isEmpty()) {
                txtPhone.setText(currentConnectionProperties.getProperty("phoneNumber"));
                
                if (Integer.parseInt(currentConnectionProperties.getProperty("flag")) != 0) {
                    txtPassword.setText(currentConnectionProperties.getProperty("password"));
                    btnLogin.fire();
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileInStream.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnLoginAction(ActionEvent event) {
        String phone = txtPhone.getText();
        String password = txtPassword.getText();
        if (phone.isEmpty() || password.isEmpty()) {
            lblWrong.setText("Empty user name or password");

        } else if (Validation.validatePhone(phone)) {

            try {
                loginUser = new LoginUser();
                loginUser.setPhoneNumber(phone);
                loginUser.setPassword(password);

                LoginLogoutService loginLogoutService = (LoginLogoutService) ServiceLocator.getService("LoginLogoutService");
                
                LightUser lightUser = loginLogoutService.login(loginUser, mainController.getClientService());

                if (lightUser != null) {
                    mainController.loginUser = loginUser;
                    mainController.switchToChatScene(lightUser, loginUser);
                    
                } else {
                    lblWrong.setText("Wrong user name or password");
                }

            } catch (RemoteException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Server Not Connected");
                successAlert.showAndWait();
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
    private void lnkRegisterAction(ActionEvent event) {
        try {
            mainController.switchToRegisterScene();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
