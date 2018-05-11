package com.andonichc.bcng.presentation.mapper

import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.presentation.model.FavoritePresentationModel
import javax.inject.Inject


class FavoriteDomainMapper
@Inject constructor() : ListMapper<FavoritePresentationModel, FavoriteModel>() {
    override fun map(from: FavoritePresentationModel): FavoriteModel =
            FavoriteModel(
                    id = from.id,
                    name = from.name,
                    icon = from.icon,
                    stationsIds = from.stationsIds
            )
}