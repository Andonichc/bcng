package com.andonichc.bcng.domain.model

const val EMPTY = 0
const val ALMOST_EMPTY = 1
const val HALF_FULL = 2
const val ALMOST_FULL = 3
const val FULL = 4

const val TYPE_NORMAL = 0
const val TYPE_ELECTRIC = 1

data class StationModel(val id: Int,
                        val type: Int,
                        val latitude: Double,
                        val longitude: Double,
                        val streetName: String,
                        val streetNumber: String,
                        val slots: Int,
                        val bikes: Int,
                        val operative: Boolean,
                        val status: Int)