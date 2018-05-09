package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.observers.ConsumerSingleObserver


class MapPresenterImpl(view: MapView,
                       private val getStationsUseCase: GetStationsUseCase,
                       private val mapper: StationPresentationMapper) :
        BasePresenterImpl<MapView>(view), MapPresenter {

    companion object {
        const val DEFAULT_ZOOM = 16F
    }

    private val markerStations = hashMapOf<String, StationPresentationModel>()
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var favorites: List<FavoritePresentationModel> = emptyList()
    private val lastKnownLocation: LocationModel
        get() = view.getLastKnownLocation()
    private val isLocationPermissionGranted: Boolean
        get() = view.isLocationPermissionGranted()
    private var isFirstLocation: Boolean = true

    override fun onMapReady() {
        if (isLocationPermissionGranted && lastKnownLocation != LocationModel()) {
            centerOnMyLocation()
            getStations(lastKnownLocation)
            isFirstLocation = false
        } else {
            view.centerMap(LocationModel(), 13F)
            getStations()
        }

        if (isLocationPermissionGranted) view.enableLocation()
    }

    override fun onMyLocationButtonClicked() {
        centerOnMyLocation()
    }

    private fun centerOnMyLocation() {
        view.centerMap(lastKnownLocation, DEFAULT_ZOOM)
    }

    override fun setLocation(location: LocationModel) {
        if (isFirstLocation) {
            view.centerMap(location, DEFAULT_ZOOM)
            isFirstLocation = false
        }
        getStations(location)
    }


    override fun onMarkerClicked(id: String) {
        markerStations[id]?.let {
            view.showDetailView(it)
        }
    }

    override fun onRefresh() {
        if (lastKnownLocation != LocationModel()) {
            getStations(lastKnownLocation, true)
        } else {
            getStations(true)
        }
    }

    private fun getStations(forceRefresh: Boolean = false) {
        getStationsUseCase.execute(forceRefresh)
                .map(mapper::map)
                .subscribe(consumer)
    }

    private fun getStations(locationModel: LocationModel, forceRefresh: Boolean = false) {
        locationModel.run {
            getStationsUseCase.execute(latitude, longitude, forceRefresh)
                    .map(mapper::map)
                    .subscribe(consumer)
        }
    }

    override fun onStop() {
        disposables.dispose()
    }

    private val consumer: ConsumerSingleObserver<List<StationPresentationModel>>
        get() {
            val consumer = ConsumerSingleObserver<List<StationPresentationModel>>({
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

            disposables.add(consumer)
            return consumer
        }
}