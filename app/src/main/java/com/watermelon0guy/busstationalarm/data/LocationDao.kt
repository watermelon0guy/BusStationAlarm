package com.watermelon0guy.busstationalarm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationPoint(locationPoint: LocationPoint)

    @Delete
    suspend fun deleteLocationPoint(locationPoint: LocationPoint)

    @Query("SELECT * FROM locationPoint WHERE id = :id")
    suspend fun getLocationPointById(id: Int): LocationPoint?

    @Query("SELECT * FROM locationPoint")
    fun getLocationPoints(): Flow<List<LocationPoint>>
}