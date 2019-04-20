package com.example.tinder.home;

public class Messages {
    private int conversation_id;
    private int sender_id;
    private String message;
    private long created_at;


    public void Messages(){

    }

    public String getMessage() {
        return message;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }
}
