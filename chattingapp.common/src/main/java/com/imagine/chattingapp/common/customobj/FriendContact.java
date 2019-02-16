/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.customobj;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shereif
 */
public class FriendContact implements Serializable, Contact{
    private String phoneNumber;
    private String name;
    private byte[] image;
    private String relativeGroup;
    private Byte status;
    private String statusDescription;
    private Long lastSentMessageTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRelativeGroup() {
        return relativeGroup;
    }

    public void setRelativeGroup(String relativeGroup) {
        this.relativeGroup = relativeGroup;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    
    public Long getLastSentMessageTime() {
        return lastSentMessageTime;
    }

    public void setLastSentMessageTime(Long lastSentMessageTime) {
        this.lastSentMessageTime = lastSentMessageTime;
    }
    
    
    
    
    
    
}
