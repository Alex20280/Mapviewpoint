package com.example.mapviewpoint.ui.map

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mapviewpoint.R
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.base.openScreen
import com.example.mapviewpoint.base.viewBinding
import com.example.mapviewpoint.databinding.FragmentMapBinding
import com.example.mapviewpoint.di.ViewModelFactory
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.utils.PermissionsHelper
import com.example.mapviewpoint.utils.PermissionsHelper.REQUEST_CODE_LOCATION_PERMISSION
import com.example.mapviewpoint.utils.Utils
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MapFragment : Fragment(R.layout.fragment_map), EasyPermissions.PermissionCallbacks  {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private lateinit var map: GoogleMap

    val list = listOf<LatLng>(LatLng(49.960507447451285, 36.22328019613084), LatLng(49.961136313354544, 36.22173431057141))

    @Inject
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var sharedViewModel: SharedViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
/*        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val sydney = LatLng(-37.0, 153.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)

        viewModelInstantiation()
        requestPermissions()
        observeDatePicker()
        observeMyCurrentLocation()
        observeExitClick()
        iconClickListener()
        currentLocationListener()
        observeTwentyFourHoursCoordinates()
        observeChosenDateCoordinates()
    }

    private fun observeExitClick() {
        sharedViewModel.getExitClicked.observe(viewLifecycleOwner){
            navigateToSignInPage()
        }
    }

    private fun observeMyCurrentLocation() {
        mapViewModel.getCurrentCoordinates().observe(viewLifecycleOwner){
            updateMap(it)
        }
    }

    private fun currentLocationListener() {
        binding.fab.setOnClickListener {
            getMyCurrentLocation()
        }
    }

    private fun getMyCurrentLocation() {
       mapViewModel.getCurrentLocation()
    }


    private fun updateMap(coordinates: List<LatLng>) {
        if(coordinates.isEmpty()) {
            Toast.makeText(requireContext(), "No location coordinates found for this date", Toast.LENGTH_SHORT).show()
            return
        }
        //val reducedCoords = reduceCoordinateDensity(coordinates, 10.0)
        map.clear()

/*        coordinates.forEach() {
            map.addMarker(MarkerOptions().position(it))
        }*/
        coordinates.forEach { coord ->
            val marker = map.addMarker(MarkerOptions().position(coord))
            marker?.isFlat = true
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates.first(), 16f))
    }

    //TODO should be removed (only for debugging)
    private fun reduceCoordinateDensity(originalCoords: List<LatLng>, distance: Double): List<LatLng> {

        val filteredCoords = ArrayList<LatLng>()
        var prevCoord: LatLng? = null

        originalCoords.forEach { coord ->
            if (prevCoord == null || SphericalUtil.computeDistanceBetween(prevCoord,coord) >= distance) {
                filteredCoords.add(coord)
                prevCoord = coord
            }
        }

        return filteredCoords
    }

    private fun requestPermissions(){
        if (PermissionsHelper.hasLocationPermission(requireContext()))
            return

        EasyPermissions.requestPermissions(
            this,
            "You need to accept location permission",
            REQUEST_CODE_LOCATION_PERMISSION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun GpsCoordinates.toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    private fun observeChosenDateCoordinates() {
        mapViewModel.getChosenDateCoordinates().observe(viewLifecycleOwner){
            val latLngs = it.map { it.toLatLng() }
            updateMap(latLngs)

        }
    }

    private fun observeTwentyFourHoursCoordinates() {
        mapViewModel.getTwentyFourHoursCoordinates().observe(viewLifecycleOwner){
            val latLngs = it.map { it.toLatLng() }
            updateMap(latLngs)
        }
    }

    private fun iconClickListener() {
        binding.icon.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                mapViewModel.getGpsCoordinatesByLast24Hours()
            }
        }
    }

    private fun navigateToSignInPage() {
        openScreen(MapFragmentDirections.actionMapFragmentToSignInFragment())
    }


    private fun observeDatePicker() {
        sharedViewModel.selectedDate.observe(requireActivity()){
            CoroutineScope(Dispatchers.IO).launch {
                mapViewModel.getGpsCoordinatesByTime(it)
            }
        }

    }

    private fun viewModelInstantiation() {
        (requireContext().applicationContext as App).appComponent.inject(this)
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        sharedViewModel = viewModelProvider.get(SharedViewModel::class.java)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
       if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
           AppSettingsDialog.Builder(this).build().show()
       } else {
           requestPermissions()
       }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}