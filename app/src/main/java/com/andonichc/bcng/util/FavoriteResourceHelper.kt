package com.andonichc.bcng.util

import android.support.annotation.DrawableRes
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.GYM
import com.andonichc.bcng.presentation.model.HOME
import com.andonichc.bcng.presentation.model.SCHOOL
import com.andonichc.bcng.presentation.model.WORK

@DrawableRes
fun getResourceFromType(type: String): Int =
        when (type) {
            SCHOOL -> R.drawable.ic_favorite_school
            WORK -> R.drawable.ic_favorite_work
            GYM -> R.drawable.ic_favorite_gym
            HOME -> R.drawable.ic_favorite_home
            else -> R.drawable.ic_favorite_border_grey
        }