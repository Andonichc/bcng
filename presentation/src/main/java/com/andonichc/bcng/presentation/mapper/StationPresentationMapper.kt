package com.andonichc.bcng.presentation.mapper

import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.StationModel
import com.andonichc.bcng.presentation.model.StationPresentationModel
import javax.inject.Inject

class StationPresentationMapper
@Inject constructor() : ListMapper<StationModel, StationPresentationModel>() {
    override fun map(from: StationModel): StationPresentationModel =
            StationPresentationModel(
                    id = from.id,
                    type = from.type,
                    latitude = from.latitude,
                    longitude = from.longitude,
                    name = "${from.id} - ${from.streetName}, ${from.streetNumber}",
                    slots = from.slots.toString(),
                    bikes = from.bikes.toString(),
                    operative = from.operative,
                    status = from.status,
                    distance = mapDistance(from.distance),
                    favoriteId = from.favoriteId,
                    favoriteIcon = from.favoriteIcon
            )

    private fun mapDistance(distance: Int): String {
        if (distance < 0) return ""

        return if (distance / 1000 > 0) {
            "${distance / 1000}km"
        } else {
            "${distance}m"
        }
    }
}