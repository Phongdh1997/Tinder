package com.example.tinder.message_box;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.internet_connection.SocketIO;

import com.example.model.Conversation;
import com.example.tinder.R;
import com.example.tinder.authentication.UserAuth;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageBoxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MessageBoxFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvMatchList;
    private RecyclerView rvMessageList;

    private MessageListAdapter messageListAdapter;

    private SocketIO mSocket;

    public MessageBoxFragment() {
        // Required empty public constructor
    }

    public static MessageBoxFragment newInstance(String param1, String param2) {
        MessageBoxFragment fragment = new MessageBoxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_box, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        addEvents(view);

        // init Socket
        mSocket = UserAuth.getInstance().getSocketIO();

        // listen new message
        mSocket.getInstance().on("chat_message", onNewMessage);
    }

    private void addEvents(final View view) {
        // onClick event
        messageListAdapter.setOnItemClickListener(new MessageListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.d("Navigate: ", "navigate " + position);
                long conversation_id = messageListAdapter.getItemId(position);
                Toast.makeText(getActivity(), "Onclick on conversation_id " + Long.toString(conversation_id),
                        Toast.LENGTH_LONG).show();

                // pass information to other fragment
                Bundle bundle = new Bundle();
                bundle.putInt("conversation_id", (int) conversation_id);

                NavHostFragment.findNavController(MessageBoxFragment.this).navigate(R.id.action_homeFragment_to_messageChatFragment, bundle);
            }
        });
    }

    private void addControls(View view) {
        rvMatchList = view.findViewById(R.id.rvMatchList);
        rvMessageList = view.findViewById(R.id.rvMessageList);

        rvMatchList.setNestedScrollingEnabled(true);
        rvMessageList.setNestedScrollingEnabled(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMatchList.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessageList.setLayoutManager(layoutManager);
        rvMessageList.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        // fetch conversations from the server
        ArrayList<Conversation> conversations = loadConversations();

        MatchListAdapter adapter = new MatchListAdapter(conversations);
        rvMatchList.setAdapter(adapter);

        messageListAdapter = new MessageListAdapter(conversations);
        rvMessageList.setAdapter(messageListAdapter);
    }

    public void updateConversation(int position, String message) {
        messageListAdapter.updateConversation(position, message);
        messageListAdapter.notifyDataSetChanged();
    }

    public ArrayList<Conversation> loadConversations() {
        // TODO: create socketIO to get all conversations of the current user
        ArrayList<Conversation> all_conversations = new ArrayList<>();

        Conversation conversation;
        // just for development
        for(int i = 1; i <= 3; i++) {
            conversation = new Conversation(i, 1, i+1);
            all_conversations.add(conversation);
        }

        return all_conversations;
    }

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
                        Log.i("Message box fragment", message);
                        // update UI
                        messageListAdapter.updateConversation(conversation_id, message);
                        messageListAdapter.notifyDataSetChanged();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
