package com.andonichc.bcng.ui.main.favorite

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import com.andonichc.bcng.util.gone
import com.andonichc.bcng.util.toBundle
import com.andonichc.bcng.util.toFavoritesList
import kotlinx.android.synthetic.main.dialog_favorite_select.*


class FavoriteSelectDialog : DialogFragment() {
    companion object {

        fun newInstance(favorites: List<FavoritePresentationModel>): FavoriteSelectDialog {
            val fragment = FavoriteSelectDialog()
            fragment.arguments = favorites.toBundle()
            return fragment
        }
    }

    var favorites: List<FavoritePresentationModel> = emptyList()

    var favoriteSelectListener: FavoriteSelectedListener? = null
    var favoriteAddListener: FavoriteAddListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.toFavoritesList().let {
            favorites = it
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_favorite_select, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavorites.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = object : FavoritesAdapter(favorites) {
                override fun onFavoriteClicked(item: FavoritePresentationModel) {
                    favoriteSelectListener?.onFavoriteSelected(item)
                    dismiss()
                }
            }
        }

        if (favorites.size > 4) btnAdd.gone()
        btnAdd.setOnClickListener {
            favoriteAddListener?.onFavoriteAdd()
            dismiss()
        }
    }

    interface FavoriteAddListener {
        fun onFavoriteAdd()
    }
}