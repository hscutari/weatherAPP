package com.eida.hscutari.weatherapp.ui.base

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eida.hscutari.weatherapp.R
import com.eida.hscutari.weatherapp.ui.main.MainActivity

/**
 * Created by henriquescutari on 13/05/18.
 */
open class BaseActivity : AppCompatActivity() {
    fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            try {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.main_container, fragment, "fragmentBase")
                fragmentTransaction.commitAllowingStateLoss()
            } catch (e: Exception) {
                Log.e(MainActivity::class.java.name, "Error in show view")
            }
        }
    }
}