package com.andonichc.bcng.presentation.model

const val DEFAULT_LAT = 41.3870154
const val DEFAULT_LON = 2.1678584

data class LocationModel(val latitude: Double = DEFAULT_LAT,
                         val longitude: Double = DEFAULT_LON)