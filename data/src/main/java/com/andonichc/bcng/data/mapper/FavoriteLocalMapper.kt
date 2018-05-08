package com.andonichc.bcng.data.mapper

import com.andonichc.bcng.data.model.FavoriteLocalModel
import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class FavoriteLocalMapper
@Inject constructor() : ListMapper<FavoriteLocalModel, FavoriteModel>() {

    private val gson = Gson()
    private val listType = object : TypeToken<List<Int>>() {}.type

    override fun map(from: FavoriteLocalModel): FavoriteModel =
            FavoriteModel(
                    id = from.id,
                    name = from.name,
                    icon = from.icon,
                    stationsIds = gson.fromJson(from.stationsIds, listType)
            )
}