
package com.imagine.chattingapp.common.dto;

import java.io.Serializable;


public class FriendRequestEligibility implements Serializable{
    
    private String phoneNumber;
    private String name;
    private String eligibility;

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

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }
    
}
