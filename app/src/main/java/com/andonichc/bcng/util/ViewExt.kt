package com.andonichc.bcng.util

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
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

fun ImageView.setTint(@ColorRes color: Int) {
    setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);
}

fun Int.toDp(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics).toInt()
}