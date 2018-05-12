package com.andonichc.bcng.presentation.presenter.main.map

import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import com.andonichc.bcng.presentation.presenter.main.LocationAwareView

interface MapView : BaseView, LocationAwareView {
    fun centerMap(location: LocationModel, zoom: Float)
    fun addMarker(station: StationPresentationModel): String?
    fun showDetailView(station: StationPresentationModel)
    fun hideDetailView()
    fun clearMap()
    fun getLastKnownLocation(): LocationModel
    fun isLocationPermissionGranted(): Boolean
    fun enableLocation()
    fun showFavoriteSelectionDialog(favorites:List<FavoritePresentationModel>)
    fun showAddFavoriteDialog()
    fun onShowFavorites(favorites: List<FavoritePresentationModel>)
    fun centerMapOnStations(stations: List<StationPresentationModel>)
    fun showDeleteDialog(favorite: FavoritePresentationModel)


}

interface MapPresenter : BasePresenter {
    fun onMapReady()
    fun setLocation(location: LocationModel)
    fun onMyLocationButtonClicked()
    fun onMarkerClicked(id: String)
    fun onRefresh()
    fun onItemFavorited()
    fun onItemUnFavorited()
    fun onAddFavorite()
    fun onItemAddedToFavorite(favorite: FavoritePresentationModel)
    fun onFavoriteSelected(favorite: FavoritePresentationModel)
    fun onMenuToggle(opened: Boolean)
    fun onFavoriteLongClick(favorite: FavoritePresentationModel)
    fun onAcceptDeleteFavorite(favorite: FavoritePresentationModel)
}