package com.watermelon0guy.busstationalarm.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocationPoint::class],
    version = 1
)
abstract class LocationDatabase: RoomDatabase() {
    abstract val dao: LocationDao
}