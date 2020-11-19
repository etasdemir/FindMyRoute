package com.elacqua.findmyrouteapp.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.findmyrouteapp.data.local.LocalRepository
import com.elacqua.findmyrouteapp.data.local.entity.Place
import com.elacqua.findmyrouteapp.data.remote.RemoteRepository
import com.elacqua.findmyrouteapp.data.remote.model.Coordinates
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MapsViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _decodedPolyline = MutableLiveData<List<LatLng>>()
    val decodedPolyline: LiveData<List<LatLng>> = _decodedPolyline

    val places: LiveData<List<Place>> = getAllPlaces()

    init {
        deleteAllPlaces()
//        TODO()
    }

    private fun getAllPlaces() =
        localRepository.getAllPlacesByUsername()

    fun findPath(places: List<Place>, location: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val longLats = ArrayList<ArrayList<Double>>()
            longLats.add(arrayListOf(location.longitude, location.latitude))
            for (index in places.indices) {
                longLats.add(arrayListOf(places[index].longitude, places[index].latitude))
            }
            longLats.add(arrayListOf(location.longitude, location.latitude))
            val coordinates = Coordinates(longLats)
            val result  = remoteRepository.getRoutes(coordinates)
            _decodedPolyline.postValue(result)
        }
    }

    fun deleteAllPlaces(){
        viewModelScope.launch {
            localRepository.deleteAllPlaces()
        }
    }
}