package com.andonichc.bcng.presentation.presenter.base


interface BaseView {
    fun showErrorState()
}

interface BasePresenter {
    fun onCreate()
    fun onResume()
    fun onStop()
}