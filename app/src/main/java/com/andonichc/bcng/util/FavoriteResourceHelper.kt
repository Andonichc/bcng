package com.andonichc.bcng.util

import com.andonichc.bcng.R


const val NO_FAVORITE = "no_favorite"

fun getResourceFromType(type: String) =
    when(type) {
        NO_FAVORITE -> R.drawable.ic_favorite_border_grey
        else->
    }