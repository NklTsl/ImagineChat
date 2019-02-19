package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.client.control.ClientServiceImpl;
import com.imagine.chattingapp.client.control.MainController;
import com.imagine.chattingapp.client.control.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.common.clientservices.ClientService;
import com.imagine.chattingapp.common.entity.Friend_Request;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.common.serverservices.AddNewContact;
import com.imagine.chattingapp.common.serverservices.LoginService;
import com.imagine.chattingapp.common.validation.Validation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Menna Helmy
 */
public class AddContactController implements Initializable {

    @FXML
    private Button addToListBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button sendRequestBtn;
    @FXML
    private TextField phoneTextField;
    @FXML
    private VBox allContactsVBox;
    MainController main;
    List<Friend_Request> usersToBeSentRequestsTo;
    List<String> listContents;
    User mainUser ;
    AddNewContact addContact;
    int counter = 0;

    public AddContactController(MainController main) {

        this.main = main;
        usersToBeSentRequestsTo = new ArrayList<>();
        listContents = new ArrayList<>();
        mainUser = new User();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void addToList(ActionEvent event) {

        String friendPhone = phoneTextField.getText();
        if (friendPhone.isEmpty()) {

            phoneTextField.clear();
            phoneTextField.setPromptText("Please Enter a phone Number");
        } else {

            if (Validation.validatePhone(friendPhone)) {
                try {

                    User userRequested = new User();
                    userRequested.setPhoneNumber(friendPhone);
                    
                    //change!!!
                    mainUser.setPhoneNumber("01154097534");
//                   
                     addContact =(AddNewContact) ServiceLocator.getService("AddContactService");
                    if (addContact.checkIsOnSys(userRequested)) {
                        if (addContact.checkIsBlocked(mainUser, userRequested)) {

                            Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            myAlert.setTitle("Error");
                            myAlert.setHeaderText("This User Can't be Located");
                            Optional<ButtonType> result = myAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                myAlert.close();
                            }
                            phoneTextField.clear();
                        } else if (addContact.checkIsFriend(mainUser, userRequested)) {

                            Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            myAlert.setTitle("ALready Friends");
                            // myAlert.setContentText("Press Ok to Exit");
                            myAlert.setHeaderText("You are Lucky, you are already Friends!");
                            Optional<ButtonType> result = myAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                myAlert.close();
                            }
                            phoneTextField.clear();

                        } else if (addContact.checkIsPending(mainUser, userRequested)) {
                            addContact.acceptPending(mainUser, userRequested);
                            Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            myAlert.setTitle("Success");
                            //myAlert.setContentText("Press Ok to Exit");
                            myAlert.setHeaderText("This User has been accepted");
                            Optional<ButtonType> result = myAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                myAlert.close();
                            }
                            phoneTextField.clear();
                        }else{
                        
                            Label newContact = new Label();
                            newContact.setText(friendPhone);
                            Image cancelImage = new Image(new FileInputStream("D:\\ImagineChat Java Project\\ImagineChat\\chattingapp.client\\src\\main\\resources\\cancel.png"));
                            ImageView cancelImageView = new ImageView(cancelImage);
                            cancelImageView.setFitHeight(20);
                            cancelImageView.setFitWidth(20);
                            phoneTextField.clear();
                            
                                
                            HBox contactHBox = new HBox(newContact,cancelImageView);
                            contactHBox.setSpacing(10);
                            contactHBox.getChildren().addAll();
                            
                            listContents.add(friendPhone);
                            contactHBox.setOnMouseClicked(new EventHandler<Event>() {
                                @Override
                                public void handle(Event event) {
                                    allContactsVBox.getChildren().remove(contactHBox);
                                    Label phoneLabel = (Label)contactHBox.getChildren().get(0);
                                    String phone = phoneLabel.getText();
                                    listContents.remove(phone);
                                    
                                }
                            });
                            allContactsVBox.setSpacing(10);
                            allContactsVBox.getChildren().add(contactHBox);
                        }

                    } else {
                        Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        myAlert.setTitle("Success");
                        //myAlert.setContentText("Press Ok to Exit");
                        myAlert.setHeaderText("This user isn't using our Application, do you like to invite him/her?");
                        myAlert.showAndWait();
                        phoneTextField.clear();
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(AddContactController.class.getName()).log(Level.SEVERE, null, ex);
                }  catch (FileNotFoundException ex) {
                    Logger.getLogger(AddContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
            
            
                 Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        myAlert.setTitle("Warning");
                        //myAlert.setContentText("Press Ok to Exit");
                        myAlert.setHeaderText("This Phone Number is not Valid!!");
                        myAlert.showAndWait();
                        phoneTextField.clear();
            
            }

        }
    }

    

    @FXML
    private void sendAddFriendRequest(ActionEvent event) {
        Platform.runLater(() -> {
            if(!listContents.isEmpty()){
        
            try {
                Iterator iterator = listContents.iterator();
                
                while(iterator.hasNext()){
                    
                    Friend_Request friendRequest = new Friend_Request();
                    friendRequest.setSenderPhoneNumber(mainUser.getPhoneNumber());
                    String phone = (String) iterator.next();
                    friendRequest.setReceiverPhoneNumber(phone);
                    int status = 1;
                    friendRequest.setStatusID((byte) status);
                   // counter++;
                    usersToBeSentRequestsTo.add(friendRequest);
                    
                    
                    
                }
                allContactsVBox.getChildren().clear();
                phoneTextField.clear();
                Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        myAlert.setTitle("Success");
                        //myAlert.setContentText("Press Ok to Exit");
                        myAlert.setHeaderText("we sent your request(s) successfully!");
                        myAlert.showAndWait();
                        phoneTextField.clear();
                        
                
                
                addContact.addNewContact(usersToBeSentRequestsTo);
            } catch (RemoteException ex) {
                Logger.getLogger(AddContactController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        }
        });
        
        
}

}
