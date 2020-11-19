package com.elacqua.findmyrouteapp.data.remote

import com.elacqua.findmyrouteapp.data.remote.dao.DirectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.openrouteservice.org"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader(
                    "Authorization",
                    "5b3ce3597851110001cf6248587efc1421f040c6b4455666b806049f"
                ).build()
            chain.proceed(request)
        }
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideDirectionService(retrofit: Retrofit) =
        retrofit.create(DirectionService::class.java)
}