package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.ChatController;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class ChatWindow extends BorderPane {
    
    ChatController chatController;
    protected final ListView lstContacts;
    protected final BorderPane borderPane;
    protected final TextField txtMessage;
    protected final Button btnBack;
    protected final TextArea textArea;
    
    String currentSelectedContact = null;
    
    public ChatWindow(ChatController chatController) {
        
        this.chatController = chatController;
        
        lstContacts = new ListView();
        borderPane = new BorderPane();
        txtMessage = new TextField();
        btnBack = new Button();
        textArea = new TextArea();
        
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        
        BorderPane.setAlignment(lstContacts, javafx.geometry.Pos.CENTER);
        lstContacts.setPrefHeight(400.0);
        lstContacts.setPrefWidth(208.0);
        setLeft(lstContacts);
        
        BorderPane.setAlignment(borderPane, javafx.geometry.Pos.CENTER);
        borderPane.setPrefHeight(200.0);
        borderPane.setPrefWidth(200.0);
        
        BorderPane.setAlignment(txtMessage, javafx.geometry.Pos.CENTER);
        txtMessage.setOnKeyPressed(this::txtMessageKeyPressed);
        borderPane.setBottom(txtMessage);
        
        BorderPane.setAlignment(btnBack, javafx.geometry.Pos.CENTER);
        btnBack.setMnemonicParsing(false);
        btnBack.setOnAction(this::btnBackAction);
        btnBack.setPrefHeight(20.0);
        btnBack.setPrefWidth(405.0);
        btnBack.setText("Back");
        borderPane.setTop(btnBack);
        
        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        borderPane.setCenter(textArea);
        setCenter(borderPane);
        
        lstContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
            currentSelectedContact = newString.toString();
        });
        
    }
    
    protected void txtMessageKeyPressed(javafx.scene.input.KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.ENTER))
        {
            if(!txtMessage.getText().trim().equals("")){
                chatController.sendOneToOneMessage(txtMessage.getText().trim(), currentSelectedContact);
                this.txtMessage.clear();
            }
        }
    }
    
    protected void btnBackAction(javafx.event.ActionEvent actionEvent){
        chatController.unregister();
        chatController.switchToLoginScene();
    }
    public void appendMessage(String message){
        this.textArea.appendText(message + "\n");
    }
    
}
