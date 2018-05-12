package com.andonichc.bcng.presentation.presenter.main.list

import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import com.andonichc.bcng.presentation.presenter.main.LocationAwareView


interface ListView: BaseView, LocationAwareView {
    fun showStations(stations: List<StationPresentationModel>)
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
    fun showDeleteDialog(favorite: FavoritePresentationModel)
    fun onShowFavorites(favorites: List<FavoritePresentationModel>)
    fun showFavoriteSelectionDialog(favorites: List<FavoritePresentationModel>)
    fun showAddFavoriteDialog()
}

interface ListPresenter: BasePresenter {
    fun onRefresh()
    fun setLocation(location: LocationModel)
    fun onLocationPermissionGranted()
    fun onFavoriteSelected(favorite: FavoritePresentationModel)
    fun onFavoriteLongClick(favorite: FavoritePresentationModel)
    fun onAcceptDeleteFavorite(favorite: FavoritePresentationModel)
    fun onAddFavorite()
    fun onItemAddedToFavorite(favorite: FavoritePresentationModel)
    fun onMenuToggle(opened: Boolean)
    fun onItemFavorited(station: StationPresentationModel)
    fun onItemUnFavorited(station: StationPresentationModel)
}