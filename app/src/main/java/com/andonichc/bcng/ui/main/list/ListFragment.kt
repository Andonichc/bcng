package com.andonichc.bcng.ui.main.list

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.main.LocationHandler
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenter
import com.andonichc.bcng.presentation.presenter.main.list.ListView
import com.andonichc.bcng.ui.base.BaseFragment
import com.andonichc.bcng.util.LocationChecker
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class ListFragment : BaseFragment<ListPresenter>(), ListView, SwipeRefreshLayout.OnRefreshListener {


    companion object {
        fun createInstance() =
                ListFragment()
    }

    private var locationHandler: LocationHandler? = null

    //region Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        rvList.adapter = StationsAdapter()
        srlList.isRefreshing = true
        srlList.setOnRefreshListener(this)
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
    //endregion

    //region View
    override fun showStations(stations: List<StationPresentationModel>) {
        srlList.isRefreshing = false
        (rvList.adapter as StationsAdapter).setItems(stations)
    }

    override fun getLastKnownLocation(): LocationModel =
            locationHandler?.getLastKnownLocation() ?: LocationModel()

    override fun isLocationPermissionGranted(): Boolean = locationHandler?.isLocationPermissionGranted()
            ?: false


    //endregion

    //region LocationAwareView
    override fun onLocationUpdated(location: LocationModel){
        presenter.setLocation(location)
    }


    override fun onLocationPermissionGranted() {
        presenter.onLocationPermissionGranted()
    }
    //endregion

    //region OnRefreshListener
    override fun onRefresh() {
        presenter.onRefresh()
        srlList.isRefreshing = true
    }
    //endregion


}
