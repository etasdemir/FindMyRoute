package com.elacqua.findmyrouteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elacqua.findmyrouteapp.data.local.dao.PlaceDao
import com.elacqua.findmyrouteapp.data.local.dao.UserDao
import com.elacqua.findmyrouteapp.data.local.entity.Place
import com.elacqua.findmyrouteapp.data.local.entity.User

@Database(
    entities = [User::class, Place::class],
    version = 3,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
}