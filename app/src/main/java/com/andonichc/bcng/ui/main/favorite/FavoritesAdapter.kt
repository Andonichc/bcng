package com.andonichc.bcng.ui.main.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.util.getResourceFromType
import kotlinx.android.synthetic.main.item_favorite.view.*


abstract class FavoritesAdapter(private val items: List<FavoritePresentationModel>)
    : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    abstract fun onFavoriteClicked(item: FavoritePresentationModel)

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var item: FavoritePresentationModel? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: FavoritePresentationModel) {
            this.item = item
            with(itemView) {
                ivFavorite.setImageResource(getResourceFromType(item.icon))
                tvFavorite.text = item.name
            }
        }

        override fun onClick(p0: View?) {
            item?.let {
                onFavoriteClicked(it)
            }
        }
    }
}