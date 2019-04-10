package com.example.tinder.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tinder.R;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageHolder> {

    private ArrayList<String> datas;

    private static OnItemClickListener listener = null;

    public MessageListAdapter(ArrayList<String> datas) {
        this.datas = datas;
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
        return this.datas.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        private View view;

        public View getView() {
            return view;
        }

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        MessageListAdapter.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

}
