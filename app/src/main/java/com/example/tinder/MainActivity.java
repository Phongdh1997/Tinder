package com.example.tinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.OnBackPressEvent;
import com.example.common.UserLocation;
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

    private UserLocation userLocation;

    private Button btnGet;
    private TextView txtLatitude;
    private TextView txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void checkLocationPermission () {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    UserLocation.LOCATION_PERMISSION_REQUEST);
        } else {
            getUserLocation();
        }
    }

    private void getUserLocation() {
        Location location = userLocation.getLastLocation(LocationManager.GPS_PROVIDER);
        if (location!= null) {
            txtLatitude.setText(String.valueOf(location.getLatitude()));
            txtLongitude.setText(String.valueOf(location.getLongitude()));
        } else {
            txtLatitude.setText("not found");
            txtLongitude.setText("not found");
        }
    }

    private void addEvents() {
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });
    }

    private void addControls() {
        userLocation = new UserLocation((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        btnGet = findViewById(R.id.btnGet);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == UserLocation.LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            }
        } else {
            Log.e("Location", "Location Listener permission denied");
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
}
