package com.andonichc.bcng.ui.base

import android.app.Fragment
import android.os.Bundle
import android.view.View
import com.andonichc.bcng.BcngApplication
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import javax.inject.Inject


abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView {

    @Inject
    protected lateinit var presenter: T

    protected val baseActivity: BaseActivity<*>?
    get() = activity as? BaseActivity<*>

    private val bcngApp: BcngApplication?
    get() = activity?.application as BcngApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bcngApp?.injector?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onStop()
    }

    override fun showErrorState() {
        baseActivity?.showErrorState()
    }
}