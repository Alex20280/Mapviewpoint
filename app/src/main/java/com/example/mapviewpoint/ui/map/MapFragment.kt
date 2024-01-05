package com.example.mapviewpoint.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mapviewpoint.R
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.extentions.openScreen
import com.example.mapviewpoint.extentions.viewBinding
import com.example.mapviewpoint.databinding.FragmentMapBinding
import com.example.mapviewpoint.di.ViewModelFactory
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.utils.PermissionsHelper
import com.example.mapviewpoint.utils.PermissionsHelper.REQUEST_CODE_LOCATION_PERMISSION
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MapFragment : Fragment(R.layout.fragment_map)  {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private lateinit var map: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    private var actionToPerformAfterPermissionGranted: (() -> Unit)? = null

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

/*        val sydney = LatLng(-37.0, 153.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))*/
        //getMyCurrentLocation()
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)

        injectDependencies()
        observeDatePicker()
        observeMyCurrentLocation()
        iconClickListener()
        currentLocationListener()
        observeTwentyFourHoursCoordinates()
        observeChosenDateCoordinates()
        observeUserLogout()
    }

    private fun checkLocationPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            actionToPerformAfterPermissionGranted?.invoke()
        }
    }


    private fun observeUserLogout() {
        sharedViewModel.getLogOutState.observe(viewLifecycleOwner) { result ->
            if (findNavController().currentDestination?.id == R.id.mapFragment) {
                when (result) {
                    is RequestResult.Success -> {
                        sharedViewModel.setLogOutState(RequestResult.Loading)
                        navigateToSignInPage()
                        resetMap()
                    }
                    is RequestResult.Error -> {
                        Toast.makeText(requireContext(), "Error with logging out", Toast.LENGTH_LONG).show()
                    }
                    is RequestResult.Loading -> Unit
                }
            }
        }
    }


    private fun observeMyCurrentLocation() {
        mapViewModel.getCurrentCoordinates().observe(viewLifecycleOwner){
            updateMap(it)
        }
    }

    private fun currentLocationListener() {
        binding.fab.setOnClickListener {
            if (checkLocationPermissions()) {
                val isGpsEnabled = mapViewModel.isLocationEnabled()
                if (!isGpsEnabled) {
                    locationServiceDialogShow()
                }
                getMyCurrentLocation()
            } else {
                requestLocationPermissions()
                actionToPerformAfterPermissionGranted = ::getMyCurrentLocation
            }
        }
    }

    private fun locationServiceDialogShow() {
        AlertDialog.Builder(requireContext())
            .setTitle("GPS Disabled")
            .setMessage("Please enable location services to allow tracking")
            .setPositiveButton("Enable") { _, _ ->
                mapViewModel.requestLocationEnable()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
        //map.clear()
        resetMap()

/*        coordinates.forEach() {
            map.addMarker(MarkerOptions().position(it))
        }*/
        coordinates.forEach { coord ->
            val marker = map.addMarker(MarkerOptions().position(coord).title("Marker"))
            marker?.isFlat = true
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates.first(), 16f))
    }

    private fun resetMap(){
        map.clear()
    }

    //TODO should be removed (only for debugging)
/*    private fun reduceCoordinateDensity(originalCoords: List<LatLng>, distance: Double): List<LatLng> {

        val filteredCoords = ArrayList<LatLng>()
        var prevCoord: LatLng? = null

        originalCoords.forEach { coord ->
            if (prevCoord == null || SphericalUtil.computeDistanceBetween(prevCoord,coord) >= distance) {
                filteredCoords.add(coord)
                prevCoord = coord
            }
        }

        return filteredCoords
    }*/

    fun GpsCoordinates.toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    private fun observeChosenDateCoordinates() {
        mapViewModel.getChosenDateCoordinates().observe(viewLifecycleOwner){
            Log.d("MyOnwObserver", "Coordinates changed: $it")
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
            if (checkLocationPermissions()) {
                getGpsCoordinatesByLast24Hours()
            } else {
                requestLocationPermissions()
                actionToPerformAfterPermissionGranted = ::getGpsCoordinatesByLast24Hours
            }
        }
    }

    private fun getGpsCoordinatesByLast24Hours(){
        CoroutineScope(Dispatchers.IO).launch {
            mapViewModel.getGpsCoordinatesByLast24Hours()
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

    private fun injectDependencies() {
        (requireContext().applicationContext as App).appComponent.inject(this)
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        sharedViewModel = viewModelProvider.get(SharedViewModel::class.java)
    }

}