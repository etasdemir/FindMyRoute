package com.elacqua.findmyrouteapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elacqua.findmyrouteapp.data.local.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User): Long

    @Query("select * from User where username = :username and password = :password")
    fun getUser(username: String, password: String): LiveData<User?>

}