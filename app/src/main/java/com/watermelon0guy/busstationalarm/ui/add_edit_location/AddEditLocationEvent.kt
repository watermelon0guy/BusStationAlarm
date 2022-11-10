package com.watermelon0guy.busstationalarm.ui.add_edit_location

sealed class AddEditLocationEvent {
    data class OnNameChange(val name: String): AddEditLocationEvent()
    data class OnLatitudeChange(val latitude: Double): AddEditLocationEvent()
    data class OnLongitudeChange(val longitude: Double): AddEditLocationEvent()
    object OnSaveLocationClick: AddEditLocationEvent()
}
