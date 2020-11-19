package com.elacqua.findmyrouteapp.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *  Coordinates are [Longitude, Latitude]
 *  example: {"coordinates":[[8.681495,49.41461]]}
 *
 *  radius will be initialized -1 for every coordinate. Because API limits 350m search area.
 */
data class Coordinates(
    @SerializedName("coordinates")
    val coordinates: List<List<Double>>,
    @SerializedName("radiuses")
    val radius: List<Int> = MutableList(coordinates.size) { -1 }
)