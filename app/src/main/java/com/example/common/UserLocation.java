package com.example.common;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class UserLocation implements LocationListener {

    public static final int LOCATION_PERMISSION_REQUEST = 100;

    private LocationManager locationManager;
    private Location lastLocation;

    private OnLastPositionChanged onLastPositionChanged;

    public UserLocation(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setOnLastPositionChanged (OnLastPositionChanged onLastPositionChanged) {
        this.onLastPositionChanged = onLastPositionChanged;
    }

    @SuppressLint("MissingPermission")
    public void listenLocationUpdate(String providerType) {
        if (locationManager != null) {
            try {
                locationManager.requestLocationUpdates(providerType, 0, 0, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLastLocation(String providerType) {
        Location location = null;
        if (locationManager != null) {
            try {
                location = locationManager.getLastKnownLocation(providerType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.lastLocation = location;
            if (onLastPositionChanged != null) {
                onLastPositionChanged.onPositionChanged(location);
            }
        } else {
            Log.e("Location", "Location changed but can't get it");
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

    public interface OnLastPositionChanged {
        void onPositionChanged(Location lastLocation);
    }
}
