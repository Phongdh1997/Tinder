package com.example.tinder;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tinder.editinfor.EditInforFragment;
import com.example.tinder.home.HomeFragment;
import com.example.tinder.home.MessageBoxFragment;
import com.example.tinder.home.ProfileFragment;
import com.example.tinder.home.SearchFriendFragment;
import com.example.tinder.login.LoginFragment;
import com.example.tinder.userinfor.UserInforFragment;

public class MainActivity extends AppCompatActivity
                        implements  HomeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener,
                                    SearchFriendFragment.OnFragmentInteractionListener, MessageBoxFragment.OnFragmentInteractionListener,
                                    ProfileFragment.OnFragmentInteractionListener,
                                    UserInforFragment.OnFragmentInteractionListener, EditInforFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
