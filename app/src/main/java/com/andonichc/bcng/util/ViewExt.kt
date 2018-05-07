package com.andonichc.bcng.util

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.TypedValue
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

fun Int.toDp(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics).toInt()
}