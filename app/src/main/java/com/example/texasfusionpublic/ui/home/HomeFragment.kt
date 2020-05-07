package com.example.texasfusionpublic.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.IOException

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private var mapView: MapView? = null
    private var mLatLng: LatLng = LatLng(29.41, -98.475)

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
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        mapView = binding.map
        mapView?.onCreate(savedInstanceState)

        binding.btnHours.setOnClickListener{ showMoreHours(binding.moreHours) }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach{
                    if(it.key == "HoursOfOp") {
                        binding.hoursMore.hours_more.text = mViewModel.sortData(it.value.toString())
                        binding.todayHours.text = mViewModel.getTodaysSchedule(it.value.toString())
                    }
                    if(it.key == "location"){
                        binding.Street.text = it.child("addr").value.toString().substringBefore(",")
                        binding.other.text = "${it.child("city").value}, ${it.child("state").value}, ${it.child("zip").value}"
                        updateMap(it.child("lat").value.toString(), it.child("lng").value.toString())
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) { // Failed to read value
                Log.i("HomeFragment", "Failed to read value.", error.toException())
            }
        })

        return binding.root
    }

    private fun updateMap(lat : String, lng: String){
        try {
            mLatLng = LatLng(lat.toDouble(), lng.toDouble())
            mapView?.getMapAsync(this)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun showMoreHours(view: View){
        if(view.visibility == View.VISIBLE)
            view.visibility = View.GONE
        else
            view.visibility = View.VISIBLE
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val marker = MarkerOptions().position(mLatLng).title("Texas Fusion")

        googleMap?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            addMarker(marker)
            moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 11.0f))
        }
    }

    private fun updateSchedule(schedule: MutableMap<String,String>){
        Log.i("HomeFragment", "schedule: $schedule")

    }

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