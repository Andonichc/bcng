package com.andonichc.bcng.util

import android.support.annotation.DrawableRes
import com.andonichc.bcng.R
import com.andonichc.bcng.domain.model.*


@DrawableRes
fun getMarker(status: Int, type: Int): Int =
        when (type) {
            TYPE_ELECTRIC -> getMarkerElectric(status)
            else -> getMarkerNormalFromStatus(status)
        }

@DrawableRes
private fun getMarkerElectric(status: Int): Int =
        when (status) {
            EMPTY -> R.drawable.marker_electric_empty
            ALMOST_EMPTY -> R.drawable.marker_electric_almost_empty
            HALF_FULL -> R.drawable.marker_electric_half
            ALMOST_FULL-> R.drawable.marker_electric_almost_full
            else -> R.drawable.marker_electric_full
        }

@DrawableRes
private fun getMarkerNormalFromStatus(status: Int): Int =
    when (status) {
        EMPTY -> R.drawable.marker_empty
        ALMOST_EMPTY -> R.drawable.marker_almost_empty
        HALF_FULL -> R.drawable.marker_half
        ALMOST_FULL-> R.drawable.marker_almost_full
        else -> R.drawable.marker_full
    }