package com.andonichc.bcng.data.model


data class StationApiModel(val id: Int,
                           val type: String,
                           val latitude: Double,
                           val longitude: Double,
                           val streetName: String,
                           val streetNumber: String,
                           val slots: Int,
                           val bikes: Int,
                           val status: String)