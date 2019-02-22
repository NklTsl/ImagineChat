/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class NewGroupInfo implements Serializable {
    private String groupName;
    private String ownerPhoneNumber;
    private byte[] groupImage;
    private List<String> membersPhones;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public byte[] getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(byte[] groupImage) {
        this.groupImage = groupImage;
    }

    public List<String> getMembersPhones() {
        return membersPhones;
    }

    public void setMembersPhones(List<String> membersPhones) {
        this.membersPhones = membersPhones;
    }
    
    
}
