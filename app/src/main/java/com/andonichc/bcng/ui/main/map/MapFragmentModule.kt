package com.andonichc.bcng.ui.main.map

import com.andonichc.bcng.domain.usecase.AddFavoriteUseCase
import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.FavoriteDomainMapper
import com.andonichc.bcng.presentation.mapper.FavoritePresentationMapper
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.presenter.main.map.MapPresenter
import com.andonichc.bcng.presentation.presenter.main.map.MapPresenterImpl
import com.andonichc.bcng.presentation.presenter.main.map.MapView
import dagger.Module
import dagger.Provides


@Module
class MapFragmentModule {

    @Provides
    fun providesMapView(mapFragment: MapFragment): MapView =
            mapFragment

    @Provides
    fun providesMapPresenter(view: MapView,
                             getStationsUseCase: GetStationsUseCase,
                             addFavoriteUseCase: AddFavoriteUseCase,
                             mapper: StationPresentationMapper,
                             favoriteMapper: FavoritePresentationMapper,
                             favoriteDomainlMapper: FavoriteDomainMapper): MapPresenter =
            MapPresenterImpl(view, getStationsUseCase, addFavoriteUseCase, mapper, favoriteMapper,
                    favoriteDomainlMapper)
}