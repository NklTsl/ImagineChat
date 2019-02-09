/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.dal.entity;

/**
 *
 * @author Mahmoud Shereif
 */
public class Friend_Request {
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private byte statusID;

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

    public byte getStatusID() {
        return statusID;
    }

    public void setStatusID(byte statusID) {
        this.statusID = statusID;
    }
}
