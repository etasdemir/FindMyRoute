package com.elacqua.findmyrouteapp.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.findmyrouteapp.data.local.LocalRepository
import com.elacqua.findmyrouteapp.data.local.entity.Place
import com.elacqua.findmyrouteapp.data.remote.RemoteRepository
import com.elacqua.findmyrouteapp.data.remote.ResultWrapper
import com.elacqua.findmyrouteapp.data.remote.ResultWrapper.*
import com.elacqua.findmyrouteapp.data.remote.model.*
import com.elacqua.findmyrouteapp.util.Utility
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _decodedPolyline = MutableLiveData<List<LatLng>>()
    val decodedPolyline: LiveData<List<LatLng>> = _decodedPolyline

    private val _genericError = MutableLiveData<GenericError>()
    val genericError: MutableLiveData<GenericError> = _genericError

    private val _networkError = MutableLiveData<NetworkError>()
    val networkError: LiveData<NetworkError> = _networkError

    val places: LiveData<List<Place>> = getAllPlaces()

    private fun getAllPlaces() =
        localRepository.getAllPlacesByUsername()

    fun findPath(places: List<Place>, location: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response: ResultWrapper<Optimization> = optimizePoints(places, location)) {
                is Success -> {
                    if (response.value.routes.isEmpty()) {
                        return@launch
                    }
                    val steps = response.value.routes[0].steps
                    val result = getRoute(steps)
                    _decodedPolyline.postValue(result)
                }
                is GenericError -> _genericError.postValue(response)
                is NetworkError -> _networkError.postValue(response)
            }
        }
    }

    private suspend fun optimizePoints(
        places: List<Place>,
        location: LatLng
    ): ResultWrapper<Optimization> {
        val jobs = ArrayList<Job>()
        var id = 1
        for (index in places.indices) {
            val jobLocation = listOf(places[index].longitude, places[index].latitude)
            val job = Job(id++, jobLocation)
            jobs.add(job)
        }
        return remoteRepository.optimizeEndPoints(jobs, location)
    }

    private suspend fun getRoute(steps: List<Step>): List<LatLng> {
        val points = ArrayList<List<Double>>()
        for (step in steps) {
            points.add(step.location)
        }
        val coordinates = Coordinates(points)
        when (val response: ResultWrapper<Direction> = remoteRepository.getRoutes(coordinates)) {
            is Success -> {
                if (response.value.polylines.isEmpty()) {
                    return emptyList()
                }
                val polyline = response.value.polylines[0].geometry
                return Utility.decodePolyline(polyline)
            }
            is GenericError -> _genericError.postValue(response)
            is NetworkError -> _networkError.postValue(response)
        }
        return emptyList()
    }
}