package com.example.tinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.common.OnBackPressEvent;
import com.example.model.User;
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

    protected LocationManager locationManager;
    protected LocationListener locationListener;

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

        getLocationUser();
    }

    private void getLocationUser() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Toast.makeText(getBaseContext(), "onChangeLocation: " + location, Toast.LENGTH_LONG).show();
                    if (userAuth.getState() != UserAuth.AUTHENTICATED) {
                        Double longitude = location.getLongitude();
                        Double latitude = location.getLatitude();
                        PostCurrLocation(longitude.toString(), latitude.toString());
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            return;
        }
    }

    private void PostCurrLocation(String longitude, String latitude) {
        // to do
    }

    private void checkLogin() {
        // check login
        User user = User.getLocalUser(getPreferences(MODE_PRIVATE));
        if (user == null) {
            userAuth.setState(UserAuth.UN_AUTHENTICATED, UserAuth.NONE);
            Log.d("authen", "continue");
        } else {
            userAuth.setState(UserAuth.AUTHENTICATED, UserAuth.NONE);
            userAuth.setUser(user);
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
            public void onStateChange(int state, int messageCode) {
                switch (state){
                    case UserAuth.AUTHENTICATED:
                        updateUI();
                        break;
                    case UserAuth.UN_AUTHENTICATED:
                        Navigation.findNavController(MainActivity.this, R.id.profileNavHostFragment).navigate(R.id.action_homeFragment_to_loginFragment);
                    default:
                        break;
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
        User user = UserAuth.getInstance().getUser();
        user.storeToLocal(getPreferences(MODE_PRIVATE).edit());
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
