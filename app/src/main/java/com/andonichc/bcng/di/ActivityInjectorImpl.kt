package com.andonichc.bcng.di

import com.andonichc.bcng.ui.base.BaseActivity
import com.andonichc.bcng.ui.base.BaseFragment
import dagger.android.AndroidInjection


class ActivityInjectorImpl : ActivityInjector {

    override fun inject(activity: BaseActivity<*>) {
        AndroidInjection.inject(activity)
    }

    override fun inject(fragment: BaseFragment<*>) {
        AndroidInjection.inject(fragment)
    }
}