package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.GroupMember;
import com.imagine.chattingapp.common.dto.NewGroupInfo;
import com.imagine.chattingapp.common.serverservices.AddNewGroupService;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class AddNewGroupController implements Initializable {
    
    @FXML
    private Circle cirGroupmage;
    @FXML
    private TextField txtGroupName;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCreate;
    @FXML
    private ListView<GroupMember> lstGroupMembers;
    
    private List<GroupMember> groupMembers;
    private Stage groupStage;
    private String ownerPhoneNumber;
    
    private byte[] groupImage;
    
    
    public AddNewGroupController(List<GroupMember> groupMembers, Stage groupStage, String ownerPhoneNumber) {
        this.groupMembers = groupMembers;
        this.groupStage = groupStage;
        this.ownerPhoneNumber = ownerPhoneNumber;
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileInputStream fin = null;
        try {
            // TODO
            lstGroupMembers.getItems().addAll(groupMembers);
            lstGroupMembers.setCellFactory(new Callback<ListView<GroupMember>, ListCell<GroupMember>>() {
                @Override
                public ListCell<GroupMember> call(ListView<GroupMember> param) {
                    return new GroupMemberCell();
                }
            });
            
            Image statusImage = new Image("/GroupDefaultImage.png");
            cirGroupmage.setFill(new ImagePattern(statusImage));
            cirGroupmage.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
            
            File imgFile = new File("src/main/resources/GroupDefaultImage.png");
            fin = new FileInputStream(imgFile);
            groupImage = new byte[(int)imgFile.length()];
            fin.read(groupImage);
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        groupStage.close();
    }
    
    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        List<String> membersPhones = new ArrayList<>();
        
        groupMembers.forEach((member) -> {
            if(member.getChecked()){
                membersPhones.add(member.getPhoneNumber());
            }
        });
        
        if(!membersPhones.isEmpty() && !txtGroupName.getText().isEmpty()){
            try {
                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
                AddNewGroupService addNewGroupService = (AddNewGroupService) registry.lookup("AddNewGroupService");
                
                NewGroupInfo groupInfo = new NewGroupInfo();
                groupInfo.setGroupName(txtGroupName.getText());
                groupInfo.setOwnerPhoneNumber(ownerPhoneNumber);
                groupInfo.setMembersPhones(membersPhones);
                groupInfo.setGroupImage(groupImage);
                
                addNewGroupService.addNewGroup(groupInfo);
                
                Alert closeAlert = new Alert(Alert.AlertType.INFORMATION,"Group created successfully");
                closeAlert.showAndWait();
                groupStage.close();
                
                
            } catch (RemoteException ex) {
                Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
                //group did't created
            } catch (NotBoundException ex) {
                Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
            Alert closeAlert = new Alert(Alert.AlertType.INFORMATION,"Select friends");
            closeAlert.showAndWait();
        }
    }
    @FXML
    private void cirGroupmageOnMouseClicked(MouseEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select group image");
        File choosenFile = fileChooser.showOpenDialog(groupStage);
        
        if(choosenFile != null)
        {
            FileInputStream fin = null;
            try {
                Image statusImage = new Image(new FileInputStream(choosenFile));
                cirGroupmage.setFill(new ImagePattern(statusImage));
                
                
                fin = new FileInputStream(choosenFile);
                groupImage = new byte[(int)choosenFile.length()];
                fin.read(groupImage);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AddNewGroupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
