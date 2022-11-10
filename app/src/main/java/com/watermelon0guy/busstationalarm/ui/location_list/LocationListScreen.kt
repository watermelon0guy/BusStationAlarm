package com.watermelon0guy.busstationalarm.ui.location_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.data.LocationPoint
import com.watermelon0guy.busstationalarm.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LocationListViewModel = hiltViewModel()
) {
    val locationPoints = viewModel.locationPoints.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = 1) {
        viewModel.uiEvent.collect() { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(LocationListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(LocationListEvent.OnAddLocationPointClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = BusStationAlarmApp.resourses.getString(
                    R.string.add_location))
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(locationPoints.value) { locP ->
                LocationPointItem(locP = locP,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(LocationListEvent.OnLocationPointClick(locP))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ScreenPreview() {
    Surface(modifier = Modifier
        .height(100.dp)
        .width(400.dp)) {
        LocationPointItem(modifier = Modifier.fillMaxWidth(), locP = LocationPoint("check", 40.0, 50.0, isChosen = false), onEvent = {})
    }
}