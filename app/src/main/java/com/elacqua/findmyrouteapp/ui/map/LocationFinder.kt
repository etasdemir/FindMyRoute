package com.elacqua.findmyrouteapp.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.GpsStatus
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elacqua.findmyrouteapp.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class LocationFinder {

    private val _location = MutableLiveData<LatLng>()
    val location: LiveData<LatLng> = _location

    fun getLocation(activity: AppCompatActivity){
        val locationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            getLocationIfPermGranted(activity)
        } else {
            turnOnGPS(activity)
        }
    }

    private fun getLocationIfPermGranted(activity: AppCompatActivity) {
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            findLocation(activity)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun findLocation(activity: AppCompatActivity) {
        val fusedLocationFinder =
            LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationFinder.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val lastLocation = LatLng(location.latitude, location.longitude)
                _location.postValue(lastLocation)
            } else {
                val locationRequest = LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000)
                    .setFastestInterval(1000)
                    .setNumUpdates(1)
                val locationCallback = object: LocationCallback(){
                    override fun onLocationResult(result: LocationResult?) {
                        val loc = result?.lastLocation
                        loc?.let {
                            val lastLocation = LatLng(loc.latitude, loc.longitude)
                            _location.postValue(lastLocation)
                        }
                    }
                }
                fusedLocationFinder.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            }
        }
    }

    private fun turnOnGPS(activity: AppCompatActivity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder
            .setMessage(activity.getString(R.string.map_request_gps))
            .setCancelable(false)
            .setPositiveButton(activity.getString(R.string.map_request_gps_yes)) { _, _ ->
                activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(activity.getString(R.string.map_request_gps_no)) { dialog, _ ->
                dialog.cancel()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    companion object{
        const val REQUEST_LOCATION = 1
    }
}