package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import com.andonichc.bcng.presentation.presenter.main.LocationAwareView

interface MapView : BaseView, LocationAwareView {
    fun requestLocationPermission()
    fun centerMap(location: LocationModel, zoom: Float)
    fun addMarker(station: StationPresentationModel): String?
    fun showDetailView(station: StationPresentationModel)
    fun clearMap()
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
    fun enableLocation()

}

interface MapPresenter : BasePresenter {
    fun onMapReady()
    fun setLocation(location: LocationModel)
    fun onMyLocationButtonClicked()
    fun onMarkerClicked(id: String)
    fun onRefresh()
}