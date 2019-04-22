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
import com.example.tinder.message_box.MessageBoxFragment;
import com.example.tinder.message_box.MessageChatFragment;
import com.example.tinder.profile.ProfileFragment;
import com.example.tinder.search_friend.SearchFriendFragment;
import com.example.tinder.login.LoginFragment;
import com.example.tinder.signup.SignUpFragment;
import com.example.tinder.userinfor.UserInforFragment;

import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        SearchFriendFragment.OnFragmentInteractionListener,
        MessageBoxFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        UserInforFragment.OnFragmentInteractionListener,
        EditInforFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        MessageChatFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener {

    public static final int RC_REQUEST_PERMISSION = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_REQUEST_PERMISSION);
        }
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        //Navigation.findNavController(this, R.id.profileNavHostFragment).navigate(R.id.action_homeFragment_to_loginFragment);
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
