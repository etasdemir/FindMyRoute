package com.elacqua.findmyrouteapp.data.remote

import com.elacqua.findmyrouteapp.data.remote.dao.DirectionService
import com.elacqua.findmyrouteapp.data.remote.dao.OptimizationService
import com.elacqua.findmyrouteapp.data.remote.model.*
import com.elacqua.findmyrouteapp.util.Utility
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val directionService: DirectionService,
    private val optimizationService: OptimizationService
){

    suspend fun getRoutes(coordinates: Coordinates): List<LatLng> {
        val response = directionService.getRoute(coordinates)
        val polyline = response.polylines[0].geometry
        return Utility.decodePolyline(polyline)
    }

    suspend fun optimizeEndPoints(jobs: ArrayList<Job>, location: LatLng): Optimization {
        if (jobs.isNullOrEmpty()){
            return Optimization()
        }
        val startEndPoint = listOf(location.longitude, location.latitude)
        val vehicle = Vehicle(startEndPoint, startEndPoint)
        val optimizationBody = OptimizationBody(jobs, listOf(vehicle))
        return optimizationService.optimizeEndPoints(optimizationBody)
    }

}