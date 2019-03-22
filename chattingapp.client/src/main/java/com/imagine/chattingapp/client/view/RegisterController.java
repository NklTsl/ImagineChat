/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.Model.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.common.validation.Validation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import com.imagine.chattingapp.common.serverservices.LoginLogoutService;

/**
 *
 * @author azza
 */
public class RegisterController implements Initializable {
    
    private MainController mainController;
    private Stage primaryStage;
    
    @FXML
    private Label validResult;
    @FXML
    private ImageView background;
    
    @FXML
    private TextField phone;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private ImageView imageProfile;
    
    @FXML
    private Button add;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField ConPassword;
    
    @FXML
    private ComboBox<String> gender;
    
    @FXML
    private ComboBox<Country> country;
    
    @FXML
    private DatePicker birthdate;
    
    @FXML
    private TextField bio;
    
    @FXML
    private Button submit;
    
    private String selectedImage;
    
    @FXML
    private ImageView welcomeimage;
    
    
    private RegisterService registerService;
    public RegisterController(MainController mainController, Stage primaryStage){
        this.mainController = mainController;
        this.primaryStage = primaryStage;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            
            RegisterService registerService = (RegisterService) ServiceLocator.getService("RegisterService");
            
            Image image = new Image(getClass().getResource("/welcome.jpg").toString());
            Image imageBack = new Image(getClass().getResource("/bckk.jpeg").toString());
            Image defaultProfileImage = new Image(getClass().getResource("/DefaultPerson.png").toString());
            welcomeimage.setImage(image);
            background.setImage(imageBack);
            imageProfile.setImage(defaultProfileImage);
            // gender items
            gender.getItems().add("male");
            gender.getItems().add("female");
            gender.getSelectionModel().selectFirst();
            
            
            List<Country> CountryList = registerService.getCountries();
            
            country.getItems().addAll(CountryList);
            
            country.setConverter(new StringConverter<Country>() {
                @Override
                public String toString(Country country) {
                    return country.getName();
                }

                @Override
                public Country fromString(String string) {
                    return country.getItems().stream().filter(ap -> 
                        ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
            
            
            country.getSelectionModel().selectFirst();
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // submit button to add to database
        
        submit.setOnAction((event)-> {
            String validationResult = validateForm();
            if(validationResult != null){
                validResult.setText(validationResult);
                validResult.setTextFill(Color.RED);
            }else{
                
                try {
                    
                    File imgfile;
                    if(selectedImage != null)
                    {
                        imgfile = new File(selectedImage);
                    }
                    else
                    {
                        imgfile = new File(getClass().getResource("/DefaultPerson.png").toString());//"target/classes/DefaultPerson.png");
                    }
                    
                    FileInputStream fin = new FileInputStream(imgfile);
                    byte[] image = new byte[(int)imgfile.length()];
                    fin.read(image);
                    
                    User user = new User();
                    user.setPhoneNumber(phone.getText());
                    user.setName(name.getText());
                    user.setEmail(email.getText());
                    user.setPassword(password.getText());
                    user.setGender(gender.getSelectionModel().getSelectedItem() == "male" ? true : false);
                    user.setPicture(image);
                    user.setCountryID(country.getSelectionModel().getSelectedItem().getId());
                    user.setDateOfBirth(Date.valueOf(birthdate.getValue()).getTime());
                    user.setBiography(bio.getText().trim());
                    
                    registerService.register(user);
                    
                    validResult.setText("Success Registeration");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,"Success Registeration");
                    successAlert.showAndWait();
                    mainController.switchToLoginScene();
                    
                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }});
        
        // button to choose picture
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Profile Image");
                File selected = fileChooser.showOpenDialog(primaryStage);
                if(selected != null)
                {
                    imageProfile.setImage(new Image(selected.toURI().toString()));
                    selectedImage = selected.toURI().toString();
                }
            }
        });
    }
    
    
    @FXML
    private void btnBackToLoginOnAction(ActionEvent event) {
        mainController.switchToLoginScene();
    }

    private String validateForm(){
        if(!Validation.validatePhone(phone.getText().toString().trim())){
            return "Invalid Phone Number";
        }
        if(name.getText().isEmpty()){
            return "Invalid user Name";
        }
        if(!Validation.validateEmail(email.getText().toString().trim())){
            return "Invalid Email";
        }
        if(!Validation.validatePassword(password.getText().toString())){
            return "Invalid Password at least 8 characters";
        }
        if(!password.getText().toString().equals(ConPassword.getText().toString()))
            return "Password Doesn't Match";
        if(gender.getSelectionModel().getSelectedItem() == null)
            return "Choose your Gender";
        if(country.getSelectionModel().getSelectedItem() == null){
            return "Choose your country";
        }
        if(birthdate.getValue() == null) //invalid logic
            return "Choose your BirthDate";
        if(bio.getText().isEmpty()){
            return "Choose your Bio";
        }
        return null;
    }
}
