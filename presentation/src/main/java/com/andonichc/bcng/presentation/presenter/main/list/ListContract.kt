package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import com.andonichc.bcng.presentation.presenter.main.LocationAwareView


interface ListView: BaseView, LocationAwareView {
    fun showStations(stations: List<StationPresentationModel>)
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
}

interface ListPresenter: BasePresenter {
    fun onRefresh()
    fun setLocation(location: LocationModel)
    fun onLocationPermissionGranted()
}