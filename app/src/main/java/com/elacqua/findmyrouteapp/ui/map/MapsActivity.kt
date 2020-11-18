package com.elacqua.findmyrouteapp.ui.map

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.data.local.model.Place
import com.elacqua.findmyrouteapp.ui.location.SaveLocationFragment
import com.elacqua.findmyrouteapp.util.FragmentState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.map_menu.*

private const val FIRST_STATE = 0
private const val ADD_STATE = 1

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MapsViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var currentMarker: Marker
    private lateinit var mAdapter: PlaceRecyclerAdapter
    private var state = FIRST_STATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        removeActionBar()
        getMap()
        initAdapter()
        initRecyclerView()

        btn_map_add_location.setOnClickListener {
            handleState()
        }

        observePlaces()
        handleBackStack()
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

    private fun initAdapter() {
        mAdapter = PlaceRecyclerAdapter(
            object : OnPlaceSelectedListener {
                override fun onDetailClicked(place: Place) {
                    val key = getString(R.string.save_location_place_key)
                    val key2 = getString(R.string.fragment_state)
                    val args = bundleOf(key to place, key2 to FragmentState.NOT_EDITABLE_STATE)
                    openSaveLocationFragment(args)
                }

                override fun onLocationClicked(place: Place) {
                    val pos = LatLng(place.latitude, place.longitude)
                    addMarkerAtPositionAndMoveCamera(pos)
                }
            }
        )
    }

    private fun addMarkerAtPositionAndMoveCamera(pos: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(pos)
        )
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                pos,
                12f
            )
        )
    }

    private fun initRecyclerView() {
        rv_map.run {
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }

    private fun observePlaces() {
        viewModel.places.observe(this, { placeList ->
            mAdapter.clearAndAddPlaces(placeList)
        })
    }

    private fun handleState() {
        state = if (state == FIRST_STATE) {
            handleFirstState()
            ADD_STATE
        } else {
            handleAddState()
            FIRST_STATE
        }
    }

    private fun handleFirstState() {
        initMarker()
        btn_map_add_location.setImageResource(R.drawable.ic_check_48)
    }

    private fun handleAddState() {
        btn_map_add_location.setImageResource(R.drawable.ic_add_48)
        val args =
            bundleOf(getString(R.string.save_location_key) to currentMarker.position)
        openSaveLocationFragment(args)
    }

    private fun initMarker() {
        if (::mMap.isInitialized) {
            addMarketAtCenter()
            setMarkerClickListener()
            setMarkerDragListener()
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
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
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
        btn_map_add_location.visibility = View.GONE
        recycler_menu.visibility = View.GONE
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

    private fun handleBackStack() {
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            val lastItem = 0
            if (count == lastItem) {
                btn_map_add_location.visibility = View.VISIBLE
                recycler_menu.visibility = View.VISIBLE
                state = FIRST_STATE
                btn_map_add_location.setImageResource(R.drawable.ic_add_48)
                if (::currentMarker.isInitialized){
                    currentMarker.remove()
                }
            }
        }
    }

}