package com.example.model;


public class Messages {
    private int conversation_id;
    private int sender_id;
    private String message;
    private long created_at;

    public Messages(){

    }

    public Messages(Integer user_id, Integer conversation_id, String body) {
        sender_id = user_id;
        message = body;
        created_at = 0;
        this.conversation_id = conversation_id;
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

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }
}
