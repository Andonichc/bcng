package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView


interface MapView : BaseView {
    fun requestLocationPermission()
    fun centerMapOnMyLocation()
    fun centerMapInDefaultPosition()
    fun addMarker(station: StationPresentationModel): String?
    fun showDetailView(station: StationPresentationModel)

}

interface MapPresenter : BasePresenter {
    fun onMapReady()
    fun onMyLocationButtonClicked()
    fun onMarkerClicked(id: String)
}