package com.andonichc.bcng.presentation.model

const val NO_FAVORITE = "no_favorite"
const val SCHOOL = "school"
const val WORK = "work"
const val GYM = "gym"
const val HOME = "home"

data class FavoritePresentationModel(
        val id: Int,
        val name: String,
        val icon: String,
        val stationsIds: MutableList<Int>)