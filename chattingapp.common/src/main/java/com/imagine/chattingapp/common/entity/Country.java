/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.entity;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shereif
 */
public class Country implements Entity {
    private byte id;
    private String name;
    
    public void setId(byte id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public byte getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}
