package com.elacqua.findmyrouteapp.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.findmyrouteapp.data.local.LocalRepository
import com.elacqua.findmyrouteapp.data.local.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    val places: LiveData<List<Place>> = getAllPlaces()

    private fun getAllPlaces() =
        localRepository.getAllPlacesByUsername()
}