package com.andonichc.bcng.ui.main.list

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenter
import com.andonichc.bcng.presentation.presenter.main.list.ListView
import com.andonichc.bcng.ui.base.BaseFragment
import com.andonichc.bcng.util.LocationChecker
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class ListFragment : BaseFragment<ListPresenter>(), ListView {

    @Inject
    lateinit var locationChecker: LocationChecker

    private var locationManager: LocationManager? = null

    companion object {
        fun createInstance() =
                ListFragment()
    }

    //region_Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        rvList.adapter = StationsAdapter()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationChecker.onRequestPermissionResult(requestCode, grantResults)
    }
    //endregion

    //region_View
    override fun showStations(stations: List<StationPresentationModel>) {
        (rvList.adapter as StationsAdapter).setItems(stations)
    }

    override fun requestLocationPermission() {
        locationChecker.check(onSuccess = this::enableLocation)
    }

    //endregion


    @SuppressLint("MissingPermission")
   private fun enableLocation() {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 1000F, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                location?.let { presenter.setLocation(it.latitude, it.longitude) }
                locationManager?.removeUpdates(this)
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

            override fun onProviderEnabled(p0: String?) {}

            override fun onProviderDisabled(p0: String?) {}
        })
    }


}
