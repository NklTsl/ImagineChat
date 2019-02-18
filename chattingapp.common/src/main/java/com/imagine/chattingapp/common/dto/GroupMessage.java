/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.dto;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shereif
 */
public class GroupMessage implements Message, Serializable {
    private int receiverGroup;
    private String message;
    private LightUser senderUser;
    
    public int getReceiverGroup() {
        return receiverGroup;
    }

    public void setReceiverGroup(int receiverGroup) {
        this.receiverGroup = receiverGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LightUser getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(LightUser senderUser) {
        this.senderUser = senderUser;
    }

    
}
