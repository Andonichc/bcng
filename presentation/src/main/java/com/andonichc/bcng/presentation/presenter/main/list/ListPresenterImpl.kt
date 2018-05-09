package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.observers.ConsumerSingleObserver


class ListPresenterImpl(view: ListView,
                        private val getStationsUseCase: GetStationsUseCase,
                        private val mapper: StationPresentationMapper)
    : BasePresenterImpl<ListView>(view), ListPresenter {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val lastKnownLocation: LocationModel
        get() = view.getLastKnownLocation()
    private val isLocationPermissionGranted: Boolean
        get() = view.isLocationPermissionGranted()

    override fun onCreate() {
        if (isLocationPermissionGranted && lastKnownLocation != LocationModel()) {
            getStations(lastKnownLocation)
        } else {
            getStations()
        }
    }

    override fun onRefresh() {
        if (lastKnownLocation != LocationModel()) {
            getStations(lastKnownLocation, true)
        } else {
            getStations(true)
        }
    }

    override fun setLocation(location: LocationModel) {
        getStations(location, false)
    }

    private fun getStations(forceRefresh: Boolean = false) {
        getStationsUseCase.execute()
                .map(mapper::map)
                .subscribe(consumer)
    }

    private fun getStations(location: LocationModel, forceRefresh: Boolean = false) {
        location.run {
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
            val consumer = ConsumerSingleObserver(view::showStations,
                    {
                        view.showErrorState()
                    })
            disposables.add(consumer)
            return consumer
        }

    override fun onLocationPermissionGranted() {
        //empty
    }
}