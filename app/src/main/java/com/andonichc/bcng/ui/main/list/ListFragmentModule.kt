package com.andonichc.bcng.ui.main.list

import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenter
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenterImpl
import com.andonichc.bcng.presentation.presenter.main.list.ListView
import com.andonichc.bcng.util.LocationChecker
import dagger.Module
import dagger.Provides


@Module
class ListFragmentModule {

    @Provides
    fun providesListView(listFragment: ListFragment): ListView =
            listFragment

    @Provides
    fun providesListPresenter(view: ListView,
                             getStationsUseCase: GetStationsUseCase,
                             mapper: StationPresentationMapper): ListPresenter =
            ListPresenterImpl(view, getStationsUseCase, mapper)
}