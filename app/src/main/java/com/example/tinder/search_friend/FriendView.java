package com.example.tinder.search_friend;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.internet_connection.SocketIO;
import com.example.model.User;
import com.example.tinder.R;
import com.example.tinder.authentication.UserAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendView extends ConstraintLayout {

    private User friend;

    private TextView txtName;
    private ImageButton btnDetailInfo;
    private SocketIO mSocket;

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
        updateUI();
    }

    public FriendView(Context context, User friend) {
        super(context);
        this.friend = friend;
        initView();

        // init SocketIO
        String authenToken = UserAuth.getInstance().getUser().getAuthen_token();
        // init socket
        mSocket = new SocketIO("http://167.99.69.92:8889", authenToken);
        mSocket.establish_connection();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_search_friend_item, this, false);
        addView(view);

        addControls();
        addEvents();
    }

    private void addControls() {
        // add controls
        txtName = findViewById(R.id.txtName);
        btnDetailInfo = findViewById(R.id.btnDetailInfo);

        updateUI();
    }

    private void updateUI() {
        if (friend == null) {
            return;
        }
        txtName.setText(friend.getName());
    }

    private void addEvents() {
        // add event
        Bundle user = new Bundle();
        if (friend != null) {
            user.putInt("id", friend.getId());
            user.putString("authen_token", friend.getAuthen_token());
            user.putString("phone", friend.getPhone());
            user.putString("mail", friend.getMail());
            user.putString("password", friend.getPassword());
            user.putString("name", friend.getName());
            user.putString("decription", friend.getDecription());
            user.putString("gender", friend.getGender());
            user.putInt("age", friend.getAge());
        }
        btnDetailInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userInforFragment, user));
    }

    /**
     * Description: current user perform like this friend. Invoked when user click like this user
     * @param currUserId: id of current user who like this friend
     */
    public void likeFriend(int currUserId) {
        if (friend == null) {
            return;
        }
        int friendId = friend.getId();
        Log.d("friend", "id = " + friendId);
        Toast.makeText(getContext(), "Like event", Toast.LENGTH_SHORT).show();

        JSONObject data = new JSONObject();
        try {
            // current user_id
            data.put("liker_id", currUserId);

            // liked user_id
            data.put("liked_id", friendId);
        } catch (JSONException e) {
            Log.e("JSON exception", e.toString());
        }


        mSocket.push_data(data, "like");
    }

}