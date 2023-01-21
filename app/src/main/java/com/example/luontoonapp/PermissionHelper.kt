package com.example.luontoonapp

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.ContentValues.TAG
import android.util.Log

class PermissionHelper(private val activity: Activity) {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(
                    activity,
                    "Location is required to function properly",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            }
            } else {
            Log.d(TAG,"Permissions granted already")
        }// Permission already given
    }
}