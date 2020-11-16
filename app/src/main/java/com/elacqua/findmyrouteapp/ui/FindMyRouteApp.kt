package com.elacqua.findmyrouteapp.ui

import android.app.Application
import com.elacqua.findmyrouteapp.BuildConfig
import timber.log.Timber

class FindMyRouteApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}