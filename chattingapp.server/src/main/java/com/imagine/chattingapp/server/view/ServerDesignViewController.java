/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.view;

import com.imagine.chattingapp.server.control.MainController;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Saror Mohamed
 */
public class ServerDesignViewController implements Initializable {

    @FXML
    private ImageView imageServer;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnStop;

    private MainController mainController;

    public ServerDesignViewController(MainController mainController) {
        this.mainController = mainController;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Image img = new Image(new FileInputStream("target/classes/manager.png"));
            imageServer.setImage(img);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        btnStart.setOnAction((event)->{
            mainController.startService();
        });
        btnStop.setOnAction((event)->{
            mainController.stopService();
        });
    }
}
