package com.watermelon0guy.busstationalarm.di

import android.app.Application
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.data.LocationDatabase
import com.watermelon0guy.busstationalarm.data.LocationRepository
import com.watermelon0guy.busstationalarm.data.LocationRepositoryImpl
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocationDatabase(app: Application): LocationDatabase {
        return Room.databaseBuilder(
            app,
            LocationDatabase::class.java,
            "location_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(db: LocationDatabase): LocationRepository {
        return LocationRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideLocationManager(app: Application): LocationManager {
        return app.getSystemService(ComponentActivity.LOCATION_SERVICE) as LocationManager
    }
}