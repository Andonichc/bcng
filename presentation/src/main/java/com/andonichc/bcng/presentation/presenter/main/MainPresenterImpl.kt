package com.andonichc.bcng.presentation.presenter.main

import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl


class MainPresenterImpl(view: MainView): BasePresenterImpl<MainView>(view), MainPresenter {

    private var lastKnownLocation: LocationModel = LocationModel()
    private var isLocationPermissionGranted: Boolean = false

    override fun onCreate() {
        view.requestLocationPermission()
    }

    override fun onLocationUpdated(lat: Double, lon: Double) {
        lastKnownLocation = LocationModel(lat, lon)
        view.notifyLocationUpdate(lastKnownLocation)
    }

    override fun onLocationPermissionGranted() {
        isLocationPermissionGranted = true
        view.setLocationChangesListener()
        view.notifyLocationPermissionGranted()
    }

    override fun getLastKnownLocation(): LocationModel = lastKnownLocation

    override fun isLocationPermissionGranted(): Boolean = isLocationPermissionGranted
}