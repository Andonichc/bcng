package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.rxkotlin.subscribeBy


class ListPresenterImpl(view: ListView,
                        private val getStationsUseCase: GetStationsUseCase,
                        private val mapper: StationPresentationMapper)
    : BasePresenterImpl<ListView>(view), ListPresenter {

    override fun onCreate() {
        getStationsUseCase.execute()
                .map(mapper::map)
                .subscribeBy(
                        onSuccess = view::showStations,
                        onError = {

                        })
    }
}