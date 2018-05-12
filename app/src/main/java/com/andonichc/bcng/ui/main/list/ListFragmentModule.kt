package com.andonichc.bcng.ui.main.list

import com.andonichc.bcng.domain.usecase.AddFavoriteUseCase
import com.andonichc.bcng.domain.usecase.DeleteFavoriteUseCase
import com.andonichc.bcng.domain.usecase.GetFavoriteUseCase
import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.FavoriteDomainMapper
import com.andonichc.bcng.presentation.mapper.FavoritePresentationMapper
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenter
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenterImpl
import com.andonichc.bcng.presentation.presenter.main.list.ListView
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
                              addFavoriteUseCase: AddFavoriteUseCase,
                              mapper: StationPresentationMapper,
                              favoriteMapper: FavoritePresentationMapper,
                              favoriteDomainlMapper: FavoriteDomainMapper,
                              getFavoriteUseCase: GetFavoriteUseCase,
                              deleteFavoriteUseCase: DeleteFavoriteUseCase): ListPresenter =
            ListPresenterImpl(view, getStationsUseCase, addFavoriteUseCase, getFavoriteUseCase,
                    deleteFavoriteUseCase, mapper, favoriteMapper, favoriteDomainlMapper)
}