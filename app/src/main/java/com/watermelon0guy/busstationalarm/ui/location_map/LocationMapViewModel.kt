package com.watermelon0guy.busstationalarm.ui.location_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.data.LocationRepository
import com.watermelon0guy.busstationalarm.util.Routes
import com.watermelon0guy.busstationalarm.util.UiEvent
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
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
    private val TARGET_LOCATION = Point(47.228914, 39.719190)
    private var mapView: MapView? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        mapView = MapView(BusStationAlarmApp.instance)


        // Двигаем камеру к местоположению пользователя TODO("Хардкод местоположения")
        mapView!!.map.move(
            CameraPosition(TARGET_LOCATION, 17.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )

        mapView!!.map.addTapListener(this)
        mapView!!.map.addInputListener(this)
    }

    fun onEvent(event: LocationMapEvent) {
        when (event) {
            is LocationMapEvent.OnLongClick -> {
                // TODO("Отправить на экран с созданием точки")
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_LOCATION)) // TODO("перадача точки")
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        val selectionMetadata = geoObjectTapEvent
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)
        if (selectionMetadata != null) {
            mapView!!.map.selectGeoObject(selectionMetadata.id, selectionMetadata.layerId)
        }
        return selectionMetadata != null
    }

    override fun onMapTap(map: Map, point: Point) {
        mapView!!.map.deselectGeoObject()
    }

    override fun onMapLongTap(map: Map, point: Point) {
        //TODO()
    }
}