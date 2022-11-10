package com.watermelon0guy.busstationalarm.ui.location_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.data.LocationPoint
import com.watermelon0guy.busstationalarm.data.LocationRepository
import com.watermelon0guy.busstationalarm.util.Routes
import com.watermelon0guy.busstationalarm.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val repository: LocationRepository
): ViewModel()  {
    val locationPoints = repository.getLocationPoints()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedLocationPoint: LocationPoint? = null

    fun onEvent(event: LocationListEvent) {
        when(event) {
            is LocationListEvent.OnLocationPointClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_LOCATION + "?locationId=${event.locationPoint.id}"))
            }
            is LocationListEvent.OnAddLocationPointClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_LOCATION))
            }
            is LocationListEvent.OnUndoDeleteClick -> {
                deletedLocationPoint?.let { locationPoint ->
                    viewModelScope.launch {
                        repository.insertLocationPoint(locationPoint)
                    }
                }
            }
            is LocationListEvent.OnDeleteLocationPointClick -> {
                viewModelScope.launch {
                    deletedLocationPoint = event.locationPoint
                    repository.deleteLocationPoint(event.locationPoint)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = BusStationAlarmApp.resourses.getString(R.string.location_deleted),
                        action = "Undo"
                    ))
                }
            }
            is LocationListEvent.OnChosenChange -> {
                viewModelScope.launch {
                    repository.insertLocationPoint(
                        event.locationPoint.copy(isChosen = event.isChosen)
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

}