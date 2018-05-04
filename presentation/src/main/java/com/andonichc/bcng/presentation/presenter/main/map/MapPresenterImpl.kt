package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.rxkotlin.subscribeBy


class MapPresenterImpl(view: MapView,
                       private val getStationsUseCase: GetStationsUseCase,
                       private val mapper: StationPresentationMapper) :
        BasePresenterImpl<MapView>(view), MapPresenter {

    private val markerStations = hashMapOf<String, StationPresentationModel>()

    override fun onMapReady() {
        view.centerMapInDefaultPosition()
        view.requestLocationPermission()
        getStations()
    }

    override fun onMyLocationButtonClicked() {
        view.centerMapOnMyLocation()
    }

    private fun getStations() {
        getStationsUseCase.execute()
                .map(mapper::map)
                .subscribeBy(
                        onSuccess = {
                            it.forEach { station ->
                                val markerId = view.addMarker(station)
                                markerId?.let {
                                    markerStations[it] = station
                                }
                            }

                        },
                        onError = {
                            view.showErrorState()
                        })
    }

    override fun onMarkerClicked(id: String) {
        markerStations[id]?.let {
            view.showDetailView(it)
        }
    }
}