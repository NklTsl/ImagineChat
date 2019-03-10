package com.imagine.chattingapp.server.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.RegisterService;
import com.imagine.chattingapp.common.validation.Validation;
import com.imagine.chattingapp.server.control.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Saror Mohamed
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXButton btnChoose;
    @FXML
    private Circle imgCircle;
    @FXML
    private ImageView imgDefault;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXDatePicker pkrBirthDate;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXPasswordField txtConfirmPassword;
    @FXML
    private JFXTextField txtBio;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private JFXComboBox<Country> cmbCountry;
    @FXML
    private JFXComboBox<String> cmbGender;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private Label validResult;
    private Image workingImage;

    private MainController mainController;
    private Stage primaryStage;
    private RegisterService registerService;
    private String selectedImage;

    public RegisterController(MainController mainController, Stage primaryStage) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            
            imgCircle.setStroke(Color.SEAGREEN);
            selectedImage = "target/classes/profileImage.png";
            File imgFile = new File(selectedImage);
            workingImage = new Image(new ByteArrayInputStream(Files.readAllBytes(Paths.get(imgFile.getAbsolutePath().toString()))));
            imgCircle.setFill(new ImagePattern(workingImage));
            imgCircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            registerService = (RegisterService) registry.lookup("RegisterService");

            Image defaultImage = new Image(getClass().getResource("/default.png").toString());
            imgDefault.setImage(defaultImage);

            // cmbGender items
            cmbGender.getItems().add("male");
            cmbGender.getItems().add("female");
            cmbGender.getSelectionModel().selectFirst();

            List<Country> CountryList = registerService.getCountries();

            for (Country country : CountryList) {
                System.out.println(country.getName());
            }

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

        } catch (RemoteException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // btnSubmit button to btnChoose to database
        btnSubmit.setOnAction((event) -> {
            String validationResult = validateForm();
            if (validationResult != null) {
                validResult.setText(validationResult);
                validResult.setTextFill(Color.RED);
            } else {

                try {

                    File imgfile;
//                    if (selectedImage != null) {
//                        imgfile = new File(selectedImage);
//                    } else {
//                        //throws NullPointerException
//                        imgfile = new File(selectedImage);
//                    }
                    imgfile = new File(selectedImage);
                    FileInputStream fin = new FileInputStream(imgfile);
                    byte[] image = new byte[(int) imgfile.length()];
                    fin.read(image);

                    User user = new User();
                    user.setPhoneNumber(txtPhone.getText());
                    user.setName(txtName.getText());
                    user.setEmail(txtEmail.getText());
                    user.setPassword(txtPassword.getText());
                    user.setGender(cmbGender.getSelectionModel().getSelectedItem() == "male" ? true : false);
                    user.setPicture(image);
                    user.setCountryID(cmbCountry.getSelectionModel().getSelectedItem().getId());
                    user.setDateOfBirth(Date.valueOf(pkrBirthDate.getValue()).getTime());
                    user.setBiography(txtBio.getText().trim());
                    user.setStatusID((byte)1);
                    System.out.println(user.toString());
                    
                    registerService.register(user);
                    
                    validResult.setText("Success Registeration");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Success Registeration");
                    successAlert.showAndWait();
                    mainController.switchToMainView();
                } catch (RemoteException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        // button to choose picture
        btnChoose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Profile Image");
                File selected = fileChooser.showOpenDialog(primaryStage);
                if (selected != null) {

                    try {
                        selectedImage = selected.getAbsolutePath();
                        imgCircle.setStroke(Color.SEAGREEN);
                        File imgFile = new File(selectedImage);
                        workingImage = new Image(new ByteArrayInputStream(Files.readAllBytes(Paths.get(imgFile.getAbsolutePath().toString()))));
                        imgCircle.setFill(new ImagePattern(workingImage));
                        imgCircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        btnCancel.setOnAction((event)->{
            mainController.switchToMainView();
        });

    }

    private String validateForm() {
        if (!Validation.validatePhone(txtPhone.getText().toString().trim())) {
            return "Invalid Phone Number";
        }
        if (txtName.getText().isEmpty()) {
            return "Invalid user Name";
        }
        if (!Validation.validateEmail(txtEmail.getText().toString().trim())) {
            return "Invalid Email";
        }
        if (!Validation.validatePassword(txtPassword.getText().toString())) {
            return "Invalid Password at least 8 characters";
        }
        if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
            return "Password Doesn't Match";
        }
        if (cmbGender.getSelectionModel().getSelectedItem() == null) {
            return "Choose your Gender";
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
