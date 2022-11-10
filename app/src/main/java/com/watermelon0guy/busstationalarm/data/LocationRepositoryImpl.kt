package com.watermelon0guy.busstationalarm.data

import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val dao: LocationDao
): LocationRepository {
    override suspend fun insertLocationPoint(locationPoint: LocationPoint) {
        dao.insertLocationPoint(locationPoint)
    }

    override suspend fun deleteLocationPoint(locationPoint: LocationPoint) {
        dao.deleteLocationPoint(locationPoint)
    }

    override suspend fun getLocationPointById(id: Int): LocationPoint? {
        return dao.getLocationPointById(id)
    }

    override fun getLocationPoints(): Flow<List<LocationPoint>> {
        return dao.getLocationPoints()
    }
}