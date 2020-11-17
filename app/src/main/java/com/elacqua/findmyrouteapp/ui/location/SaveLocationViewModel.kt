package com.elacqua.findmyrouteapp.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.findmyrouteapp.data.local.LocalRepository
import com.elacqua.findmyrouteapp.data.local.model.Place
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class SaveLocationViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    fun saveLocation(title: String, description: String, location: LatLng){
        viewModelScope.launch {
            val username = localRepository.username
            val place = Place(
                username = username,
                latitude = location.latitude,
                longitude = location.longitude,
                title = title,
                description = description
            )
            localRepository.addPlace(place)
        }
    }
}