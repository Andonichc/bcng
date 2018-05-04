package com.andonichc.bcng.ui.main

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.presenter.main.MainPresenter
import com.andonichc.bcng.presentation.presenter.main.MainView
import com.andonichc.bcng.ui.base.BaseActivity
import com.andonichc.bcng.ui.main.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>(), MainView, MapFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fragmentManager.beginTransaction()
                .add(R.id.mainContainer, MapFragment.createInstance())
                .commit()
    }

    override fun initView() {

    }

    override fun showErrorState() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
