package com.watermelon0guy.busstationalarm.ui.location_map

sealed class LocationMapEvent {
    data class OnLongClick(val latitude: Double, val longitude: Double): LocationMapEvent()
    data class OnShortClick(val latitude: Double, val longitude: Double): LocationMapEvent()
}