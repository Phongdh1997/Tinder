package com.example.tinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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

    public static final int RC_REQUEST_PERMISSION = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_REQUEST_PERMISSION) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "App can't run without READ EXTERNAL STORAGE permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
