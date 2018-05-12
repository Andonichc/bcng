package com.andonichc.bcng.ui.main

import com.andonichc.bcng.presentation.presenter.main.MainPresenter
import com.andonichc.bcng.presentation.presenter.main.MainPresenterImpl
import com.andonichc.bcng.presentation.presenter.main.MainView
import com.andonichc.bcng.ui.main.map.MapFragment
import com.andonichc.bcng.util.LocationChecker
import dagger.Module
import dagger.Provides


@Module
class MainActivityModule {

    @Provides
    fun providesMainView(mainActivity: MainActivity): MainView =
            mainActivity

    @Provides
    fun providesMainPresenter(view: MainView): MainPresenter =
            MainPresenterImpl(view)

    @Provides
    fun providesLocationChecker(mainActivity: MainActivity): LocationChecker =
            LocationChecker(mainActivity)
}