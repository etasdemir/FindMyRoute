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
import com.elacqua.findmyrouteapp.data.remote.model.Job
import com.elacqua.findmyrouteapp.data.remote.model.Step
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _decodedPolyline = MutableLiveData<List<LatLng>>()
    val decodedPolyline: LiveData<List<LatLng>> = _decodedPolyline

    val places: LiveData<List<Place>> = getAllPlaces()

    init {
//        deleteAllPlaces()
//        TODO()
    }
    fun deleteAllPlaces(){
        viewModelScope.launch {
            localRepository.deleteAllPlaces()
        }
    }

    private fun getAllPlaces() =
        localRepository.getAllPlacesByUsername()

    fun findPath(places: List<Place>, location: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val steps = optimizePoints(places, location)
            val result = getRoute(steps)
            _decodedPolyline.postValue(result)
        }
    }

    private suspend fun optimizePoints(places: List<Place>, location: LatLng): List<Step> {
        val jobs = ArrayList<Job>()
        var id = 1
        for (index in places.indices) {
            val jobLocation = listOf(places[index].longitude, places[index].latitude)
            val job = Job(id++, jobLocation)
            jobs.add(job)
        }
        val optimization = remoteRepository.optimizeEndPoints(jobs, location)
        if (optimization.routes.isEmpty()){
            return emptyList()
        }
        return optimization.routes[0].steps
    }

    private suspend fun getRoute(steps: List<Step>): List<LatLng> {
        val points = ArrayList<List<Double>>()
        for (step in steps){
            points.add(step.location)
        }
        val coordinates = Coordinates(points)
        return remoteRepository.getRoutes(coordinates)
    }
}