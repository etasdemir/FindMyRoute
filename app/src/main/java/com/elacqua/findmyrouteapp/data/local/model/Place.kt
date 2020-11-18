package com.elacqua.findmyrouteapp.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String
) : Parcelable