package com.example.tinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tinder.editinfor.EditInforFragment;
import com.example.tinder.message_box.MessageBoxFragment;
import com.example.tinder.profile.ProfileFragment;
import com.example.tinder.search_friend.SearchFriendFragment;
import com.example.tinder.login.LoginFragment;
import com.example.tinder.profile.ProfileContainerFragment;
import com.example.tinder.userinfor.UserInforFragment;

public class MainActivity extends AppCompatActivity
                        implements  LoginFragment.OnFragmentInteractionListener,
                                    SearchFriendFragment.OnFragmentInteractionListener,
                                    MessageBoxFragment.OnFragmentInteractionListener,
                                    ProfileFragment.OnFragmentInteractionListener,
                                    UserInforFragment.OnFragmentInteractionListener,
                                    EditInforFragment.OnFragmentInteractionListener,
                                    ProfileContainerFragment.OnFragmentInteractionListener{

    public static final int RC_REQUEST_PERMISSION = 999;

    private MainPagerAdapter mainPagerAdapter;
    private ViewPager pgHome;

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
        Fragment[] listFragment = new Fragment[3];
        listFragment[0] = new ProfileContainerFragment();
        listFragment[1] = new SearchFriendFragment();
        listFragment[2] = new MessageBoxFragment();

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), listFragment);
        pgHome = findViewById(R.id.pgHome);
        pgHome.setAdapter(mainPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pgHome);
        tabLayout.setSelectedTabIndicatorHeight(0);

        // add custome tab item
        int[] imageResId = {
                R.drawable.ic_person_gray_state,
                R.drawable.ic_search_friend_state,
                R.drawable.ic_message_state };

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
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
