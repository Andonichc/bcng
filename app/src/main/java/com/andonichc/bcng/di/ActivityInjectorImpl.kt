package com.andonichc.bcng.di

import com.andonichc.bcng.ui.base.BaseActivity
import dagger.android.AndroidInjection


class ActivityInjectorImpl : ActivityInjector {

    override fun inject(activity: BaseActivity<*>) {
        AndroidInjection.inject(activity)
    }
}