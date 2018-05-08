package com.andonichc.bcng.data.mapper

import com.andonichc.bcng.data.model.FavoriteLocalModel
import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.google.gson.Gson
import javax.inject.Inject


class FavoriteModelMapper
@Inject constructor() : ListMapper<FavoriteModel, FavoriteLocalModel>() {

    private val gson = Gson()

    override fun map(from: FavoriteModel): FavoriteLocalModel =
            FavoriteLocalModel(
                    id = from.id,
                    name = from.name,
                    icon = from.icon,
                    stationsIds = gson.toJson(from.stationsIds)
            )
}