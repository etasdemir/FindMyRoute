package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("geometry")
    val geometry: String = ""
)