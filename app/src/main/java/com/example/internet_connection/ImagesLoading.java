package com.example.internet_connection;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImagesLoading extends AsyncTask<Void, Object[], Void> {
    private int userId;
    private OnImageLoadDoneListener onImageLoadDoneListener;

    public ImagesLoading(int userId, OnImageLoadDoneListener onImageLoadDoneListener) {
        this.userId = userId;
        this.onImageLoadDoneListener = onImageLoadDoneListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 1; i < 6; i++) {
            try {
                InputStream is = (InputStream) new URL(User.getImageUrl(userId, i)).getContent();
                Drawable d = Drawable.createFromStream(is, "avatar");
                publishProgress(new Object[]{d, i});
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[]... drawable) {
        super.onProgressUpdate(drawable);
        onImageLoadDoneListener.onImageLoadDone((Drawable) drawable[0][0], (int) drawable[0][1]);
    }
}
