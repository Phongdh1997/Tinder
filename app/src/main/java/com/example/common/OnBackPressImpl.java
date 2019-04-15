package com.example.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.tinder.R;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class OnBackPressImpl {
    private NavHostFragment navHostFragment;

    public OnBackPressImpl(NavHostFragment navHostFragment) {
        this.navHostFragment = navHostFragment;
    }

    public boolean onBackPress() {
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            return navController.navigateUp();
        }
        return false;
    }
}
