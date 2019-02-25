/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.client.view;

import com.imagine.chattingapp.common.dto.Contact;
import com.imagine.chattingapp.common.dto.FriendContact;
import com.imagine.chattingapp.common.dto.GroupContact;
import com.sun.javafx.scene.control.skin.CustomColorDialog;
import java.io.ByteArrayInputStream;
import javafx.geometry.Pos;
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
public class ContactCell extends ListCell<Contact> {
    
    
    public ContactCell() {
        
    }
    
    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        
        if(!empty)
        {
            String contactName = null;
            Circle contactImagecircle = new Circle(25);
            
            
            HBox contactHBox = new HBox();
            
            contactHBox.setAlignment(Pos.CENTER_LEFT);
            contactHBox.setSpacing(5);
            
            contactImagecircle.setStroke(Color.SEAGREEN);
            
            if(contact instanceof FriendContact)
            {
                Circle statusCircle = new Circle(10);
                Pane spacePane = new Pane();
                contactHBox.setHgrow(spacePane, Priority.ALWAYS);
                
                FriendContact friendContact = (FriendContact)contact;
                if(friendContact.getImage() != null){
                    Image contactImage = new Image(new ByteArrayInputStream(friendContact.getImage()));
                    contactImagecircle.setFill(new ImagePattern(contactImage));
                    contactImagecircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
                }
                statusCircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
                if(friendContact.getStatus() != null)
                {
                    Image statusImage;
                    if(friendContact.getStatus() == 1)
                        statusImage = new Image("/online.png");
                    else if(friendContact.getStatus() == 2)
                        statusImage = new Image("/busy.png");
                    else
                        statusImage = new Image("/away.png");
                    
                    statusCircle.setFill(new ImagePattern(statusImage));
                    
                }
                else
                {
                    Image statusImage = new Image("/offline.png");
                    statusCircle.setFill(new ImagePattern(statusImage));
                    
                }
                
                contactHBox.getChildren().addAll(contactImagecircle, new Text(friendContact.getName()), spacePane, statusCircle);
                this.setGraphic(contactHBox);
            }
            else
            {
                GroupContact groupContact = (GroupContact)contact;
                
                if(groupContact.getImage() != null){
                    
                    Image contactImage = new Image(new ByteArrayInputStream(groupContact.getImage()));
                    contactImagecircle.setFill(new ImagePattern(contactImage));
                    contactImagecircle.setEffect(new DropShadow(25d, 0d, 2d, Color.DARKSEAGREEN));
                }
                contactHBox.getChildren().addAll(contactImagecircle, new Text(groupContact.getName()));
                this.setGraphic(contactHBox);
            }
        }
        else
        {
            this.setText(null);
            this.setGraphic(null);
        }
    }
}
