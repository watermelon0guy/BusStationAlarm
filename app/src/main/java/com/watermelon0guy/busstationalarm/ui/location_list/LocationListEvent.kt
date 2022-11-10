package com.watermelon0guy.busstationalarm.ui.location_list

import com.watermelon0guy.busstationalarm.data.LocationPoint

sealed class LocationListEvent {
    data class OnDeleteLocationPointClick(val locationPoint: LocationPoint): LocationListEvent()
    data class OnChosenChange(val locationPoint: LocationPoint, val isChosen: Boolean): LocationListEvent()
    object OnUndoDeleteClick: LocationListEvent()
    data class OnLocationPointClick(val locationPoint: LocationPoint): LocationListEvent()
    object OnAddLocationPointClick: LocationListEvent()
}
