package com.andonichc.bcng.ui.base

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.andonichc.bcng.BcngApplication
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.presenter.base.BasePresenter
import com.andonichc.bcng.presentation.presenter.base.BaseView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import java.lang.reflect.Modifier
import javax.inject.Inject


abstract class BaseActivity<T : BasePresenter> : AppCompatActivity(), BaseView, HasFragmentInjector {

    @Inject
    @VisibleForTesting(otherwise = Modifier.PROTECTED)
    lateinit var presenter: T

    private val bcngApp: BcngApplication
        get() = application as BcngApplication

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        bcngApp.injector.inject(this)
        super.onCreate(savedInstanceState)
        initView()

        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showErrorState() {
        Snackbar.make(findViewById(android.R.id.content), R.string.error_generic, Snackbar.LENGTH_LONG)
                .show()
    }

    protected abstract fun initView()

    protected fun BaseActivity<*>.snackBar(@StringRes message: Int): Snackbar? {
        findViewById<View>(android.R.id.content)?.let {
            return Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        }

        return null
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}