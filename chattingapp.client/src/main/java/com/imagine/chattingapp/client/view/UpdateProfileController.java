/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.Model.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.client.view.RegisterController;
import com.imagine.chattingapp.common.dto.LightUser;
import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.UpdateProfileService;
import com.imagine.chattingapp.common.validation.Validation;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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

/**
 * FXML Controller class
 *
 * @author Hamada Abdrabou
 */
public class UpdateProfileController implements Initializable {

    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnAddPhoto;
    @FXML
    private Button btnCancel;
    @FXML
    private ImageView imgProfileImage;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtBio;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtConPassword;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private DatePicker pkrBirthDate;
    @FXML
    private ComboBox<Country> cmbCountry;
    @FXML
    private Label lblValidResult;

    private String selectedImage;
    private MainController mainController;
    private Stage primaryStage;
    private User updateUser;
    private UpdateProfileService updateProfileService;

    public UpdateProfileController(MainController mainController, Stage primaryStage, User updateUser) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.updateUser = updateUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            updateProfileService = (UpdateProfileService) ServiceLocator.getService("UpdateProfileService");
            if (updateUser.getPicture() != null) {
                Image userProfileImage = new Image(new ByteArrayInputStream(updateUser.getPicture()));
                imgProfileImage.setImage(userProfileImage);
            } else {
                FileInputStream fin;
                File imgfile = new File(getClass().getResource("/DefaultPersonIcon.png").toString());//"target/classes/DefaultPersonIcon.png");
                fin = new FileInputStream(imgfile);
                byte[] defaultUserImage = new byte[(int) imgfile.length()];
                fin.read(defaultUserImage);
                Image userProfileImage = new Image(new ByteArrayInputStream(defaultUserImage));
                imgProfileImage.setImage(userProfileImage);
            }

            List<Country> CountryList = updateProfileService.getCountries();
            cmbCountry.getItems().addAll(CountryList);
            cmbCountry.setConverter(new StringConverter<Country>() {
                @Override
                public String toString(Country country) {
                    return country.getName();
                }

                @Override
                public Country fromString(String string) {
                    return cmbCountry.getItems().stream().filter(ap
                            -> ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
            cmbCountry.getSelectionModel().selectFirst();
            btnSubmit.setOnAction((event) -> {
                String validationResult = validateForm();
                if (validationResult != null) {
                    lblValidResult.setText(validationResult);
                    lblValidResult.setTextFill(Color.RED);
                } else {

                    try {

                        File imgfile;
                        if (selectedImage != null) {
                            imgfile = new File(selectedImage);
                        } else {
                            imgfile = new File(getClass().getResource("/DefaultPersonIcon.png").toString());//"target/classes/DefaultPerson.png");
                        }

                        FileInputStream fin = new FileInputStream(imgfile);
                        byte[] tempProfileImage = new byte[(int) imgfile.length()];
                        fin.read(tempProfileImage);

                        User user = new User();
                        user.setPhoneNumber(updateUser.getPhoneNumber());
                        user.setName(txtName.getText());
                        user.setEmail(txtEmail.getText());
                        user.setPassword(txtPassword.getText());
                        user.setPicture(tempProfileImage);
                        user.setCountryID(cmbCountry.getSelectionModel().getSelectedItem().getId());
                        user.setDateOfBirth(Date.valueOf(pkrBirthDate.getValue()).getTime());
                        user.setBiography(txtBio.getText().trim());
                        updateProfileService.updateProfile(user);
                        lblValidResult.setText("Update Proile Success");
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Your Profile Updated Success");
                        successAlert.showAndWait();
                        LoginUser loginUser = new LoginUser();
                        loginUser.setPassword(user.getPassword());
                        loginUser.setPhoneNumber(user.getPhoneNumber());
                        LightUser lightUser = new LightUser();
                        lightUser.setName(user.getName());
                        lightUser.setImage(user.getPicture());
                        lightUser.setPhoneNumber(user.getPhoneNumber());
                        mainController.switchToChatScene(lightUser, loginUser);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            btnAddPhoto.setOnAction((event) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Profile Image");
                File selected = fileChooser.showOpenDialog(primaryStage);
                if (selected != null) {
                    FileInputStream fin = null;
                    try {
                        imgProfileImage.setImage(new Image(selected.toURI().toString()));
                        fin = new FileInputStream(selected);
                        byte[] tempProfileImage = new byte[(int) selected.length()];
                        fin.read(tempProfileImage);
                        updateUser.setPicture(tempProfileImage);
                        selectedImage = selected.toURI().toString();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(com.imagine.chattingapp.client.view.UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(com.imagine.chattingapp.client.view.UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fin.close();
                        } catch (IOException ex) {
                            Logger.getLogger(com.imagine.chattingapp.client.view.UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            btnCancel.setOnAction((event) -> {

//        mainController.switchToChatScene(lightUser, loginUser);
            });
        } catch (RemoteException ex) {
            Logger.getLogger(com.imagine.chattingapp.client.view.UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String validateForm() {
        if (txtName.getText().isEmpty()) {
            return "Invalid user Name";
        }
        if (!Validation.validateEmail(txtEmail.getText().toString().trim())) {
            return "Invalid Email";
        }
        if (!Validation.validatePassword(txtPassword.getText().toString())) {
            return "Invalid Password at least 8 characters";
        }
        if (!txtConPassword.getText().toString().equals(txtConPassword.getText().toString())) {
            return "Password Doesn't Match";
        }
        if (cmbCountry.getSelectionModel().getSelectedItem() == null) {
            return "Choose your country";
        }
        if (pkrBirthDate.getValue() == null) //invalid logic
        {
            return "Choose your BirthDate";
        }
        if (txtBio.getText().isEmpty()) {
            return "Choose your Bio";
        }
        return null;
    }
}
