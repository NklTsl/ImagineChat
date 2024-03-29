/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.entity;

import java.sql.Date;

/**
 *
 * @author Mahmoud Shereif
 */
public class User implements Entity{
    private String phoneNumber;
    private String name;
    private String email;
    private byte[] picture;
    private String password;
    private boolean gender;
    private long dateOfBirth;
    private String biography;
    private byte countryID;
    private byte statusID;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public byte getCountryID() {
        return countryID;
    }

    public void setCountryID(byte countryID) {
        this.countryID = countryID;
    }

    public byte getStatusID() {
        return statusID;
    }

    public void setStatusID(byte statusID) {
        this.statusID = statusID;
    }
}
