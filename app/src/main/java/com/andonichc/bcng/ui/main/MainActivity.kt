package com.andonichc.bcng.ui.main

import android.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.presenter.main.MainPresenter
import com.andonichc.bcng.presentation.presenter.main.MainView
import com.andonichc.bcng.ui.base.BaseActivity
import com.andonichc.bcng.ui.main.list.ListFragment
import com.andonichc.bcng.ui.main.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>(), MainView, MapFragment.OnFragmentInteractionListener {

    private val mapFragment: MapFragment by lazy { MapFragment.createInstance() }
    private val listFragment: ListFragment by lazy { ListFragment.createInstance() }

    private var currentFragment: Fragment = mapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun initView() {
        setFragment(mapFragment)
    }

    override fun showErrorState() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_view -> {
                changeFragment(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeFragment(item: MenuItem) {
        if (currentFragment is MapFragment) {
            setFragment(listFragment)
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_map, null)
        } else {
            setFragment(mapFragment)
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_list, null)
        }
    }

    private fun setFragment(fragment: Fragment) {
        currentFragment = fragment
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
