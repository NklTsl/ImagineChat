/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.view;

import com.imagine.chattingapp.server.control.MainController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.BoxLayout;

/**
 *
 * @author Saror Mohamed
 */
public class StatisticsViewController implements Initializable {

    @FXML
    private JFXHamburger burger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane anchopPane;
    @FXML
    private StackPane pnStackPane;

    public StatisticsViewController(MainController mainController) {
        this.mainController = mainController;
    }

    public StackPane getPnStackPane() {
        return pnStackPane;
    }

    private HamburgerBackArrowBasicTransition transition;

    private MainController mainController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadDrawer();
        loadService();
        
        transition = new HamburgerBackArrowBasicTransition(burger);
        transition.setRate(1);

        burger.setOnMousePressed((event) -> {
            System.out.println("pressed");
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
            drawer.setVisible(true);

        });

    }

    public HamburgerBackArrowBasicTransition getTransition() {
        return transition;
    }

    public JFXHamburger getBurger() {
        return burger;
    }

    public AnchorPane getAnchopPane() {
        return anchopPane;
    }

    public JFXDrawer getDrawer() {
        return drawer;
    }

    private void loadDrawer() {
        VBox box = null;
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            DrawerViewController drawerController = new DrawerViewController(mainController, this);
//            drawerController.setController(this);
            loader.setController(drawerController);
            root = loader.load(getClass().getResource("/DrawerView.fxml").openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        box = (VBox) root;
        drawer.setSidePane(box);
        
    }

    public void loadService() {
        try {
            FXMLLoader loader = new FXMLLoader();
            ServerDesignViewController serverDesignViewController = new ServerDesignViewController(mainController);
            loader.setController(serverDesignViewController);
            Parent root = loader.load(getClass().getResource("/ServerDesign.fxml").openStream());
            pnStackPane.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
