
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.Model.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.common.dto.FriendRequestEligibility;
import com.imagine.chattingapp.common.entity.LoginUser;
import com.imagine.chattingapp.common.serverservices.FriendRequest;
import com.imagine.chattingapp.common.validation.Validation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
     
    ChatController chatController;
    String senderPhoneNumber;
    String receiverPhoneNumber;
    FriendRequestEligibility friendRequestEligibility;
    FriendRequest friendRequest;
    List<String> listContents;
    
    

    public SendFriendRequestController(ChatController chatController,LoginUser loginUser) {
        this.chatController =chatController;
        receiverPhoneNumber = null;
        friendRequestEligibility = null;
        friendRequest = null;
        senderPhoneNumber = loginUser.getPhoneNumber();
        listContents = new ArrayList<>();
        
    }
            
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allContactsVBox.setSpacing(20);
    }
    
    @FXML
    private void addToList(ActionEvent event){
        receiverPhoneNumber = phoneTextField.getText();
        if(receiverPhoneNumber.isEmpty()){
        
            phoneTextField.clear();
            phoneTextField.setPromptText("Please Enter your Phone number!");
            
        }else if(phoneTextField.getText().equals(senderPhoneNumber)){
            this.displayUserAlert("Unfortunately,you cant add yourself!");
        }else{
        
            if(Validation.validatePhone(receiverPhoneNumber)){
            
                try {
                    friendRequestEligibility = new FriendRequestEligibility();
                    friendRequest = (FriendRequest)ServiceLocator.getService("AddContactService");
                    friendRequestEligibility = friendRequest.checkFriendRequestEligibility(senderPhoneNumber, receiverPhoneNumber);
                    if(friendRequestEligibility.getEligibility() !=null){
                    
                        displayUserAlert(friendRequestEligibility.getEligibility());
                    }else{
                    
                        viewFriendDetailsInList();
                    }
                
                } catch (RemoteException ex) {
                    Logger.getLogger(SendFriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }
    
    }

    private void displayUserAlert(String eligibility) {
        phoneTextField.clear();
        phoneTextField.setPromptText("Please Enter your Friend's Number");
        Alert userResponseAlert = new Alert(Alert.AlertType.CONFIRMATION);
        userResponseAlert.setTitle(eligibility);
        userResponseAlert.setHeaderText(eligibility);
        Optional<ButtonType> choice = userResponseAlert.showAndWait();
        if(choice.isPresent()&& choice.get() == ButtonType.OK){
        
            userResponseAlert.close();
        }
    }
    private void viewFriendDetailsInList(){
        try {
            phoneTextField.clear();
            Label newContact  = new Label(friendRequestEligibility.getPhoneNumber());
            Image cancelImage = new Image(new FileInputStream(getClass().getResource("/Button-Delete-icon.png").toString()));
            ImageView cancelImageView = new ImageView(cancelImage);
            cancelImageView.setFitHeight(15);
            cancelImageView.setFitWidth(15);
            
            HBox contactHBox = new HBox(10,newContact,cancelImageView);
            listContents.add(friendRequestEligibility.getPhoneNumber());
            contactHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    allContactsVBox.getChildren().remove(contactHBox);
                    Label label= (Label) contactHBox.getChildren().get(0);
                    String phone = label.getText();
                    listContents.remove(phone);

                }
            });
            
            allContactsVBox.getChildren().add(contactHBox);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SendFriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    
    }
    @FXML
    private void sendAddFriendRequest(ActionEvent event){
    
        try {
            allContactsVBox.getChildren().clear();
            phoneTextField.clear();
            displayUserAlert("Friend Request Is Sent Successfully");
            friendRequest.sendFriendRequests(senderPhoneNumber, listContents);
        } catch (RemoteException ex) {
            Logger.getLogger(SendFriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
