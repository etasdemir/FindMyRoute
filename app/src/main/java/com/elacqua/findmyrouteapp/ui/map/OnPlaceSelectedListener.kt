package com.elacqua.findmyrouteapp.ui.map

import com.elacqua.findmyrouteapp.data.local.entity.Place

interface OnPlaceSelectedListener {
    fun onDetailClicked(place: Place)
    fun onLocationClicked(place: Place)
}