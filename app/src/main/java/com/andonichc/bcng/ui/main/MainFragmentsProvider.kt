package com.andonichc.bcng.ui.main

import com.andonichc.bcng.ui.main.list.ListFragment
import com.andonichc.bcng.ui.main.list.ListFragmentModule
import com.andonichc.bcng.ui.main.map.MapFragment
import com.andonichc.bcng.ui.main.map.MapFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentsProvider {

    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun bindsMapFragment(): MapFragment

    @ContributesAndroidInjector(modules = [ListFragmentModule::class])
    abstract fun bindsListFragment(): ListFragment
}