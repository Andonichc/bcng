package com.andonichc.bcng.di

import com.andonichc.bcng.ui.base.BaseActivity


interface ActivityInjector {
    fun inject(activity: BaseActivity<*>)
}