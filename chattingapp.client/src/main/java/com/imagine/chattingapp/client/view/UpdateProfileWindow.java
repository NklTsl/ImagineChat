package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class UpdateProfileWindow extends BorderPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final RowConstraints rowConstraints2;
    protected final RowConstraints rowConstraints3;
    protected final RowConstraints rowConstraints4;
    protected final RowConstraints rowConstraints5;
    protected final RowConstraints rowConstraints6;
    protected final RowConstraints rowConstraints7;
    protected final RowConstraints rowConstraints8;
    protected final RowConstraints rowConstraints9;
    protected final Label lblPhone;
    protected final Label lblName;
    protected final Label lblEmail;
    protected final Label lblPicture;
    protected final Label lblPassword;
    protected final Label lblConfirmPassword;
    protected final Label lblGender;
    protected final Label label;
    protected final Label lblBirthDate;
    protected final Label lblBio;
    protected final TextField txtPhone;
    protected final TextField txtName;
    protected final TextField txtEmail;
    protected final TextField txtCountry;
    protected final DatePicker pickerBirthDate;
    protected final TextField txtBio;
    protected final PasswordField txtPassword;
    protected final PasswordField txtConfirmPassword;
    protected final ComboBox comboGender;
    protected final Button btnRegister;
    protected final Button btnCancel;
    protected final TextField txtPhotoPath;
    protected final Label welcomeMessage;

    public UpdateProfileWindow() {

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();
        rowConstraints3 = new RowConstraints();
        rowConstraints4 = new RowConstraints();
        rowConstraints5 = new RowConstraints();
        rowConstraints6 = new RowConstraints();
        rowConstraints7 = new RowConstraints();
        rowConstraints8 = new RowConstraints();
        rowConstraints9 = new RowConstraints();
        lblPhone = new Label();
        lblName = new Label();
        lblEmail = new Label();
        lblPicture = new Label();
        lblPassword = new Label();
        lblConfirmPassword = new Label();
        lblGender = new Label();
        label = new Label();
        lblBirthDate = new Label();
        lblBio = new Label();
        txtPhone = new TextField();
        txtName = new TextField();
        txtEmail = new TextField();
        txtCountry = new TextField();
        pickerBirthDate = new DatePicker();
        txtBio = new TextField();
        txtPassword = new PasswordField();
        txtConfirmPassword = new PasswordField();
        comboGender = new ComboBox();
        btnRegister = new Button();
        btnCancel = new Button();
        txtPhotoPath = new TextField();
        welcomeMessage = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.TOP_CENTER);
        gridPane.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        gridPane.setHgap(5.0);
        gridPane.setPrefHeight(427.0);
        gridPane.setPrefWidth(600.0);
        gridPane.setVgap(5.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(295.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(146.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(434.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(429.0);

        rowConstraints.setMaxHeight(129.0);
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(66.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMaxHeight(129.0);
        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(66.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMaxHeight(129.0);
        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(66.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMaxHeight(129.0);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(66.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints3.setMaxHeight(129.0);
        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(66.0);
        rowConstraints3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints4.setMaxHeight(129.0);
        rowConstraints4.setMinHeight(10.0);
        rowConstraints4.setPrefHeight(66.0);
        rowConstraints4.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints5.setMaxHeight(129.0);
        rowConstraints5.setMinHeight(10.0);
        rowConstraints5.setPrefHeight(66.0);
        rowConstraints5.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints6.setMaxHeight(197.0);
        rowConstraints6.setMinHeight(10.0);
        rowConstraints6.setPrefHeight(66.0);
        rowConstraints6.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints7.setMaxHeight(129.0);
        rowConstraints7.setMinHeight(10.0);
        rowConstraints7.setPrefHeight(66.0);
        rowConstraints7.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints8.setMaxHeight(129.0);
        rowConstraints8.setMinHeight(10.0);
        rowConstraints8.setPrefHeight(66.0);
        rowConstraints8.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints9.setMaxHeight(129.0);
        rowConstraints9.setMinHeight(10.0);
        rowConstraints9.setPrefHeight(66.0);
        rowConstraints9.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        lblPhone.setText("Phone Number:");
        lblPhone.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblName, 1);
        lblName.setText("Display Name:");
        lblName.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblEmail, 2);
        lblEmail.setText("Email:");
        lblEmail.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblPicture, 3);
        lblPicture.setText("Picture:");
        lblPicture.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblPassword, 4);
        lblPassword.setText("Password:");
        lblPassword.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblConfirmPassword, 5);
        lblConfirmPassword.setText("Confirm Password:");
        lblConfirmPassword.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblGender, 6);
        lblGender.setText("Gender:");
        lblGender.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(label, 7);
        label.setText("Country:");
        label.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblBirthDate, 8);
        lblBirthDate.setText("Birth Date:");
        lblBirthDate.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblBio, 9);
        lblBio.setText("BIO:");
        lblBio.setFont(new Font("Tahoma", 20.0));

        GridPane.setColumnIndex(txtPhone, 1);
        txtPhone.setPromptText("Enter your phone number");

        GridPane.setColumnIndex(txtName, 1);
        GridPane.setRowIndex(txtName, 1);
        txtName.setPromptText("Enter your full name");

        GridPane.setColumnIndex(txtEmail, 1);
        GridPane.setRowIndex(txtEmail, 2);
        txtEmail.setPromptText("Enter a valid email");

        GridPane.setColumnIndex(txtCountry, 1);
        GridPane.setRowIndex(txtCountry, 7);
        txtCountry.setPromptText("Home Town");

        GridPane.setColumnIndex(pickerBirthDate, 1);
        GridPane.setRowIndex(pickerBirthDate, 8);
        pickerBirthDate.setPrefHeight(25.0);
        pickerBirthDate.setPrefWidth(433.0);

        GridPane.setColumnIndex(txtBio, 1);
        GridPane.setRowIndex(txtBio, 9);
        txtBio.setPromptText("Enter your Bio");

        GridPane.setColumnIndex(txtPassword, 1);
        GridPane.setRowIndex(txtPassword, 4);

        GridPane.setColumnIndex(txtConfirmPassword, 1);
        GridPane.setRowIndex(txtConfirmPassword, 5);

        GridPane.setColumnIndex(comboGender, 1);
        GridPane.setRowIndex(comboGender, 6);
        comboGender.setPrefHeight(25.0);
        comboGender.setPrefWidth(434.0);

        GridPane.setColumnIndex(btnRegister, 1);
        GridPane.setHalignment(btnRegister, javafx.geometry.HPos.RIGHT);
        GridPane.setRowIndex(btnRegister, 10);
        btnRegister.setMnemonicParsing(false);
        btnRegister.setPrefHeight(25.0);
        btnRegister.setPrefWidth(73.0);
        btnRegister.setText("Register");

        GridPane.setRowIndex(btnCancel, 10);
        btnCancel.setMnemonicParsing(false);
        btnCancel.setPrefHeight(25.0);
        btnCancel.setPrefWidth(70.0);
        btnCancel.setText("Cancel");

        GridPane.setColumnIndex(txtPhotoPath, 1);
        GridPane.setRowIndex(txtPhotoPath, 3);
        txtPhotoPath.setPromptText("Select a photo");
        gridPane.setPadding(new Insets(10.0));
        setCenter(gridPane);

        BorderPane.setAlignment(welcomeMessage, javafx.geometry.Pos.CENTER);
        welcomeMessage.setPrefHeight(47.0);
        welcomeMessage.setPrefWidth(478.0);
        welcomeMessage.setText("Welcome To Imagine Chat");
        welcomeMessage.setFont(new Font(18.0));
        setTop(welcomeMessage);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getRowConstraints().add(rowConstraints2);
        gridPane.getRowConstraints().add(rowConstraints3);
        gridPane.getRowConstraints().add(rowConstraints4);
        gridPane.getRowConstraints().add(rowConstraints5);
        gridPane.getRowConstraints().add(rowConstraints6);
        gridPane.getRowConstraints().add(rowConstraints7);
        gridPane.getRowConstraints().add(rowConstraints8);
        gridPane.getRowConstraints().add(rowConstraints9);
        gridPane.getChildren().add(lblPhone);
        gridPane.getChildren().add(lblName);
        gridPane.getChildren().add(lblEmail);
        gridPane.getChildren().add(lblPicture);
        gridPane.getChildren().add(lblPassword);
        gridPane.getChildren().add(lblConfirmPassword);
        gridPane.getChildren().add(lblGender);
        gridPane.getChildren().add(label);
        gridPane.getChildren().add(lblBirthDate);
        gridPane.getChildren().add(lblBio);
        gridPane.getChildren().add(txtPhone);
        gridPane.getChildren().add(txtName);
        gridPane.getChildren().add(txtEmail);
        gridPane.getChildren().add(txtCountry);
        gridPane.getChildren().add(pickerBirthDate);
        gridPane.getChildren().add(txtBio);
        gridPane.getChildren().add(txtPassword);
        gridPane.getChildren().add(txtConfirmPassword);
        gridPane.getChildren().add(comboGender);
        gridPane.getChildren().add(btnRegister);
        gridPane.getChildren().add(btnCancel);
        gridPane.getChildren().add(txtPhotoPath);

    }

    public UpdateProfileWindow(MainController aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
