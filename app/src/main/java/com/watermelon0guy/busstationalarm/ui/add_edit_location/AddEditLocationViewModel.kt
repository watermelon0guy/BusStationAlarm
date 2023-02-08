package com.watermelon0guy.busstationalarm.ui.add_edit_location

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.data.LocationPoint
import com.watermelon0guy.busstationalarm.data.LocationRepository
import com.watermelon0guy.busstationalarm.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditLocationViewModel @Inject constructor(
    private val repository: LocationRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var locP by mutableStateOf<LocationPoint?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var latitude by mutableStateOf(0.0)
        private set

    var longitude by mutableStateOf(0.0)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        Log.i("AddEditLocationViewModelInit", "Edit VM init")
        val locationId = savedStateHandle.get<Int>("locationId")!!
        if (locationId != -1) {
            viewModelScope.launch {
                repository.getLocationPointById(locationId)?.let { location ->
                    name = location.name
                    latitude = location.latitude
                    longitude = location.longitude
                    this@AddEditLocationViewModel.locP = location
                }
            }
        }
    }

    fun onEvent(event: AddEditLocationEvent) {
        when(event) {
            is AddEditLocationEvent.OnNameChange -> {
                name = event.name
            }
            is AddEditLocationEvent.OnLatitudeChange -> {
                latitude = event.latitude
            }
            is AddEditLocationEvent.OnLongitudeChange -> {
                longitude = event.longitude
            }
            is AddEditLocationEvent.OnSaveLocationClick -> {
                viewModelScope.launch {
                    if (name.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(message = BusStationAlarmApp.resourses.getString(R.string.name_cant_be_empty)))
                        return@launch
                    }
                    repository.insertLocationPoint(
                        LocationPoint(
                            name = name,
                            latitude = latitude,
                            longitude = longitude,
                            isChosen = locP?.isChosen ?: false,
                            id = locP?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }
}