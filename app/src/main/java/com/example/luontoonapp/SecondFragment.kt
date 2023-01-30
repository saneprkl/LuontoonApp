package com.example.luontoonapp

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.luontoonapp.databinding.FragmentSecondBinding
import com.example.luontoonapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val permissionHelper = PermissionHelper(requireActivity())
        permissionHelper.requestLocationPermission()

        val fusedLocationClient: FusedLocationProviderClient
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().setUserAgentValue("LuontoonApp/1.0")

        if (ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnFailureListener { println("Location not found") }
            //fusedLocationClient.lastLocation.addOnSuccessListener


            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
                override fun isCancellationRequested() = false
            }).addOnSuccessListener { location: Location ->
                if (location == null) {
                    Toast.makeText(requireContext(), "Cannot get location.", Toast.LENGTH_SHORT).show()
                } else {

                    val mapView = view.findViewById<MapView>(R.id.mapView)
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.setMultiTouchControls(true)

                    var longitude = location.longitude
                    var latitude = location.latitude
                    var userLocation = Marker(mapView)
                    var geoPoint = GeoPoint(latitude, longitude)
                    userLocation.position = geoPoint

                    var mapController = mapView.controller
                    var start = GeoPoint(latitude, longitude)
                    mapController.setCenter(start)
                    mapController.setZoom(14.5)

                    userLocation.setAnchor(Marker.ANCHOR_BOTTOM, Marker.ANCHOR_CENTER)
                    userLocation.title = "You're here!"
                    userLocation.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.baseline_person_pin_circle_24
                    )

                    mapView.overlays.add(userLocation)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}