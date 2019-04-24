package com.example.tinder;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.common.OnBackPressEvent;
import com.example.tinder.authentication.UserAuth;
import com.example.tinder.editinfor.EditInforFragment;
import com.example.tinder.home.HomeFragment;
import com.example.tinder.message_box.MessageBoxFragment;
import com.example.tinder.message_box.MessageChatFragment;
import com.example.tinder.profile.ProfileFragment;
import com.example.tinder.search_friend.SearchFriendFragment;
import com.example.tinder.login.LoginFragment;
import com.example.tinder.signup.SignUpFragment;
import com.example.tinder.userinfor.UserInforFragment;

import java.util.List;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
    private final String USER_TOKEN = "USER_TOKEN";
    private final String USER_TOKEN_DEFAULT = "NULL";

    private UserAuth userAuth;

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
        checkLogin();
    }

    private void checkLogin() {
        // check login
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String userToken = sharedPreferences.getString(USER_TOKEN, USER_TOKEN_DEFAULT);
        if (userToken == null || userToken.equals(USER_TOKEN_DEFAULT)) {
            userAuth.setState(UserAuth.UN_AUTHENTICATED);
            Log.d("authen", "continue");
        } else {
            userAuth.setState(UserAuth.AUTHENTICATED);
            userAuth.authencationWithToken(userToken);
        }


    }

    private void addEvents() {

    }

    private void updateUI() {

    }

    private void addControls() {
        userAuth = UserAuth.getInstance();

        // add controls


        // listen authen state changed
        userAuth.addStateObserver(new UserAuth.StateObserver() {
            @Override
            public void onStateChange(int state) {
                switch (state){
                    case UserAuth.AUTHENTICATED:
                        updateUI();
                        break;
                    default:
                        Navigation.findNavController(MainActivity.this, R.id.profileNavHostFragment).navigate(R.id.action_homeFragment_to_loginFragment);
                }
            }
        });
        userAuth.setOnFirstAuthenListener(new UserAuth.OnFirstAuthenListener() {
            @Override
            public void onAuthenSuccess(String authenToken) {
                saveAthenToken(authenToken);
            }
        });
    }

    private void saveAthenToken(String authenToken) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, authenToken);
        editor.apply();
        Log.d("save", "saveAthenToken: ");
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
        String path = uri.getPath();
        if (path != null && path.equals("ExitApp")) {
            finishAffinity();
            System.exit(0);
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            NavHostFragment navHostFragment = (NavHostFragment) fragments.get(0);
            Fragment currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (currFragment instanceof OnBackPressEvent) {
                Log.d("child call", "back");
                if (!((OnBackPressEvent) currFragment).onBackPress()) {
                    return;
                }
            }
        }
        Log.d("parent", "press");
        super.onBackPressed();
    }
}
