package com.andonichc.bcng.ui.main.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.ui.main.StationDetailView
import com.andonichc.bcng.util.toDp
import kotlinx.android.synthetic.main.view_station_detail.view.*


abstract class StationsAdapter : RecyclerView.Adapter<StationsAdapter.StationViewHolder>() {

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
        items.clear()
        items.addAll(stations)
        notifyDataSetChanged()

    }

    abstract fun onFavorited(station: StationPresentationModel)
    abstract fun onUnfavorited(station: StationPresentationModel)

    inner class StationViewHolder(view: View) : RecyclerView.ViewHolder(view), StationDetailView.FavoriteListener {
        var item: StationPresentationModel? = null

        fun bind(station: StationPresentationModel) {
            item = station
            (itemView as? StationDetailView)?.run {
                bind(station)
                favoriteListener = this@StationViewHolder
            }
        }

        override fun onFavorited() {
            item?.let {
                onFavorited(it)
            }
        }

        override fun onUnfavorited() {
            item?.let {
                onUnfavorited(it)
            }
        }
    }
}