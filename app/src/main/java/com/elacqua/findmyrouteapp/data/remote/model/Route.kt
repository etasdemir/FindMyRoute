package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("steps")
    val steps: List<Step> = listOf()
)