package com.andonichc.bcng.di

import com.andonichc.bcng.ui.main.MainActivity
import com.andonichc.bcng.ui.main.MainActivityModule
import com.andonichc.bcng.ui.main.MainFragmentsProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentsProvider::class])
    abstract fun bindMainActivity(): MainActivity
}