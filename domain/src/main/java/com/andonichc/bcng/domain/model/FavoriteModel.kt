package com.andonichc.bcng.domain.model


data class FavoriteModel(
        val id: Int,
        val name: String,
        val icon: String,
        val stationsIds: List<Int>)