package com.andonichc.bcng.presentation.presenter.main.map

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


class MapPresenterImpl(view: MapView,
                       private val getStationsUseCase: GetStationsUseCase,
                       private val addFavoriteUseCase: AddFavoriteUseCase,
                       private val getFavoriteUseCase: GetFavoriteUseCase,
                       private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
                       private val stationsMapper: StationPresentationMapper,
                       private val favoritesMapper: FavoritePresentationMapper,
                       private val favoritesDomainMapper: FavoriteDomainMapper) :
        BasePresenterImpl<MapView>(view), MapPresenter {

    companion object {
        const val DEFAULT_ZOOM = 16F
    }

    private val markerStations = hashMapOf<String, StationPresentationModel>()
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var favorites: List<FavoritePresentationModel> = emptyList()

    private var selectedStation: StationPresentationModel? = null
    private var favoriteSelected: FavoritePresentationModel? = null

    private val lastKnownLocation: LocationModel
        get() = view.getLastKnownLocation()
    private val isLocationPermissionGranted: Boolean
        get() = view.isLocationPermissionGranted()
    private var isFirstLocation: Boolean = true

    override fun onCreate() {
        getFavorites()
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
            selectedStation = it
        }
    }

    override fun onRefresh() {
        refresh(true)
    }

    private fun getStations(forceRefresh: Boolean = false) {
        getStationsUseCase.execute(forceRefresh)
                .map(stationsMapper::map)
                .subscribe(consumer)
    }

    private fun getStations(locationModel: LocationModel, forceRefresh: Boolean = false) {
        locationModel.run {
            getStationsUseCase.execute(latitude, longitude, forceRefresh)
                    .map(stationsMapper::map)
                    .subscribe(consumer)
        }
    }

    override fun onStop() {
        disposables.dispose()
    }

    //region favorite
    override fun onItemFavorited() {
        view.showFavoriteSelectionDialog(favorites)
    }

    override fun onItemUnFavorited() {
        removeFavorite()
    }

    private fun removeFavorite() {
        val selectedStation = selectedStation ?: return
        val favorite = favorites.find { it.id == selectedStation.favoriteId }?.apply {
            stationsIds.remove(selectedStation.id)
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
                        onComplete = {
                            refresh()
                        },
                        onError = {
                            view.showErrorState()
                        })
        disposables.add(disposable)
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
                .subscribeBy(
                        onComplete = {
                            refresh()
                        },
                        onError = {
                            view.showErrorState()
                        })
    }

    override fun onMenuToggle(opened: Boolean) {
        if (!opened) {
            refresh()
        }
    }

    private fun getStationsFilteredByFavorite(favorite: FavoritePresentationModel) {
        getStationsUseCase.execute(favorite = favoritesDomainMapper.map(favorite))
                .map(stationsMapper::map)
                .subscribeBy(
                        onSuccess = { stations ->
                            clearEstate()
                            addStationsToMap(stations)
                            view.centerMapOnStations(stations)
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

    private fun clearEstate() {
        markerStations.clear()
        view.clearMap()
        selectedStation = null
        view.hideDetailView()
    }

    //endregion

    private fun addStationsToMap(stations: List<StationPresentationModel>) {
        stations.forEach { station ->
            val markerId = view.addMarker(station)
            markerId?.let {
                markerStations[it] = station
            }
        }
    }

    private val consumer: ConsumerSingleObserver<List<StationPresentationModel>>
        get() {
            val consumer = ConsumerSingleObserver<List<StationPresentationModel>>({
                clearEstate()
                addStationsToMap(it)
            }, {
                view.showErrorState()
            })

            disposables.add(consumer)
            return consumer
        }
}