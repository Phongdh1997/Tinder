package com.example.tinder.message_box;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tinder.R;

import java.util.ArrayList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;

    private ArrayList<String> mMessageList;

    public MessageChatAdapter() {
        // empty constructor
        this.mMessageList = new ArrayList<>();
    }

    public MessageChatAdapter(ArrayList<String> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (i == VIEW_TYPE_MESSAGE_SENT) {
            v = inflater.inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(v);
        }
        else if (i == VIEW_TYPE_MESSAGE_RECEIVED) {
            v = inflater.inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String message = mMessageList.get(i);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) viewHolder).bind(message);
                break;
        }

    }

    public void sendMessage(String message) {
        // add message into dataset of Apdapter
        mMessageList.add(message);

        // notify Dataset changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_MESSAGE_SENT;
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
