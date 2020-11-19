package com.elacqua.findmyrouteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val username: String,
    val password: String
)