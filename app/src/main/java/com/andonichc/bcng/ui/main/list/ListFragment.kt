package com.andonichc.bcng.ui.main.list

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
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : BaseFragment<ListPresenter>(), ListView {

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
    //endregion

    //region_View
    override fun showStations(stations: List<StationPresentationModel>) {
        (rvList.adapter as StationsAdapter).setItems(stations)
    }
    //endregion


}
