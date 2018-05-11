package com.andonichc.bcng.util

import android.os.Bundle
import com.andonichc.bcng.presentation.model.FavoritePresentationModel

private const val ID = "id"
private const val NAME = "name"
private const val ICON = "icon"
private const val STATIONS_ID = "stations_id"


fun FavoritePresentationModel.toBundle(): Bundle =
        Bundle().apply {
            putInt(ID, id)
            putString(NAME, name)
            putString(ICON, icon)
            putIntegerArrayList(STATIONS_ID, ArrayList(stationsIds))
        }

fun Bundle.toFavoritePresentationModel(): FavoritePresentationModel =
        FavoritePresentationModel(
                id = getInt(ID),
                name = getString(NAME),
                icon = getString(ICON),
                stationsIds = getIntegerArrayList(STATIONS_ID)
        )

fun List<FavoritePresentationModel>.toBundle(): Bundle {
    val bundle = Bundle()
    forEachIndexed { i, model ->
        bundle.putBundle(i.toString(), model.toBundle())
    }
    return bundle
}

fun Bundle.toFavoritesList(): List<FavoritePresentationModel> {
    val list = mutableListOf<FavoritePresentationModel>()

    (0 until size()).forEach {
        i -> list.add(getBundle(i.toString())
            .toFavoritePresentationModel())
    }
    return list
}
