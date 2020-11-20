package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("geometry")
    val geometry: String = ""
)