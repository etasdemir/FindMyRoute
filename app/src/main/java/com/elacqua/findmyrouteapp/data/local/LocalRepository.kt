package com.elacqua.findmyrouteapp.data.local

import com.elacqua.findmyrouteapp.data.local.dao.PlaceDao
import com.elacqua.findmyrouteapp.data.local.dao.UserDao
import com.elacqua.findmyrouteapp.data.local.entity.Place
import com.elacqua.findmyrouteapp.data.local.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val userDao: UserDao,
    private val placeDao: PlaceDao
) {
    lateinit var username: String

    suspend fun getUser(username: String, password: String) =
        userDao.getUser(username, password)

    suspend fun addUser(user: User) =
        userDao.addUser(user)

    suspend fun addPlace(place: Place) =
        placeDao.addPlace(place)

    fun getAllPlacesByUsername() =
        placeDao.getAllPlacesByUsername(username)

    suspend fun deleteAllPlaces() =
        placeDao.nukePlaceTable()
}