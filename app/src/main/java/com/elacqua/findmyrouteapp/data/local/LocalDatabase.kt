package com.elacqua.findmyrouteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elacqua.findmyrouteapp.data.local.dao.UserDao
import com.elacqua.findmyrouteapp.data.local.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}