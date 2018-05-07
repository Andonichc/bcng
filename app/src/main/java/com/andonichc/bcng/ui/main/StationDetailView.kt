package com.andonichc.bcng.ui.main

import android.content.Context
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.andonichc.bcng.R
import com.andonichc.bcng.domain.model.TYPE_ELECTRIC
import com.andonichc.bcng.presentation.model.StationPresentationModel
import com.andonichc.bcng.util.setRightDrawable
import kotlinx.android.synthetic.main.view_station_detail.view.*


class StationDetailView : FrameLayout {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
            super(context, attrs, defStyleAttr)

    @RequiresApi(21)
    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = -1,
                defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.view_station_detail, this)
    }

    fun bind(item: StationPresentationModel) {
        tvTitle.text = item.name
        tvBikes.text = item.bikes
        tvSlots.text = item.slots
        tvDistance.text = item.distance

        bindType(item.type)
    }

    private fun bindType(type: Int) {
        when(type) {
            TYPE_ELECTRIC -> {
                tvBikes.setRightDrawable(R.drawable.ic_bike_electric)
                tvSlots.setRightDrawable(R.drawable.ic_slots_electric)
            }
            else -> {
                tvBikes.setRightDrawable(R.drawable.ic_bike)
                tvSlots.setRightDrawable(R.drawable.ic_slots)
            }
        }
    }
}