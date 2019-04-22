package com.example.tinder.message_box;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinder.R;
import com.example.model.Message;

import java.util.ArrayList;

public class MessageChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;

    private ArrayList<Message> mMessageList;

    public MessageChatAdapter() {
        // empty constructor
        this.mMessageList = new ArrayList<>();
    }

    public MessageChatAdapter(ArrayList<Message> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = null;
        RecyclerView.ViewHolder viewHolder = null;
        Message messages = mMessageList.get(i-1);
        int user_id = messages.getSender_id();
        if (user_id == VIEW_TYPE_MESSAGE_SENT) {
            v = inflater.inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(v);
        }
        else {
            v = inflater.inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message message = mMessageList.get(i);
        int user_id = message.getSender_id();
        switch (user_id) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(message.getMessage());
                break;
            default:
                ((ReceivedMessageHolder) viewHolder).bind(message.getMessage());
                break;
        }

    }

    public void addMessage(Message message) {
        // add message into dataset of Apdapter
        mMessageList.add(message);

        // notify Dataset changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);
        int user_id = message.getSender_id();

        if (user_id == VIEW_TYPE_MESSAGE_SENT) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return this.mMessageList.size();
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
        }

        void bind(String message) {
            messageText.setText(message);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
        }

        void bind(String message) {
            messageText.setText(message);
        }
    }

}
