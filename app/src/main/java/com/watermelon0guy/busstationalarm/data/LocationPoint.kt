package com.watermelon0guy.busstationalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationPoint(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val isChosen: Boolean,
    @PrimaryKey val id: Int? = null
)