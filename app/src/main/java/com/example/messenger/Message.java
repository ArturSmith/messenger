package com.example.messenger;

public class Message {
    private String text;
    private String senderId;
    private String receivedId;


    public Message(String message, String senderId, String receiveID) {
        this.text = message;
        this.senderId = senderId;
        this.receivedId = receiveID;

    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceivedId() {
        return receivedId;
    }
}
