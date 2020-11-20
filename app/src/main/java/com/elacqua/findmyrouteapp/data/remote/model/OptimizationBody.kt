package com.elacqua.findmyrouteapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class OptimizationBody(
    @SerializedName("jobs")
    val jobs: List<Job> = listOf(),
    @SerializedName("vehicles")
    val vehicles: List<Vehicle> = listOf()
)