package com.elacqua.findmyrouteapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class Direction(
    @SerializedName("routes")
    val polylines: List<Polyline> = listOf()
)
