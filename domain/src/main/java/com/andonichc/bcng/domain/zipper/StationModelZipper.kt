package com.andonichc.bcng.domain.zipper

import com.andonichc.bcng.domain.Zipper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.domain.model.StationModel
import javax.inject.Inject


class StationModelZipper
@Inject constructor() : Zipper<List<StationModel>, List<FavoriteModel>, List<StationModel>> {
    override fun zip(stations: List<StationModel>, favorites: List<FavoriteModel>): List<StationModel> {
        stations.forEach { station ->
            favorites.forEach foreach@ { favorite ->
                if (favorite.stationsIds.contains(station.id)) {
                    station.favoriteIcon = favorite.icon
                    station.favoriteId = favorite.id
                    return@forEach
                }
            }
        }
        return stations
    }
}