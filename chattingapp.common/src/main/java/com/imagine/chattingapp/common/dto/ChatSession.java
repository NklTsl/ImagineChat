/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.dto;

import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class ChatSession {
    private String sessionId;
    private String name;
    private byte[] picture;
    private boolean oneOrGroupFlag;
    
    private List<Message> messagesList;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean getOneOrGroupFlag() {
        return oneOrGroupFlag;
    }

    public void setOneOrGroupFlag(boolean oneOrGroupFlag) {
        this.oneOrGroupFlag = oneOrGroupFlag;
    }
    
    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Message> messagesList) {
        this.messagesList = messagesList;
    }
}
