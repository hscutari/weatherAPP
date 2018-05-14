package com.eida.hscutari.weatherapp.ui.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.view.View
import com.eida.hscutari.weatherapp.R
import android.support.design.widget.Snackbar

/**
 * Created by henriquescutari on 13/05/18.
 */

fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    return info != null && info.isConnected && info.isAvailable
}

fun Context.snackBarErrorMessage(@NonNull view: View): Snackbar {
    return Snackbar.make(view, R.string.error_message, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(ContextCompat.getColor(this, R.color.button))
            .setDuration(Snackbar.LENGTH_SHORT)
}

fun Context.snackBarNoConnection(@NonNull view: View): Snackbar {
    return Snackbar.make(view, R.string.title_no_connection, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(ContextCompat.getColor(this, R.color.button))
            .setDuration(Snackbar.LENGTH_SHORT)
}