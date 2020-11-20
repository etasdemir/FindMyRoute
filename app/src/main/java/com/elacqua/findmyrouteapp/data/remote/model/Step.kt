package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("location")
    val location: List<Double> = listOf()
)