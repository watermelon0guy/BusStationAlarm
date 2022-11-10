package com.watermelon0guy.busstationalarm.ui.location_list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.BusStationAlarmApp
import com.watermelon0guy.busstationalarm.data.LocationPoint

@Composable
fun LocationPointItem(
    locP: LocationPoint,
    onEvent: (LocationListEvent) -> Unit,
    modifier: Modifier = Modifier 
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = locP.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = {
            onEvent(LocationListEvent.OnDeleteLocationPointClick(locP))
        }) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = BusStationAlarmApp.resourses.getString(R.string.delete_location))
        }
        Switch(checked = locP.isChosen ,
            onCheckedChange = {isChosen ->
                onEvent(LocationListEvent.OnChosenChange(locP, isChosen))
            })
    }
}