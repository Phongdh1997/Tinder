package com.example.tinder.search_friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.model.User;
import com.example.rest.service.SearchFriendService;
import com.example.tinder.R;
import com.example.tinder.authentication.UserAuth;

@SuppressLint("ViewConstructor")
public class FriendView extends ConstraintLayout implements SearchFriendData.OnDataLoadDoneListener {

    private User friend;

    private TextView txtName;
    private ImageButton btnDetailInfo;

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
        Bundle user = null;
        if (friend != null) {
            user = friend.toBundle();
        }
        btnDetailInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userInforFragment, user));
    }

    /**
     * Description: current user perform like this friend. Invoked when user click like this user.
     * @param currUserId: id of current user who like this friend
     */
    public void likeFriend(int currUserId) {
        if (friend == null) {
            return;
        }
        int friendId = friend.getId();
        Log.d("friend", "id = " + friendId);

        // TODO: call API like friend
    }

    /**
     * Description: current user perform dislike this friend.
     */
    public void dislikeFriend() {
        if (friend == null) {
            return;
        }
        Log.d("dislike friend", "id = " + friend.getId());
        if (UserAuth.getInstance().getUser() != null) {
            UserAuth.getInstance().getUser().dislikeFriend(friend.getId());
        }
    }

    public void clearData() {
        if (friend == null) {
            return;
        }
        int friendId = friend.getId();
        if (SearchFriendData.getInstance().removeDataItem(friendId)) {
            dislikeFriend();
            friend = null;
        }
        updateUI();
    }

    @Override
    public void onLoadDone() {
        if (friend != null) {
            return;
        }
        friend = SearchFriendData.getInstance().getUserData();
        updateUI();
    }
}
