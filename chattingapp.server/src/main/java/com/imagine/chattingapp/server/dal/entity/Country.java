/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.entity;

/**
 *
 * @author Mahmoud Shereif
 */
public class Country {
    private byte id;
    private String name;
    
    public void setID(byte id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public byte getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}
