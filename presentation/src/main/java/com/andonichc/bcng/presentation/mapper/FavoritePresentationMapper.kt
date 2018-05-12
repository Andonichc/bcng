package com.andonichc.bcng.presentation.mapper

import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import javax.inject.Inject


class FavoritePresentationMapper
@Inject constructor() : ListMapper<FavoriteModel, FavoritePresentationModel>() {
    override fun map(from: FavoriteModel): FavoritePresentationModel =
            FavoritePresentationModel(
                    id = from.id,
                    name = from.name,
                    icon = from.icon,
                    stationsIds = from.stationsIds.toMutableList()
            )
}