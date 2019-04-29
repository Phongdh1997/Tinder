package com.example.tinder.message_box;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.Message;
import com.example.tinder.R;
import com.example.internet_connection.SocketIO;
import com.example.tinder.authentication.UserAuth;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.navigation.NavType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MessageChatAdapter messageChatAdapter;
    private Button sendButton;
    private EditText messageText;
    private OnFragmentInteractionListener mListener;
    private String CHANNEL_ID = "notification_recevied_message";
    private static SocketIO mSocket;

    public MessageChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String authenToken = UserAuth.getInstance().getUser().getAuthen_token();
        // init socket
        mSocket = new SocketIO("http://167.99.69.92:8889", authenToken);
        mSocket.establish_connection();

        // listen new message
        mSocket.getInstance().on("chat_message", onNewMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendButton = view.findViewById(R.id.button_chatbox_send);

        messageText = view.findViewById(R.id.edittext_chatbox);

        recyclerView = view.findViewById(R.id.reyclerview_message_list);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        if (messageChatAdapter == null) {
            messageChatAdapter = new MessageChatAdapter();
        }
        recyclerView.setAdapter(messageChatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageText.getText().toString();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                int USER_ID = UserAuth.getInstance().getUser().getId();
                int conversation_id = 4;
                addMessage(USER_ID, conversation_id, msg);
                messageText.setText("");

                // create data object
                JSONObject message = new JSONObject();
                try {

                    message.put("sender_id", USER_ID);
                    message.put("conversation_id", conversation_id);
                    message.put("message", msg);
                } catch (JSONException e) {
                    Log.e("JSOn exception", e.toString());
                }

                mSocket.push_data(message, "chat_message");
            }
        });
    }

    private void addMessage(Integer user_id, Integer conversation_id, String message) {
        Message new_message = new Message(user_id, conversation_id, message);
        messageChatAdapter.addMessage(new_message);
    }


    // receving a new message
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // get information from received message
                        Integer sender_id;
                        Integer conversation_id;
                        String message;
                        try {
                            JSONObject data = new JSONObject(args[0].toString());
                            sender_id = data.getInt("sender_id");
                            conversation_id = data.getInt("conversation_id");
                            message = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }

                        // create notification
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_message)
                                .setContentTitle("Tin nhắn mới từ Tinder")
                                .setContentText(message)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true)
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

                        // show notification
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

                        // notificationId is a unique int for each notification that you must define
                        int notificationId = 456;
                        notificationManager.notify(notificationId, builder.build());

                        // add message to view
                        addMessage(sender_id, conversation_id, message);
                    }
                });
            }

        }
    };


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
