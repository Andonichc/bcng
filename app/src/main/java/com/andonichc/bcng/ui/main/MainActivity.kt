package com.andonichc.bcng.ui.main

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.LocationModel
import com.andonichc.bcng.presentation.presenter.main.LocationAwareView
import com.andonichc.bcng.presentation.presenter.main.LocationHandler
import com.andonichc.bcng.presentation.presenter.main.MainPresenter
import com.andonichc.bcng.presentation.presenter.main.MainView
import com.andonichc.bcng.ui.base.BaseActivity
import com.andonichc.bcng.ui.main.list.ListFragment
import com.andonichc.bcng.ui.main.map.MapFragment
import com.andonichc.bcng.util.LocationChecker
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainPresenter>(), MainView, LocationHandler {

    private val mapFragment: MapFragment by lazy { MapFragment.createInstance() }
    private val listFragment: ListFragment by lazy { ListFragment.createInstance() }

    private lateinit var menuItem: MenuItem

    private var currentFragment: Fragment = mapFragment

    @Inject
    lateinit var locationChecker: LocationChecker
    private var locationManager: LocationManager? = null

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
        menuItem = menu.findItem(R.id.action_change_view)
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

    override fun onBackPressed() {
        if (currentFragment is ListFragment) {
            changeFragment(menuItem)
        } else {
            super.onBackPressed()
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

    override fun notifyLocationUpdate(location: LocationModel) {
        val currentFragment = currentFragment
        if (currentFragment is LocationAwareView) {
            currentFragment.onLocationUpdated(location)
        }
    }

    override fun requestLocationPermission() {
        locationChecker.check(onSuccess = presenter::onLocationPermissionGranted)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationChecker.onRequestPermissionResult(requestCode, grantResults)
    }

    override fun notifyLocationPermissionGranted() {
        val currentFragment = currentFragment
        if (currentFragment is LocationAwareView && currentFragment.isAdded) {
            currentFragment.onLocationPermissionGranted()
        }
    }

    @SuppressLint("MissingPermission")
    override fun setLocationChangesListener() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 1000F, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                location?.run { presenter.onLocationUpdated(latitude, longitude) }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

            override fun onProviderEnabled(p0: String?) {}

            override fun onProviderDisabled(p0: String?) {}
        })
    }

    override fun getLastKnownLocation(): LocationModel = presenter.getLastKnownLocation()

    override fun isLocationPermissionGranted(): Boolean = presenter.isLocationPermissionGranted()
}
