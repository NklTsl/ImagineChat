package com.imagine.chattingapp.common.dto;

import java.io.Serializable;
public class AnnounceNotification implements Notification, Serializable {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
   
}
