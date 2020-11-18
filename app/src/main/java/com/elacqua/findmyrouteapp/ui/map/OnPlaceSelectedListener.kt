package com.elacqua.findmyrouteapp.ui.map

import com.elacqua.findmyrouteapp.data.local.model.Place

interface OnPlaceSelectedListener {
    fun onDetailClicked(place: Place)
    fun onLocationClicked(place: Place)
}