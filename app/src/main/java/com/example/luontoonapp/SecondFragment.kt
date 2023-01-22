package com.example.luontoonapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.luontoonapp.databinding.FragmentSecondBinding
import com.example.luontoonapp.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var userLocation: UserLocation

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val permissionHelper = PermissionHelper(requireActivity())
        permissionHelper.requestLocationPermission()

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().setUserAgentValue("LuontoonApp/1.0")
        val mapView = view.findViewById<MapView>(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)
        mapView.setMinZoomLevel(3.0)
        mapView.setMaxZoomLevel(20.0)
        userLocation = UserLocation(requireContext())
        userLocation.startTracking(mapView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}