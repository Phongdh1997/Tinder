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

    public static final String REQUEST_ERROR = "REQUEST_ERROR";
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String USER_NAME_PASS_WORD_INVALID = "USER_NAME_PASS_WORD_INVALID";
    public static final String WEAK_PASSWORD = "WEAK_PASSWORD";
    public static final String BACK_PRESS = "BACK_PRESS";
    public static final String NO_LOGIN = "NO_LOGIN";

    private ArrayList<StateObserver> observers;
    private OnFirstAuthenListener onFirstAuthenListener;

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

    public void setOnFirstAuthenListener(OnFirstAuthenListener onFirstAuthenListener) {
        this.onFirstAuthenListener = onFirstAuthenListener;
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

    public void setState(int state, String message) {
        this.state = state;
        for (StateObserver observer : observers) {
            observer.onStateChange(state, message);
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
            setState(INVALID_AUTHEN, REQUEST_ERROR);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            setState(INVALID_AUTHEN, REQUEST_ERROR);
        }
    }

    public void authencationWithToken(String token) {

    }

    public void refuseAuthentication() {
        setState(UN_AUTHENTICATED, BACK_PRESS);
    }


    @Override
    public void onLoginSuccess(SigninService.SigninResponse response) {
        this.state = AUTHENTICATED;
        this.user = new User(response.getUser());
        this.user.setAuthen_token(response.getAuthToken());
        this.onFirstAuthenListener.onAuthenSuccess(response.getAuthToken());
        setState(AUTHENTICATED, LOGIN_SUCCESS);
    }

    @Override
    public void onLoginFail(int error) {
        setState(INVALID_AUTHEN, USER_NAME_PASS_WORD_INVALID);
    }

    public interface StateObserver {
        void onStateChange(int state, String message);
    }

    public interface OnFirstAuthenListener {
        void onAuthenSuccess(String authenToken);
    }
}
