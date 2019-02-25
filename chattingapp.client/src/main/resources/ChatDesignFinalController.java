/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class ChatDesignFinalController implements Initializable {

    @FXML
    private Button btnFriendRequest;
    @FXML
    private Button btnAddGroup;
    @FXML
    private Button btnSendFile;
    @FXML
    private JFXToggleButton btnEnableChatBot;
    @FXML
    private Button btnsaveChatSession;
    @FXML
    private ListView<?> lstContacts;
    @FXML
    private WebView chatWebView;
    @FXML
    private HTMLEditor htmlEditor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnFriendRequestOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddGroupOnAction(ActionEvent event) {
    }

    @FXML
    private void btnSendFileOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEnableChatBotOnAction(ActionEvent event) {
    }

    @FXML
    private void btnsaveChatSessionOnAction(ActionEvent event) {
    }

    @FXML
    private void htmlEditorOnKeyPressed(KeyEvent event) {
    }
    
}
