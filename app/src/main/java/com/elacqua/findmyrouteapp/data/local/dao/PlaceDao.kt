package com.elacqua.findmyrouteapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elacqua.findmyrouteapp.data.local.entity.Place

@Dao
interface PlaceDao {

    @Insert
    suspend fun addPlace(place: Place)

    @Query("select * from Place where username = :username")
    fun getAllPlacesByUsername(username: String): LiveData<List<Place>>

    @Query("delete from Place")
    suspend fun nukePlaceTable()
}