/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.GroupContact;
import com.imagine.chattingapp.common.dto.GroupMember;
import com.sun.javafx.scene.control.skin.CustomColorDialog;
import java.io.ByteArrayInputStream;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author Mahmoud Shereif
 */
public class GroupMemberCell extends ListCell<GroupMember> {
    
    
    public GroupMemberCell() {
        
    }
    
    @Override
    protected void updateItem(GroupMember groupMember, boolean empty) {
        super.updateItem(groupMember, empty);
        if(!empty)
        {
            Circle MemberImagecircle = new Circle(18);
            
            HBox contactHBox = new HBox();
            Pane spacePane = new Pane();
            
            contactHBox.setHgrow(spacePane, Priority.ALWAYS);
            
            contactHBox.setAlignment(Pos.CENTER_LEFT);
            contactHBox.setSpacing(5);
            
            MemberImagecircle.setStroke(Color.SEAGREEN);
            
            if(groupMember.getImage() != null){
                Image GroupMemberImage = new Image(new ByteArrayInputStream(groupMember.getImage()));
                MemberImagecircle.setFill(new ImagePattern(GroupMemberImage));
                MemberImagecircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
            }
            CheckBox groupMemberCheckBox =  new CheckBox();
            groupMember.checkedProperty().bind(groupMemberCheckBox.selectedProperty());
            contactHBox.getChildren().addAll(MemberImagecircle, new Text(groupMember.getName()), spacePane, groupMemberCheckBox);
            this.setGraphic(contactHBox);
            
        }
        else
        {
            this.setText(null);
            this.setGraphic(null);
        }
    }  
}
