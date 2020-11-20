package com.elacqua.findmyrouteapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class Optimization(
    @SerializedName("routes")
    val routes: List<Route> = listOf(),
)

