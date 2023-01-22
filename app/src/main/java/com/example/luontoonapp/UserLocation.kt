package com.example.luontoonapp

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.telephony.CarrierConfigManager.Gps
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.checkSelfPermission
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MyLocationListener : LocationListener {

    override fun onLocationChanged(location: Location) {
        // Code to execute when the location changes
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Code to execute when the status of the location provider changes
    }
    override fun onProviderEnabled(provider: String) {
        // Code to execute when the location provider is enabled
    }
    override fun onProviderDisabled(provider: String) {
        // Code to execute when the location provider is disabled
    }
}

class UserLocation(private val context: Context) {
    private lateinit var userMarker: Marker
    private var locationManager: LocationManager? = null
    fun startTracking(mapView: MapView) {
        userMarker = Marker(mapView)
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        userMarker.setIcon(context.resources.getDrawable(R.drawable.baseline_person_pin_circle_24))
        userMarker.title = "My Location"
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val locationListener = LocationListener {
            override fun onLocationChanged(location: Location?) {
                userMarker.position = GeoPoint(location!!.latitude, location.longitude)
            }
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        if (locationManager != null) {
            if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            }
        }
        mapView.overlays.add(userMarker)
    }
}