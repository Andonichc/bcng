package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.internal.observers.ConsumerSingleObserver


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

    override fun setLocation(lat: Double, lon: Double) {
        getStations(lat, lon)
    }


    override fun onMarkerClicked(id: String) {
        markerStations[id]?.let {
            view.showDetailView(it)
        }
    }

    private fun getStations() {
        getStationsUseCase.execute()
                .map(mapper::map)
                .subscribe(consumer)
    }

    private fun getStations(lat: Double, lon: Double) {
        getStationsUseCase.execute(lat, lon)
                .map(mapper::map)
                .subscribe(consumer)
    }


    private val consumer: ConsumerSingleObserver<List<StationPresentationModel>>
        get() =
            ConsumerSingleObserver({
                markerStations.clear()
                view.clearMap()
                it.forEach { station ->
                    val markerId = view.addMarker(station)
                    markerId?.let {
                        markerStations[it] = station
                    }
                }

            }, {
                view.showErrorState()
            })
}