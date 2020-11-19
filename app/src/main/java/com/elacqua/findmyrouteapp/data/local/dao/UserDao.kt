package com.elacqua.findmyrouteapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elacqua.findmyrouteapp.data.local.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User): Long

    @Query("select * from User where username = :username and password = :password")
    suspend fun getUser(username: String, password: String): User?

}