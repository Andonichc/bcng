package com.andonichc.bcng.util

import android.Manifest
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat


class LocationChecker(val fragment: Fragment) {

    companion object {
        const val PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        const val LOCATION_PERMISSION_REQUEST = 1001
    }

    private var onSuccessCallback: (() -> Unit)? = null

    private var onErrorCallback: (() -> Unit)? = null

    fun check(onSuccess: (() -> Unit)?, onError: (() -> Unit)? = null) {

        onSuccessCallback = onSuccess
        onErrorCallback = onError

        if (ContextCompat.checkSelfPermission(fragment.activity,
                        PERMISSION)
                != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST)

        } else {
            onSuccessCallback?.invoke()
        }
    }

    fun onRequestPermissionResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSuccessCallback?.invoke()
                } else {

                }
            }
        }
    }
}