package com.andonichc.bcng.ui.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.main.LocationHandler
import com.andonichc.bcng.presentation.presenter.main.map.MapPresenter
import com.andonichc.bcng.presentation.presenter.main.map.MapView
import com.andonichc.bcng.ui.base.BaseFragment
import com.andonichc.bcng.ui.main.StationDetailView
import com.andonichc.bcng.ui.main.favorite.AddFavoriteDialog
import com.andonichc.bcng.ui.main.favorite.FavoriteSelectDialog
import com.andonichc.bcng.ui.main.favorite.FavoriteSelectedListener
import com.andonichc.bcng.util.getMarker
import com.andonichc.bcng.util.gone
import com.andonichc.bcng.util.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseFragment<MapPresenter>(), MapView, StationDetailView.FavoriteListener,
        FavoriteSelectedListener, FavoriteSelectDialog.FavoriteAddListener {

    private var map: GoogleMap? = null

    private var locationHandler: LocationHandler? = null

    companion object {

        private const val FAVORITE_SELECTION = "favorite_selection"
        private const val FAVORITE_ADD = "favorite_add"
        fun createInstance() =
                MapFragment()
    }

    //region_Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMapView(savedInstanceState)
        detailView.favoriteListener = this
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
            presenter.onMapReady()
            configureMap()
        }

        locationFab.setOnClickListener {
            if (map?.isMyLocationEnabled == true) {
                presenter.onMyLocationButtonClicked()
            }
        }

        refreshFab.setOnClickListener {
            presenter.onRefresh()
        }
    }

    private fun configureMap() {
        map?.setOnMarkerClickListener {
            presenter.onMarkerClicked(it.id)
            false
        }
        map?.setOnMapClickListener {
            detailView.gone()
        }

        map?.uiSettings?.run {
            isMyLocationButtonEnabled = false
            isZoomControlsEnabled = false
            isMapToolbarEnabled = false
        }
    }

    override fun centerMap(location: LocationModel, zoom: Float) {
        location.run {
            centerMapWithZoom(latitude, longitude, zoom)

        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LocationHandler) {
            locationHandler = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        locationHandler = null
    }

    //endregion_Fragment

    //region_View
    override fun addMarker(station: StationPresentationModel): String? =
            map?.addMarker(
                    MarkerOptions()
                            .position(
                                    LatLng(
                                            station.latitude,
                                            station.longitude
                                    ))
                            .icon(BitmapDescriptorFactory
                                    .fromResource(getMarker(station.status, station.type))))
                    ?.id

    private fun centerMapWithZoom(lat: Double, lon: Double, zoom: Float) {
        val latLng = LatLng(lat, lon)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
        map?.moveCamera(cameraUpdate)
    }

    override fun showDetailView(station: StationPresentationModel) {
        detailView.run {
            bind(station)
            visible()
        }
    }

    override fun clearMap() {
        map?.clear()
    }

    override fun getLastKnownLocation(): LocationModel =
            locationHandler?.getLastKnownLocation() ?: LocationModel()

    override fun isLocationPermissionGranted(): Boolean = locationHandler?.isLocationPermissionGranted()
            ?: false

    //endregion_View

    @SuppressLint("MissingPermission")
    override fun enableLocation() {
        map?.isMyLocationEnabled = true
        locationFab.visible()
    }

    override fun onLocationUpdated(location: LocationModel) {
        presenter.setLocation(location)
    }

    override fun onLocationPermissionGranted() {
        enableLocation()
    }


    //region favorite
    override fun onFavorited() {
        presenter.onItemFavorited()
    }

    override fun onUnfavorited() {
        presenter.onItemUnFavorited()
    }

    override fun showFavoriteSelectionDialog(favorites: List<FavoritePresentationModel>) {
        val dialog = FavoriteSelectDialog.newInstance(favorites)
        dialog.show(fragmentManager, FAVORITE_SELECTION)
        dialog.favoriteSelectListener = this
        dialog.favoriteAddListener = this
    }
    //endregion

    //region favorite selection
    override fun onFavoriteSelected(favorite: FavoritePresentationModel) {
        presenter.onItemAddedToFavorite(favorite)
    }

    override fun onFavoriteAdd() {
        presenter.onAddFavorite()
    }

    override fun showAddFavoriteDialog() {
        val dialog = AddFavoriteDialog.newInstance()
        dialog.show(fragmentManager, FAVORITE_ADD)
        dialog.favoriteSelectListener = this
    }

    //endregion
}