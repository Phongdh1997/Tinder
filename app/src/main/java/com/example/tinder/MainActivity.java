package com.example.tinder;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tinder.login.signin.signin.LoginFragment;
import com.example.tinder.home.HomeFragment;

public class MainActivity extends AppCompatActivity
                        implements  HomeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
