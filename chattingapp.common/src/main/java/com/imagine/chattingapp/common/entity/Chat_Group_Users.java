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
public class Chat_Group_Users implements Entity {
    private int groupId;
    private String userPhoneNumber;
    private String fontFamliy;
    private short fontSize;
    private int fontColor;
    private boolean boldFlag;
    private boolean underlineFlag;
    private boolean italicFlag;
    private byte[] textBackground;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupID) {
        this.groupId = groupID;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
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
    
}
