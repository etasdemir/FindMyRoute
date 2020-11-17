package com.elacqua.findmyrouteapp.data.local

import android.content.Context
import androidx.room.Room
import com.elacqua.findmyrouteapp.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase{
        return Room.databaseBuilder(context, LocalDatabase::class.java,  "FindMyRouteDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(db: LocalDatabase): UserDao {
        return db.userDao()
    }
}