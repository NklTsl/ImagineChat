/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.entity;

import java.sql.Timestamp;

/**
 *
 * @author Mahmoud Shereif
 */
public class One_To_One_Messages implements Entity {
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private Timestamp timestamp;
    private String messageContentOrFileName;
    private byte[] file;
    private byte status;

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageContentOrFileName() {
        return messageContentOrFileName;
    }

    public void setMessageContentOrFileName(String messageContentOrFileName) {
        this.messageContentOrFileName = messageContentOrFileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
