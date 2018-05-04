package com.andonichc.bcng.di

import com.andonichc.bcng.ui.base.BaseActivity
import com.andonichc.bcng.ui.base.BaseFragment


interface ActivityInjector {
    fun inject(activity: BaseActivity<*>)
    fun inject(fragment: BaseFragment<*>)
}