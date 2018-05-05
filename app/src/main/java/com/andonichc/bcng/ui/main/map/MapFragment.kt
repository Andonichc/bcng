package com.andonichc.bcng.ui.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.main.map.MapPresenter
import com.andonichc.bcng.presentation.presenter.main.map.MapView
import com.andonichc.bcng.ui.base.BaseFragment
import com.andonichc.bcng.util.LocationChecker
import com.andonichc.bcng.util.getMarker
import com.andonichc.bcng.util.gone
import com.andonichc.bcng.util.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


class MapFragment : BaseFragment<MapPresenter>(), MapView {

    @Inject
    lateinit var locationChecker: LocationChecker

    private var locationManager: LocationManager? = null

    private var map: GoogleMap? = null

    private var mListener: OnFragmentInteractionListener? = null

    companion object {
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
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
            presenter.onMapReady()
            setMapperListeners()
        }

        locationFab.setOnClickListener {
            if (map?.isMyLocationEnabled == true) {
                presenter.onMyLocationButtonClicked()
            }
        }
    }

    private fun setMapperListeners() {
        map?.setOnMarkerClickListener {
            presenter.onMarkerClicked(it.id)
            false
        }
        map?.setOnMapClickListener {
            detailView.gone()
        }
    }

    private fun centerMap(lat: Double, lon: Double) {
        centerMapWithZoom(lat, lon, 16f)
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
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationChecker.onRequestPermissionResult(requestCode, grantResults)
    }

    //endregion_Fragment

    //region_View
    override fun requestLocationPermission() {
        locationChecker.check(onSuccess = this::enableLocation, onError = this::onLocationNotAvailable)
    }

    private fun onLocationNotAvailable() {
        presenter.onLocationNotAvailable()
    }

    @SuppressLint("MissingPermission")
    override fun centerMapOnMyLocation() {
        map?.myLocation?.let { onLocationUpdated(it.latitude, it.longitude) }
                ?: locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 1000F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        location?.let { onLocationUpdated(it.latitude, it.longitude) }
                        locationManager?.removeUpdates(this)
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(p0: String?) {}

                    override fun onProviderDisabled(p0: String?) {}
                })
    }

    private fun onLocationUpdated(latitude: Double, longitude: Double) {
        centerMap(latitude, longitude)
        presenter.setLocation(latitude, longitude)
    }

    override fun centerMapInDefaultPosition() {
        val latLng = LatLng(41.3870154, 2.1678584)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13f)
        map?.moveCamera(cameraUpdate)
    }

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

    //endregion_View

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        map?.isMyLocationEnabled = true
        map?.uiSettings?.run {
            isMyLocationButtonEnabled = false
            isZoomControlsEnabled = false
            isMapToolbarEnabled = false
        }
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationFab.visibility = View.VISIBLE
        centerMapOnMyLocation()
    }


interface OnFragmentInteractionListener {
    fun onFragmentInteraction(uri: Uri)
}
}