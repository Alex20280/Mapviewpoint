package com.example.mapviewpoint.ui.map

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mapviewpoint.R
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.databinding.FragmentForgetPasswordBinding
import com.example.mapviewpoint.databinding.FragmentMapBinding
import com.example.mapviewpoint.di.ViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Calendar
import java.util.Timer
import javax.inject.Inject

class MapFragment : Fragment(R.layout.fragment_map)  {

    private val binding by viewBinding(FragmentMapBinding::bind)

    var toolbarIconClickListener: ToolbarIconClickListener? = null

    @Inject
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var  sharedViewModel: SharedViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInstanciation()
        observeDatePicker()
        observeButtonClick()
    }

    private fun observeButtonClick() {
        binding.iconCard.setOnClickListener{
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_LONG).show()
        }
    }


    private fun observeDatePicker() {
        sharedViewModel.selectedDate.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            Log.d("fvfdvfdvfdvfdvdf", it.toString())
        }

    }

    private fun viewModelInstanciation() {
        (requireContext().applicationContext as App).appComponent.inject(this)

    }
}