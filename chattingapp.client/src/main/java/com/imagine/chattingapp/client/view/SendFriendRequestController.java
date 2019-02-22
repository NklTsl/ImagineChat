
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.control.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.common.dto.FriendRequestEligibility;
import com.imagine.chattingapp.common.serverservices.FriendRequest;
import com.imagine.chattingapp.common.validation.Validation;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class SendFriendRequestController implements Initializable{
    
    @FXML
    private Button addToListBtn;
    @FXML
    private Button sendRequestBtn;
    @FXML
    private TextField phoneTextField;
    @FXML
    private VBox allContactsVBox;
    MainController mainController;
    String senderPhoneNumber;
    String receiverPhoneNumber;
    FriendRequestEligibility friendRequestEligibility;
    FriendRequest friendRequest;

    public SendFriendRequestController(MainController main) {
        this.mainController =main;
        senderPhoneNumber = null;
        receiverPhoneNumber = null;
        friendRequestEligibility = null;
        friendRequest = null;
        
    }
            
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    private void addToList(ActionEvent event){
        //Dont forget to apply the service locator pattern
        
        receiverPhoneNumber = phoneTextField.getText();
        if(receiverPhoneNumber.isEmpty()){
        
            phoneTextField.clear();
            phoneTextField.setPromptText("Please Enter your Phone number!");
            
        }else{
        
            if(Validation.validatePhone(receiverPhoneNumber)){
            
                try {
                    friendRequestEligibility = new FriendRequestEligibility();
                    friendRequest = (FriendRequest)ServiceLocator.getService("AddContactService");
                    friendRequestEligibility = friendRequest.checkFriendRequestEligibility(senderPhoneNumber, receiverPhoneNumber);
                    if(friendRequestEligibility.getPhoneNumber().equals(null)&& friendRequestEligibility.getName().equals(null)){
                    
                        displayUserAlert(friendRequestEligibility.getEligibility());
                    }
                
                } catch (RemoteException ex) {
                    Logger.getLogger(SendFriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }
    
    }

    private void displayUserAlert(String eligibility) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
