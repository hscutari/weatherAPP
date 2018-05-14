package com.eida.hscutari.weatherapp.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

/**
 * Created by henriquescutari on 14/05/18.
 */

private fun hasPermission(context: Context, permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}
fun Context.hasLocationPermission(): Boolean {
    return (hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            && hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION))
}


private fun shouldShowRequestPermissionRationale(activity: Activity): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
}


private fun requestPermissions(activity: Activity, requestCode: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
}


private fun showLocationRequestPermission(activity: Activity, requestCod: Int) {
    if (shouldShowRequestPermissionRationale(activity)) {
        requestPermissions(activity, requestCod,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

fun requestLocationOrShowMessage(activity: Activity, requestCode: Int): Boolean {
    if (!activity.hasLocationPermission()) {
        showLocationRequestPermission(activity, requestCode)
        return false
    }
    return true
}