package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.internal.observers.ConsumerSingleObserver


class ListPresenterImpl(view: ListView,
                        private val getStationsUseCase: GetStationsUseCase,
                        private val mapper: StationPresentationMapper)
    : BasePresenterImpl<ListView>(view), ListPresenter {

    override fun onCreate() {
        getStations()
        view.requestLocationPermission()
    }

    override fun onRefresh() {
        getStations()
    }

    override fun setLocation(latitude: Double, longitude: Double) {
        getStations(latitude, longitude)
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
            ConsumerSingleObserver(view::showStations,
                    {
                view.showErrorState()
            })
}