package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("location")
    val location: List<Double> = listOf()
)