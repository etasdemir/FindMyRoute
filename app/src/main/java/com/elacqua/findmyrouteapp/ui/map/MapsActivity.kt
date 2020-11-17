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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_maps.*

private const val FIRST_STATE = 0
private const val ADD_STATE = 1

@AndroidEntryPoint
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
            state = if (state == FIRST_STATE) {
                handleFirstState()
                ADD_STATE
            } else {
                handleAddState()
                FIRST_STATE
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

    private fun handleFirstState() {
        initMarker()
        btn_map_add_location.setImageResource(R.drawable.ic_check_48)
    }

    private fun handleAddState() {
        btn_map_add_location.run {
            setImageResource(R.drawable.ic_add_48)
            visibility = View.GONE
        }
        val args =
            bundleOf(getString(R.string.save_location_key) to currentMarker.position )
        openSaveLocationFragment(args)
    }

    private fun initMarker() {
        if (::mMap.isInitialized) {
            addMarketAtCenter()
            setMarkerClickListener()
            setMarkerDragListener()
            backStackListener()
        }
    }

    private fun addMarketAtCenter() {
        val centerPosition = mMap.cameraPosition.target
        currentMarker = mMap.addMarker(
            MarkerOptions()
                .position(centerPosition)
        )
    }

    private fun setMarkerClickListener() {
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

    private fun setMarkerDragListener() {
        mMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragStart(p0: Marker?) {
            }

            override fun onMarkerDrag(p0: Marker?) {
            }

            override fun onMarkerDragEnd(marker: Marker?) {
                marker?.let { newMarker ->
                    currentMarker = newMarker
                }
            }
        })
    }

    private fun openSaveLocationFragment(args: Bundle) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.run {
            add(
                R.id.fragment_container,
                SaveLocationFragment::class.java,
                args,
                "SaveLocationFragment"
            )
            addToBackStack("SaveLocationFragment")
            commit()
        }
    }

    private fun backStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            val lastItem = 0
            if (count == lastItem) {
                currentMarker.remove()
                btn_map_add_location.visibility = View.VISIBLE
            }
        }
    }
}