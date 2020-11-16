package com.elacqua.findmyrouteapp.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elacqua.findmyrouteapp.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        removeActionBar()
        getMap()
    }

    private fun removeActionBar() {
        this.supportActionBar?.hide()
    }

    private fun getMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val centerPosition = mMap.cameraPosition.target
        val currentMarker = mMap.addMarker(
            MarkerOptions()
                .position(centerPosition)
        )

        setMarkerClickListener(currentMarker)
        setMarkerDragListener()
    }

    private fun setMarkerClickListener(currentMarker: Marker) {
        mMap.setOnMarkerClickListener{ marker ->
            if (marker == currentMarker){
                markerClicked(marker)
            }
            false
        }
    }

    private fun markerClicked(marker: Marker) {
        marker.isDraggable = true
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
    }

    private fun setMarkerDragListener() {
        mMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragStart(marker: Marker?) {
            }

            override fun onMarkerDrag(marker: Marker?) {
            }

            override fun onMarkerDragEnd(marker: Marker?) {
                Timber.e("marker position: ${marker?.position}")
            }
        })
    }


}