package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.LoginController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class LoginWindow extends BorderPane {

    LoginController loginController = null;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final Label lblPhone;
    protected final Label lblPassword;
    protected final TextField txtPhone;
    protected final PasswordField txtPassword;
    protected final Button btnLogin;
    protected final Button btnCancel;
    protected final Label welcomeMessage;

    public LoginWindow(LoginController loginController) {

        this.loginController = loginController;
        
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        lblPhone = new Label();
        lblPassword = new Label();
        txtPhone = new TextField();
        txtPassword = new PasswordField();
        btnLogin = new Button();
        btnCancel = new Button();
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
        gridPane.setPrefHeight(225.0);
        gridPane.setPrefWidth(483.0);
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
        rowConstraints.setPrefHeight(36.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMaxHeight(180.0);
        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(43.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMaxHeight(223.0);
        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(223.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        lblPhone.setText("Phone Number:");
        lblPhone.setFont(new Font("Tahoma", 20.0));

        GridPane.setRowIndex(lblPassword, 1);
        lblPassword.setText("Password:");
        lblPassword.setFont(new Font("Tahoma", 20.0));

        GridPane.setColumnIndex(txtPhone, 1);
        txtPhone.setPromptText("Enter your phone number");

        GridPane.setColumnIndex(txtPassword, 1);
        GridPane.setRowIndex(txtPassword, 1);

        GridPane.setColumnIndex(btnLogin, 1);
        GridPane.setHalignment(btnLogin, javafx.geometry.HPos.RIGHT);
        GridPane.setRowIndex(btnLogin, 2);
        btnLogin.setMnemonicParsing(false);
        btnLogin.setOnAction(this::btnLoginAction);
        btnLogin.setPrefHeight(25.0);
        btnLogin.setPrefWidth(73.0);
        btnLogin.setText("login");

        GridPane.setRowIndex(btnCancel, 2);
        btnCancel.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        btnCancel.setMnemonicParsing(false);
        btnCancel.setOnAction(this::btnCancelAction);
        btnCancel.setPrefHeight(25.0);
        btnCancel.setPrefWidth(70.0);
        btnCancel.setText("Cancel");
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
        gridPane.getChildren().add(lblPhone);
        gridPane.getChildren().add(lblPassword);
        gridPane.getChildren().add(txtPhone);
        gridPane.getChildren().add(txtPassword);
        gridPane.getChildren().add(btnLogin);
        gridPane.getChildren().add(btnCancel);

    }

    protected void btnLoginAction(javafx.event.ActionEvent actionEvent){
        loginController.login(txtPhone.getText(), txtPassword.getText());
    }

    protected void btnCancelAction(javafx.event.ActionEvent actionEvent){
        
    }

}
