package com.watermelon0guy.busstationalarm.ui.location_map

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.mapview.MapView

@Composable
fun MapScreen(
    viewModel: LocationMapViewModel = hiltViewModel()
) {
    val locationPoints = viewModel.locationPoints.collectAsState(initial = emptyList())
}

@Composable
fun Map(mapView: MapView) {
    AndroidView({ mapView }, modifier = Modifier.clip(RoundedCornerShape(topStartPercent = 5, topEndPercent = 5)))
}