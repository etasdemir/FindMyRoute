package com.elacqua.findmyrouteapp.data.remote

import com.elacqua.findmyrouteapp.data.remote.dao.DirectionService
import com.elacqua.findmyrouteapp.data.remote.model.Coordinates
import com.elacqua.findmyrouteapp.util.Utility
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val directionService: DirectionService
){

    suspend fun getRoutes(coordinates: Coordinates): List<LatLng> {
        val response = directionService.getRoute(coordinates)
        val polyline = response.routes[0].geometry
        return Utility.decodePolyline(polyline)
    }

}