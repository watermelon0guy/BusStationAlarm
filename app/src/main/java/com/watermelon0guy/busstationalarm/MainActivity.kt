package com.watermelon0guy.busstationalarm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.busstationalarm.R
import com.watermelon0guy.busstationalarm.trash.MainLocationScreen
import com.watermelon0guy.busstationalarm.ui.add_edit_location.AddEditLocationScreen
import com.watermelon0guy.busstationalarm.ui.location_list.LocationListScreen
import com.watermelon0guy.busstationalarm.ui.theme.BusStationAlarmTheme
import com.watermelon0guy.busstationalarm.util.Routes
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private lateinit var locationManager: LocationManager
//    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
//        if (!isPermissionGranted()) requestPermission()
//        if (!isGPSEnabled()) enableLocationSettings()
//
//        MapKitFactory.setApiKey(getString(R.string.mapkit_key))
//        mapView = MapView(this)
//        mapView.map.move(
//            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH, 0.0f),
//            null
//        )
        setContent {
            BusStationAlarmTheme {
                val navController = rememberNavController()
                // инициализируем ключ API для карт TODO("убрать ключ из ресурсов")
                MapKitFactory.setApiKey(getString(R.string.mapkit_key))
                NavHost(navController = navController, startDestination = Routes.LOCATION_LIST) {
                    composable(Routes.LOCATION_LIST) {
                        LocationListScreen(onNavigate = { navController.navigate(it.route) })
                    }
                    composable(route = Routes.ADD_EDIT_LOCATION + "?locationId={locationId}",
                    arguments = listOf(navArgument(name = "locationId") {
                        type = NavType.IntType
                        defaultValue = -1
                    })) {
                        AddEditLocationScreen(onPopBackStack = { navController.popBackStack() })
                    }
                }
            }
        }

    }

//    override fun onStart() {
//        super.onStart()
//        MapKitFactory.getInstance().onStart();
//        mapView.onStart();
//    }
//
//    override fun onStop() {
//        mapView.onStop();
//        MapKitFactory.getInstance().onStop();
//        super.onStop()
//    }
//
//    private fun enableLocationSettings() {
//        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//        startActivity(settingsIntent)
//    }
//
//    private fun isGPSEnabled(): Boolean {
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//
//    private fun isPermissionGranted(): Boolean {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 123)
//    }
}






