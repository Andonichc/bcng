package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.domain.usecase.AddFavoriteUseCase
import com.andonichc.bcng.domain.usecase.DeleteFavoriteUseCase
import com.andonichc.bcng.domain.usecase.GetFavoriteUseCase
import com.andonichc.bcng.domain.usecase.GetStationsUseCase
import com.andonichc.bcng.presentation.mapper.FavoriteDomainMapper
import com.andonichc.bcng.presentation.mapper.FavoritePresentationMapper
import com.andonichc.bcng.presentation.mapper.StationPresentationMapper
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.observers.ConsumerSingleObserver
import io.reactivex.rxkotlin.subscribeBy


class ListPresenterImpl(view: ListView,
                        private val getStationsUseCase: GetStationsUseCase,
                        private val addFavoriteUseCase: AddFavoriteUseCase,
                        private val getFavoriteUseCase: GetFavoriteUseCase,
                        private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
                        private val stationsMapper: StationPresentationMapper,
                        private val favoritesMapper: FavoritePresentationMapper,
                        private val favoritesDomainMapper: FavoriteDomainMapper)
    : BasePresenterImpl<ListView>(view), ListPresenter {

    private var favorites: List<FavoritePresentationModel> = emptyList()

    private var favoriteSelected: FavoritePresentationModel? = null
    private var selectedStation: StationPresentationModel? = null

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val lastKnownLocation: LocationModel
        get() = view.getLastKnownLocation()
    private val isLocationPermissionGranted: Boolean
        get() = view.isLocationPermissionGranted()

    override fun onCreate() {
        refresh()
    }

    override fun onRefresh() {
        refresh(true)
    }

    override fun setLocation(location: LocationModel) {
        getStations(location, false)
    }

    private fun getStations(forceRefresh: Boolean = false) {
        getStationsUseCase.execute(forceRefresh)
                .map(stationsMapper::map)
                .subscribe(consumer)
    }

    private fun getStations(location: LocationModel, forceRefresh: Boolean = false) {
        location.run {
            getStationsUseCase.execute(latitude, longitude, forceRefresh)
                    .map(stationsMapper::map)
                    .subscribe(consumer)
        }
    }

    private fun getFavorites() {
        getFavoriteUseCase.execute()
                .map(favoritesMapper::map)
                .subscribeBy(
                        onSuccess = { favorites ->
                            this.favorites = favorites
                            view.onShowFavorites(favorites)
                        },
                        onError = {
                            view.showErrorState()
                        })
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


    override fun onFavoriteSelected(favorite: FavoritePresentationModel) {
        favoriteSelected = favorite
        getStationsFilteredByFavorite(favorite)
    }

    override fun onFavoriteLongClick(favorite: FavoritePresentationModel) {
        view.showDeleteDialog(favorite)
    }

    override fun onAcceptDeleteFavorite(favorite: FavoritePresentationModel) {
        deleteFavoriteUseCase.execute(favoritesDomainMapper.map(favorite))
                .subscribe {
                    refresh()
                }
    }

    override fun onMenuToggle(opened: Boolean) {
        if (!opened) {
            refresh()
        }
    }

    override fun onAddFavorite() {
        view.showAddFavoriteDialog()
    }

    override fun onItemAddedToFavorite(favorite: FavoritePresentationModel) {
        addFavorite(favorite)
    }

    private fun addFavorite(favorite: FavoritePresentationModel) {
        val selectedStation = selectedStation ?: return
        favorite.stationsIds.add(selectedStation.id)
        val disposable = addFavoriteUseCase
                .execute(favoritesDomainMapper.map(favorite))
                .subscribeBy(
                        onComplete = { refresh() },
                        onError = {
                            view.showErrorState()
                        })
        disposables.add(disposable)
    }


    override fun onItemFavorited(station: StationPresentationModel) {
        selectedStation = station
        view.showFavoriteSelectionDialog(favorites)
    }

    override fun onItemUnFavorited(station: StationPresentationModel) {
        removeFavorite(station)
    }

    private fun removeFavorite(station: StationPresentationModel) {
        val favorite = favorites.find { it.id == station.favoriteId }?.apply {
            stationsIds.remove(station.id)
        } ?: return

        val disposable = addFavoriteUseCase.execute(favoritesDomainMapper.map(favorite))
                .subscribeBy(
                        onComplete = {
                            refresh(true)
                        },
                        onError = {
                            view.showErrorState()
                        })
        disposables.add(disposable)
    }

    private fun getStationsFilteredByFavorite(favorite: FavoritePresentationModel) {
        getStationsUseCase.execute(favorite = favoritesDomainMapper.map(favorite))
                .map(stationsMapper::map)
                .subscribeBy(
                        onSuccess = { stations ->
                            view.showStations(stations)
                        },
                        onError = {
                            view.showErrorState()
                        })
    }

    private fun refresh(forceRefresh: Boolean = false) {
        if (lastKnownLocation != LocationModel()) {
            getStations(lastKnownLocation, forceRefresh)
        } else {
            getStations(forceRefresh)
        }
        getFavorites()
    }
}