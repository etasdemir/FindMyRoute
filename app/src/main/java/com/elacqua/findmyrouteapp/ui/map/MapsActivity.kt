package com.elacqua.findmyrouteapp.ui.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.ui.location.SaveLocationFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import timber.log.Timber

private const val FIRST_STATE = 0
private const val ADD_STATE = 1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var currentMarker: Marker
    private var state = FIRST_STATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        removeActionBar()
        getMap()

        btn_map_add_location.setOnClickListener {
            if (state == FIRST_STATE){
                changeStateToAddState()
            } else {
                changeStateToFirstState()
            }
        }
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
        mMap.uiSettings.isMapToolbarEnabled = false
    }

    private fun changeStateToAddState() {
        initMarker()
        btn_map_add_location.setImageResource(R.drawable.ic_check_48)
        state = ADD_STATE
    }

    private fun changeStateToFirstState() {
        Timber.e("marker position: ${currentMarker.position}")
        btn_map_add_location.visibility = View.GONE
        openSaveLocationFragment()
        btn_map_add_location.setImageResource(R.drawable.ic_add_48)
        state = FIRST_STATE
    }

    private fun initMarker() {
        if (::mMap.isInitialized){
            addMarketAtCenter()
            setMarkerClickListener(currentMarker)
            backStackListener(currentMarker)
        }
    }

    private fun addMarketAtCenter() {
        val centerPosition = mMap.cameraPosition.target
        currentMarker = mMap.addMarker(
            MarkerOptions()
                .position(centerPosition)
        )
    }

    private fun setMarkerClickListener(currentMarker: Marker) {
        mMap.setOnMarkerClickListener { marker ->
            if (marker == currentMarker) {
                setMarkerDraggableAndChangeColor(marker)
            }
            false
        }
    }

    private fun setMarkerDraggableAndChangeColor(marker: Marker) {
        marker.run {
            isDraggable = true
            setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        }
    }

    private fun openSaveLocationFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val args = bundleOf()
        transaction.run {
            add(R.id.fragment_container, SaveLocationFragment::class.java, args, "SaveLocationFragment")
            addToBackStack("SaveLocationFragment")
            commit()
        }
    }

    private fun backStackListener(currentMarker: Marker) {
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            val lastItem = 0
            if (count == lastItem){
                currentMarker.remove()
                btn_map_add_location.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}