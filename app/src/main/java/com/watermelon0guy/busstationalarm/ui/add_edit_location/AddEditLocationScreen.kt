package com.watermelon0guy.busstationalarm.ui.add_edit_location

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditLocationScreen(
    onPopBackStack:() -> Unit,
    viewModel: AddEditLocationViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit 
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditLocationEvent.OnSaveLocationClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = BusStationAlarmApp.resourses.getString(R.string.save_location))
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.onEvent(AddEditLocationEvent.OnNameChange(it))
                },
                placeholder = {
                    Text(text = BusStationAlarmApp.resourses.getString(R.string.name))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.latitude.toString(),
                onValueChange = {
                    viewModel.onEvent(AddEditLocationEvent.OnLatitudeChange(it.filter { c -> c.isDigit() || c=='.' }.toDouble()))
                },
                placeholder = {
                    Text(text = BusStationAlarmApp.resourses.getString(R.string.latitude))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.longitude.toString(),
                onValueChange = {
                    viewModel.onEvent(AddEditLocationEvent.OnLongitudeChange(it.filter { c -> c.isDigit() || c=='.' }.toDouble()))
                },
                placeholder = {
                    Text(text = BusStationAlarmApp.resourses.getString(R.string.longitude))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}