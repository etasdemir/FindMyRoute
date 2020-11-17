package com.elacqua.findmyrouteapp.data.local

import com.elacqua.findmyrouteapp.data.local.dao.UserDao
import com.elacqua.findmyrouteapp.data.local.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getUser(username: String, password: String) =
        userDao.getUser(username, password)

    suspend fun addUser(user: User) =
        userDao.addUser(user)


}