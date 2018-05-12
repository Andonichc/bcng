package com.andonichc.bcng.ui.main.favorite

import com.andonichc.bcng.presentation.model.FavoritePresentationModel


interface FavoriteSelectedListener {
    fun onFavoriteSelected(favorite: FavoritePresentationModel)
}