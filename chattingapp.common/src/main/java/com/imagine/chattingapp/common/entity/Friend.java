/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.common.entity;

import java.sql.Timestamp;

/**
 *
 * @author Mahmoud Shereif
 */
public class Friend implements Entity {
    private String phoneNumber1;
    private String phoneNumber2;
    private String realtiveGroup;
    private boolean blockFlag;
    private Timestamp lastMessageSentTime;
    private String fontFamliy;
    private short fontSize;
    private int fontColor;
    private boolean boldFlag;
    private boolean underlineFlag;
    private boolean italicFlag;

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getRealtiveGroup() {
        return realtiveGroup;
    }

    public void setRealtiveGroup(String realtiveGroup) {
        this.realtiveGroup = realtiveGroup;
    }

    public boolean getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(boolean blockFlag) {
        this.blockFlag = blockFlag;
    }

    public Timestamp getLastMessageSentTime() {
        return lastMessageSentTime;
    }

    public void setLastMessageSentTime(Timestamp latsMessageSentTime) {
        this.lastMessageSentTime = latsMessageSentTime;
    }

    public String getFontFamliy() {
        return fontFamliy;
    }

    public void setFontFamliy(String fontFamliy) {
        this.fontFamliy = fontFamliy;
    }

    public short getFontSize() {
        return fontSize;
    }

    public void setFontSize(short fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public boolean getBoldFlag() {
        return boldFlag;
    }

    public void setBoldFlag(boolean boldFlag) {
        this.boldFlag = boldFlag;
    }

    public boolean getUnderlineFlag() {
        return underlineFlag;
    }

    public void setUnderlineFlag(boolean underlineFlag) {
        this.underlineFlag = underlineFlag;
    }

    public boolean getItalicFlag() {
        return italicFlag;
    }

    public void setItalicFlag(boolean italicFlag) {
        this.italicFlag = italicFlag;
    }

    public byte[] getTextBackground() {
        return textBackground;
    }

    public void setTextBackground(byte[] textBackground) {
        this.textBackground = textBackground;
    }
    private byte[] textBackground;
    
}
