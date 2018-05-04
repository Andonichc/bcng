package com.andonichc.bcng.data.mapper

import com.andonichc.bcng.data.model.StationApiModel
import com.andonichc.bcng.domain.ListMapper
import com.andonichc.bcng.domain.model.*
import javax.inject.Inject


const val API_TYPE_ELECTRIC = "BIKE-ELECTRIC"
const val API_TYPE_MECHANICAL = "BIKE"

const val API_OPEN = "OPN"

class StationApiMapper
@Inject constructor() : ListMapper<StationApiModel, StationModel>() {
    override fun map(from: StationApiModel): StationModel =
            StationModel(
                    id = from.id,
                    type = mapType(from.type),
                    latitude = from.latitude,
                    longitude = from.longitude,
                    streetName = from.streetName,
                    streetNumber = from.streetNumber,
                    slots = from.slots,
                    bikes = from.bikes,
                    operative = from.status == API_OPEN,
                    status = mapStatus(from.bikes, from.slots)
            )

    private fun mapStatus(bikes: Int, slots: Int): Int {
        val totalSlots = slots + bikes
        val ratioAvailable: Float = bikes.toFloat() / totalSlots.toFloat()

        return when (ratioAvailable) {
            0F -> EMPTY
            in 0F..0.4F -> ALMOST_EMPTY
            in 0.41F..0.6F -> HALF_FULL
            in 0.61F..0.99F -> ALMOST_FULL
            else -> FULL
        }
    }

    private fun mapType(type: String): Int =
            when (type) {
                API_TYPE_ELECTRIC -> TYPE_ELECTRIC
                else -> TYPE_NORMAL
            }
}