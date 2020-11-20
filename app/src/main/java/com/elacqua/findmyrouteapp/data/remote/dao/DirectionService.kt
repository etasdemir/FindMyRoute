package com.elacqua.findmyrouteapp.data.remote.dao

import com.elacqua.findmyrouteapp.data.remote.model.Coordinates
import com.elacqua.findmyrouteapp.data.remote.model.Direction
import retrofit2.http.Body
import retrofit2.http.POST

interface DirectionService {

    @POST("/v2/directions/driving-car/")
    suspend fun getRoute(@Body coordinates: Coordinates): Direction
}