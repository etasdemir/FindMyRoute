package com.elacqua.findmyrouteapp.util

import com.google.android.gms.maps.model.LatLng
import java.security.MessageDigest
import kotlin.math.pow

object Utility {
    /**
     *  MD5 Hash: Used for hashing password before saving to the database and reading.
     * */
    val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    /**
     *  Note: Returns LatLng(Longitude, Latitude)
     *  Because openrouteservice takes requests as (Long, Lat) and responses too.
     * */
    fun decodePolyline(polyline: String): List<LatLng> {
        val coordinateChunks: MutableList<MutableList<Int>> = mutableListOf()
        coordinateChunks.add(mutableListOf())

        for (char in polyline.toCharArray()) {
            // convert each character to decimal from ascii
            var value = char.toInt() - 63

            // values that have a chunk following have an extra 1 on the left
            val isLastOfChunk = (value and 0x20) == 0
            value = value and (0x1F)

            coordinateChunks.last().add(value)

            if (isLastOfChunk)
                coordinateChunks.add(mutableListOf())
        }

        coordinateChunks.removeAt(coordinateChunks.lastIndex)

        val coordinates: MutableList<Double> = mutableListOf()

        for (coordinateChunk in coordinateChunks) {
            var coordinate = coordinateChunk.mapIndexed { i, chunk -> chunk shl (i * 5) }
                .reduce { i, j -> i or j }

            // there is a 1 on the right if the coordinate is negative
            if (coordinate and 0x1 > 0)
                coordinate = (coordinate).inv()

            coordinate = coordinate shr 1
            coordinates.add((coordinate).toDouble() / 100000.0)
        }

        val points: MutableList<LatLng> = mutableListOf()
        var previousX = 0.0
        var previousY = 0.0

        for (i in 0 until coordinates.size step 2) {
            if (coordinates[i] == 0.0 && coordinates[i + 1] == 0.0)
                continue

            previousX += coordinates[i + 1]
            previousY += coordinates[i]

            points.add(LatLng(round(previousY), round(previousX)))
        }
        return points
    }

    private fun round(value: Double, precision: Int = 5) =
        (value * 10.0.pow(precision.toDouble())).toInt().toDouble() / 10.0.pow(precision.toDouble())
}