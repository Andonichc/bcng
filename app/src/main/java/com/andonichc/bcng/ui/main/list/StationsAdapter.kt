package com.andonichc.bcng.ui.main.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.ui.main.StationDetailView
import com.andonichc.bcng.util.toDp
import kotlinx.android.synthetic.main.view_station_detail.view.*


class StationsAdapter : RecyclerView.Adapter<StationsAdapter.StationViewHolder>() {

    private val items: MutableList<StationPresentationModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_station_list, parent, false)

        val margin = 8.toDp(parent.context)

        (view.cvStation.layoutParams as FrameLayout.LayoutParams).apply {
            setMargins(margin, margin, margin, margin)
        }
        return StationViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(stations: List<StationPresentationModel>) {
        items.addAll(stations)
        notifyItemRangeInserted(items.size - stations.size, stations.size)

    }

    inner class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(station: StationPresentationModel) {
            (itemView as? StationDetailView)?.bind(station)
        }
    }
}