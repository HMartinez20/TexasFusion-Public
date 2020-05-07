/**
 * LocationFragment.kt
 *
 * Handles UI fuctionalities and connection to bindings and view models.
 * Handles the events related to updating the Google Map preview and
 * updating the location on Firebase Realtime Database.
 **/
package com.example.texasfusionpublic.ui.info

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mViewModel: InfoViewModel
    private lateinit var binding: FragmentLocationBinding
    private lateinit var database: DatabaseReference
    private var mapView: MapView? = null
    private var LatLng: LatLng = LatLng(29.41, -98.475)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProviders.of(this).get(InfoViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_location,container,false)

        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        // Update the map preview after the enter button is pressed on keyboard
        binding.address.setOnKeyListener(View.OnKeyListener { _, keyCode, keyevent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyevent.action == KeyEvent.ACTION_UP) {
                updateMap(binding.address.text.toString())
                return@OnKeyListener true
            }
            false
        })

        // Handle the apply button click
        binding.btnApply.setOnClickListener{
            if(!binding.address.text.toString().isNullOrEmpty())
                updateLocation(binding.address.text.toString())
            else
                Toast.makeText(this.context, "Please enter an address", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    // Update the marker on the map preview
    private fun updateMap(addr: String){
        val coder = Geocoder(context)
        val address: List<Address>?
        try {
            address = coder.getFromLocationName(addr, 1)
            if (address != null) {
                LatLng = LatLng(address[0].latitude, address[0].longitude)
                mapView?.getMapAsync(this)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    // Apply the location change with write to Firebase Realtime Database
    private fun updateLocation(addr: String){
        val coder = Geocoder(context)
        var address: MutableList<Address> = mutableListOf()
        try {
            address = coder.getFromLocationName(addr, 1)
            if (address != null) {
                LatLng = LatLng(address[0].latitude, address[0].longitude)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        database.child("location").child("lat").setValue(address[0].latitude)
        database.child("location").child("lng").setValue(address[0].longitude)
        database.child("location").child("addr").setValue(address[0].getAddressLine(0))
        database.child("location").child("city").setValue(address[0].locality)
        database.child("location").child("state").setValue(address[0].adminArea)
        database.child("location").child("zip").setValue(address[0].postalCode)
        Toast.makeText(context,"Location updated!", Toast.LENGTH_SHORT).show()
        activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_login_menu)
    }

    // Setup the map preview on load
    override fun onMapReady(googleMap: GoogleMap?) {
        val marker = MarkerOptions().position(LatLng)

        googleMap?.apply {
            clear()
            mapType = GoogleMap.MAP_TYPE_NORMAL
            addMarker(marker)
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng, 11.0f))
        }
    }

    // Functions below are required for Google Maps preview
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}