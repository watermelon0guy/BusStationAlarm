package com.watermelon0guy.busstationalarm.trash

import android.annotation.SuppressLint
import android.location.LocationListener
import android.location.LocationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.busstationalarm.LocationViewModel
import com.watermelon0guy.busstationalarm.ui.theme.BusStationAlarmTheme
import com.yandex.mapkit.mapview.MapView



@Composable
fun MainLocationScreen(
    locationManager: LocationManager, mapView: MapView,
    viewModel: LocationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextLocation(latitude = uiState.latitude, longitude = uiState.longitude, modifier = Modifier)
        LocationSwitch(locationManager = locationManager, locationListener = viewModel as LocationListener)
        MapPreview(mapView)
    }
}

@Composable
fun TextLocation(latitude: Double, longitude: Double, modifier: Modifier) {
    Text(text = "Latitude: $latitude\nLongitude: $longitude")
}

@SuppressLint("MissingPermission")
@Composable
fun ButtonLocationSwitch(locationManager: LocationManager, locationListener: LocationListener) {
    val tracking = rememberSaveable {
        mutableStateOf(false)
    }
    Button(onClick = {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            5000,
            30f,
            locationListener)
        tracking.value = !tracking.value }) {
        if (tracking.value)
            Text(text = "On")
        else
            Text(text = "Off")
    }
}

@SuppressLint("MissingPermission")
@Composable
fun LocationSwitch(locationManager: LocationManager, locationListener: LocationListener) {
    val tracking = rememberSaveable { mutableStateOf(false) }
    Switch(checked = tracking.value, onCheckedChange = {
        if (!it)
            locationManager.removeUpdates(locationListener)
        else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000,
                30f,
                locationListener)
        tracking.value = !tracking.value

    })
}

@Composable
fun MapPreview(mapView: MapView) {
    AndroidView({ mapView }, modifier = Modifier.clip(RoundedCornerShape(topStartPercent = 5, topEndPercent = 5)))
}

@Preview(device = Devices.PIXEL_XL)
@Composable
fun Scr() {
    BusStationAlarmTheme {
        
    }

}