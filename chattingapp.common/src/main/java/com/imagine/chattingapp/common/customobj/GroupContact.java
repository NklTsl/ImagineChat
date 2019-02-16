/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.customobj;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Mahmoud Shereif
 */
public class GroupContact implements Serializable, Contact {
    private int groupId;
    private String name;
    private byte[] image;
    private Long lastSentMessageTime;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupNumber) {
        this.groupId = groupNumber;
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
    
    public Long getLastSentMessageTime() {
        return lastSentMessageTime;
    }

    public void setLastSentMessageTime(Long lastSentMessageTime) {
        this.lastSentMessageTime = lastSentMessageTime;
    }
    
}
