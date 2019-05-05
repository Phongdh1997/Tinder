package com.example.rest.service;

import com.example.model.Message;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {

    @GET("/api/messages")
    Call<MessageResponse> getHistoricalMessage(@Query("conversation_id") Integer conversation_id);

    class MessageResponse {
        @SerializedName("conversation_id")
        @Expose
        private String conversation_id;

        @SerializedName("messages")
        @Expose
        private List<MessageItem> messages = null;

        public MessageResponse(String response) {
            try {
                JSONObject obj = new JSONObject(response);

                // get conversation_id
                this.conversation_id = obj.getString("conversation_id");

                ArrayList<MessageItem> messageList = new ArrayList<>();

                JSONArray dataArray  = obj.getJSONArray("messages");
                for (int i = 0; i < dataArray.length(); i++) {

                    MessageItem messageItem = new MessageItem();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    messageItem.setSender_id(dataobj.getInt("sender_id"));
                    messageItem.setMessage(dataobj.getString("message"));
                    messageItem.setCreated_at(dataobj.getInt("created_at"));

                    messageList.add(messageItem);
                }

                this.messages = messageList;

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void setConversation_id(String conversation_id) {
            this.conversation_id = conversation_id;
        }

        public String getConversation_id() {
            return this.conversation_id;
        }

        public List<Message> getAllMessages() {
            ArrayList<Message> results = new ArrayList<>();
            int num_item = messages.size();

            for(int i = num_item - 1; i > 0; i--) {
                MessageItem ith_message = messages.get(i);
                results.add(new Message(Integer.valueOf(conversation_id),
                        ith_message.getSender_id(), ith_message.getMessage()));
            }

            return results;
        }
    }

    class MessageItem {

        @SerializedName("sender_id")
        @Expose
        private Integer sender_id;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("created_at")
        @Expose
        private Integer created_at;

        public Integer getSender_id() {
            return sender_id;
        }

        public void setSender_id(Integer sender_id) {
            this.sender_id = sender_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Date getCreated_at() {
            return new Date((long) created_at*1000);
        }

        public void setCreated_at(Integer created_at) {
            this.created_at = created_at;
        }
    }

}
