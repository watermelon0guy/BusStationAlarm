package com.example.busstationalarm

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class LocationUiState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val tracking: Boolean = false
)

class LocationViewModel: ViewModel(), LocationListener {
    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()
    override fun onLocationChanged(location: Location) {
        _uiState.update { locationUiState -> locationUiState.copy(latitude = location.latitude, longitude = location.longitude) }
        Log.i("VM", "Location changed")
    }
}