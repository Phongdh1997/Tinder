package com.example.tinder.authentication;

import android.content.SharedPreferences;

import com.example.model.User;
import com.example.rest.service.SigninService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserAuth implements User.OnLoginCallBack {
    public static final int UN_AUTHENTICATED = -1;
    public static final int AUTHENTICATED = 1;
    public static final int INVALID_AUTHEN = -100;

    private ArrayList<StateObserver> observers;

    private int state;
    private User user;

    private static UserAuth userAuth;

    public int getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public void addStateObserver(StateObserver observer) {
        observers.add(observer);
    }

    private UserAuth() {
        observers = new ArrayList<>();
        state = UN_AUTHENTICATED;
    }

    public static UserAuth getInstance() {
        if (userAuth == null) {
            userAuth = new UserAuth();
        }
        return userAuth;
    }

    public void setState(int state) {
        this.state = state;
        for (StateObserver observer : observers) {
            observer.onStateChange(state);
        }
    }

    public boolean isAuthencated() {

        return false;
    }

    public void authenticate(String email, String password) {
        User user = new User();
        user.setMail(email);
        try {
            user.setPassword(password);
            user.setOnLoginCallBack(this);
            user.login();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            setState(UN_AUTHENTICATED);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            setState(UN_AUTHENTICATED);
        }
    }

    public void authencationWithToken(String token) {

    }

    public void refuseAuthentication() {
        setState(UN_AUTHENTICATED);
    }


    @Override
    public void onLoginSuccess(SigninService.SigninResponse message) {
        this.state = AUTHENTICATED;
        this.user = new User();
        setState(AUTHENTICATED);
    }

    @Override
    public void onLoginFail(int error) {
        this.state = INVALID_AUTHEN;
        setState(INVALID_AUTHEN);
    }

    public interface StateObserver {
        void onStateChange(int state);
    }
}
