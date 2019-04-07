package com.example.tinder;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tinder.home.HomeFragment;
import com.example.tinder.login.LoginFragment;

public class MainActivity extends AppCompatActivity
                        implements  HomeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
