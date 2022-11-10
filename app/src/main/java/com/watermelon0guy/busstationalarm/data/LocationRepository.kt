package com.watermelon0guy.busstationalarm.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface LocationRepository  {
    suspend fun insertLocationPoint(locationPoint: LocationPoint)

    suspend fun deleteLocationPoint(locationPoint: LocationPoint)

    suspend fun getLocationPointById(id: Int): LocationPoint?

    fun getLocationPoints(): Flow<List<LocationPoint>>
}