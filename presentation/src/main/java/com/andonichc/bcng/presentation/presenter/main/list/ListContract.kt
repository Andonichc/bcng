package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView


interface ListView: BaseView {
    fun showStations(stations: List<StationPresentationModel>)
    fun requestLocationPermission()
}

interface ListPresenter: BasePresenter {
    fun onRefresh()
    fun setLocation(latitude: Double, longitude: Double)
}