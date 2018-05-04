package com.andonichc.bcng.util

import android.support.annotation.DrawableRes
import android.view.View
import android.widget.TextView


fun TextView.setRightDrawable(@DrawableRes drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(
            0, 0, drawable, 0
    )
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}