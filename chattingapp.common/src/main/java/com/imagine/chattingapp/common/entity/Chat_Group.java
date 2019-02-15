/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.entity;

import java.sql.Timestamp;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group implements Entity{
    private int id;
    private String ownerPhoneNumber;
    private String name;
    private Timestamp lastMessageSentTime;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastMessageSentTime() {
        return lastMessageSentTime;
    }

    public void setLastMessageSentTime(Timestamp lastMessageSentTime) {
        this.lastMessageSentTime = lastMessageSentTime;
    }
    

}
