package com.andonichc.bcng.ui.main.list

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.presentation.presenter.main.LocationHandler
import com.andonichc.bcng.presentation.presenter.main.list.ListPresenter
import com.andonichc.bcng.presentation.presenter.main.list.ListView
import com.andonichc.bcng.ui.base.BaseFragment
import com.andonichc.bcng.ui.main.favorite.AddFavoriteDialog
import com.andonichc.bcng.ui.main.favorite.FavoriteSelectDialog
import com.andonichc.bcng.ui.main.favorite.FavoriteSelectedListener
import com.andonichc.bcng.util.getWhiteResourceFromType
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : BaseFragment<ListPresenter>(), ListView, SwipeRefreshLayout.OnRefreshListener,
        FavoriteSelectedListener,
        FavoriteSelectDialog.FavoriteAddListener {


    companion object {
        private const val FAVORITE_SELECTION = "favorite_selection"
        private const val FAVORITE_ADD = "favorite_add"

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
        rvList.adapter = object : StationsAdapter() {
            override fun onFavorited(station: StationPresentationModel) {
                presenter.onItemFavorited(station)
            }

            override fun onUnfavorited(station: StationPresentationModel) {
                presenter.onItemUnFavorited(station)
            }
        }
        srlList.isRefreshing = true
        srlList.setOnRefreshListener(this)

        fabMenu.setOnMenuToggleListener { opened ->
            presenter.onMenuToggle(opened)
            if (opened) {
                fabMenu.menuIconView.setImageResource(R.drawable.fab_add)
            } else {
                fabMenu.menuIconView.setImageResource(R.drawable.ic_favorite_border_white_36dp)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LocationHandler) {
            locationHandler = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is LocationHandler) {
            locationHandler = activity
        } else {
            throw RuntimeException(activity!!.toString() + " must implement OnFragmentInteractionListener")
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
    override fun onLocationUpdated(location: LocationModel) {
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

    //region Favorites
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

    override fun showFavoriteSelectionDialog(favorites: List<FavoritePresentationModel>) {
        val dialog = FavoriteSelectDialog.newInstance(favorites)
        dialog.show(fragmentManager, FAVORITE_SELECTION)
        dialog.favoriteSelectListener = this
        dialog.favoriteAddListener = this
    }

    override fun onShowFavorites(favorites: List<FavoritePresentationModel>) {
        fabMenu.removeAllMenuButtons()
        favorites.forEach { favorite ->
            val button = FloatingActionButton(activity).apply {
                buttonSize = FloatingActionButton.SIZE_MINI
                labelText = favorite.name
                setImageResource(getWhiteResourceFromType(favorite.icon))
                setOnClickListener {
                    presenter.onFavoriteSelected(favorite)
                }
                setOnLongClickListener {
                    presenter.onFavoriteLongClick(favorite)
                    false
                }
            }
            fabMenu.addMenuButton(button)
        }
    }

    override fun showDeleteDialog(favorite: FavoritePresentationModel) {
        AlertDialog.Builder(activity)
                .setTitle(getString(R.string.delete_dialog_title, favorite.name))
                .setPositiveButton(R.string.delete, { dialog, _ ->
                    presenter.onAcceptDeleteFavorite(favorite)
                    dialog.dismiss()
                })
                .setNegativeButton(R.string.cancel, { dialog, _ ->
                    dialog.dismiss()
                })
                .create()
                .show()
    }
    //endregion


}
