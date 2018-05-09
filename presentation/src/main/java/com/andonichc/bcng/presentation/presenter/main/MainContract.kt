package com.andonichc.bcng.presentation.presenter.main

import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView


interface MainView: BaseView {
    fun notifyLocationUpdate(location: LocationModel)
    fun requestLocationPermission()
    fun notifyLocationPermissionGranted()
    fun setLocationChangesListener()
}

interface MainPresenter: BasePresenter {
    fun onLocationUpdated(lat: Double, lon: Double)
    fun onLocationPermissionGranted()
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
}

interface LocationAwareView {
    fun onLocationUpdated(location: LocationModel)
    fun onLocationPermissionGranted()
}
interface LocationHandler {
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
}