package com.watermelon0guy.busstationalarm.ui.location_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watermelon0guy.busstationalarm.data.LocationRepository
import com.watermelon0guy.busstationalarm.ui.location_list.LocationListEvent
import com.watermelon0guy.busstationalarm.util.Routes
import com.watermelon0guy.busstationalarm.util.UiEvent
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationMapViewModel @Inject constructor(
    private val repository: LocationRepository
): ViewModel(), GeoObjectTapListener, InputListener {
    val locationPoints = repository.getLocationPoints()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LocationMapEvent) {
        when (event) {
            is LocationMapEvent.OnLongClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_LOCATION)) // TODO("перадача точки")
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    override fun onObjectTap(p0: GeoObjectTapEvent): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMapTap(p0: Map, p1: Point) {
        TODO("Not yet implemented")
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        TODO("Not yet implemented")
    }
}