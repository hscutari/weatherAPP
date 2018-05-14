package com.eida.hscutari.weatherapp.ui.main

import android.os.Bundle
import com.eida.hscutari.weatherapp.R
import com.eida.hscutari.weatherapp.ui.base.BaseActivity
import com.eida.hscutari.weatherapp.ui.weather.WeatherFragment
import com.facebook.stetho.Stetho

class MainActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.activity_main)
        loadFragment(WeatherFragment.newInstance())
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        fragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}
