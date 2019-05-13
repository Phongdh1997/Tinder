package com.example.internet_connection;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.model.User;
import com.example.tinder.search_friend.FriendView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class FriendViewAvatarLoading extends AsyncTask<Void, Void, Drawable> {

    private int userId;
    private WeakReference<FriendView> view;

    public FriendViewAvatarLoading(FriendView view, int userId) {
        this.userId = userId;
        this.view = new WeakReference<>(view);
    }

    @Override
    protected Drawable doInBackground(Void... voids) {
        for (int i = 1; i < 6; i++) {
            try {
                InputStream is = (InputStream) new URL(User.getImageUrl(userId, i)).getContent();
                Drawable d = Drawable.createFromStream(is, "avatar");
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        if (drawable == null) {
            return;
        }
        FriendView v = view.get();
        v.getImgSearchFriendAvatar().setImageDrawable(drawable);
    }
}
