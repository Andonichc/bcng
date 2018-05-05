package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView

const val DEFAULT_LAT = 41.3870154
const val DEFAULT_LON = 2.1678584

interface MapView : BaseView {
    fun requestLocationPermission()
    fun centerMapOnMyLocation()
    fun centerMapInDefaultPosition()
    fun addMarker(station: StationPresentationModel): String?
    fun showDetailView(station: StationPresentationModel)
    fun clearMap()

}

interface MapPresenter : BasePresenter {
    fun onMapReady()
    fun setLocation(lat: Double, lon: Double)
    fun onMyLocationButtonClicked()
    fun onMarkerClicked(id: String)
    fun onLocationNotAvailable()
}