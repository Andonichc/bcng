package com.andonichc.bcng.data.cache

import com.andonichc.bcng.domain.model.StationModel


object StationsMemoryCache {
    var stationsList: List<StationModel> = listOf()

    fun isValid(): Boolean = stationsList.isNotEmpty()
}