package com.example.tinder.message_box;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.Conversation;
import com.example.tinder.R;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageHolder> {

    private ArrayList<Conversation> conversations;

    private static OnItemClickListener listener = null;

    public MessageListAdapter(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    public MessageListAdapter(Conversation new_conversation) {
        this.conversations.add(new_conversation);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageHolder messageHolder, int i) {

        // add item click event
        messageHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MessageListAdapter.listener != null) {
                    MessageListAdapter.listener.onClick(messageHolder.view, messageHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.conversations.size();
    }


    public static class MessageHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView lasted_msg;
        private TextView user_name;

        public View getView() {
            return view;
        }

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lasted_msg = itemView.findViewById(R.id.lasted_msg_txtView);
            user_name = itemView.findViewById(R.id.user_name_txtView);
            setLastedMessage("Lasted message");
        }

        public void setLastedMessage(String message) {
            lasted_msg.setText(message);
        }

        public void setUsername(String username) {
            user_name.setText(username);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        MessageListAdapter.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public void updateConversation(int position, String message) {
        Conversation conversation = this.conversations.get(position);
        conversation.setLasted_message(message);
        notifyDataSetChanged();
    }

}
