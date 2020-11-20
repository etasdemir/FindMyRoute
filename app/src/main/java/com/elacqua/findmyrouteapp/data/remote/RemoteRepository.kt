package com.elacqua.findmyrouteapp.data.remote

import com.elacqua.findmyrouteapp.data.remote.dao.DirectionService
import com.elacqua.findmyrouteapp.data.remote.dao.OptimizationService
import com.elacqua.findmyrouteapp.data.remote.model.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val directionService: DirectionService,
    private val optimizationService: OptimizationService
) {

    suspend fun getRoutes(coordinates: Coordinates): ResultWrapper<Direction> {
        return SafeApiCall.execute(Dispatchers.IO) {
            directionService.getRoute(coordinates)
        }
    }

    suspend fun optimizeEndPoints(
        jobs: ArrayList<Job>,
        location: LatLng
    ): ResultWrapper<Optimization> {
        if (jobs.isNullOrEmpty()) {
            return ResultWrapper.GenericError(-1, "Empty job end points")
        }
        val startEndPoint = listOf(location.longitude, location.latitude)
        val vehicle = Vehicle(startEndPoint, startEndPoint)
        val optimizationBody = OptimizationBody(jobs, listOf(vehicle))
        return SafeApiCall.execute(Dispatchers.IO) {
            optimizationService.optimizeEndPoints(optimizationBody)
        }
    }

}