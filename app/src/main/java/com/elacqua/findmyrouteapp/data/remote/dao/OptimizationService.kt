package com.elacqua.findmyrouteapp.data.remote.dao

import com.elacqua.findmyrouteapp.data.remote.model.Optimization
import com.elacqua.findmyrouteapp.data.remote.model.OptimizationBody
import retrofit2.http.Body
import retrofit2.http.POST

interface OptimizationService {

    @POST("/optimization")
    suspend fun optimizeEndPoints(@Body optimizationBody: OptimizationBody): Optimization
}