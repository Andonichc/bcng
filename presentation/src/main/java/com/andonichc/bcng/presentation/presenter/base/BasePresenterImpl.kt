package com.andonichc.bcng.presentation.presenter.base


abstract class BasePresenterImpl<out T>(protected val view: T) : BasePresenter where T : BaseView {
    override fun onCreate() {

    }

    override fun onResume() {

    }

    override fun onStop() {

    }
}