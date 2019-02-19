
package com.imagine.chattingapp.common.entity;

public class Request_Status implements Entity {
    private byte id;
    private String description;

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
