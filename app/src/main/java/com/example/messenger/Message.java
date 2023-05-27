package com.example.messenger;

public class Message {
    private String message;
    private String id;
    private String receivedId;


    public Message(String message, String id, String receivedId) {
        this.message = message;
        this.id = id;
        this.receivedId = receivedId;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getReceivedId() {
        return receivedId;
    }
}
