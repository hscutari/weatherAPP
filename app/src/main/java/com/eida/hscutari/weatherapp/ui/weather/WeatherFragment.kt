package com.eida.hscutari.weatherapp.ui.weather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.eida.hscutari.weatherapp.R
import com.eida.hscutari.weatherapp.common.constants.ID_DB
import com.eida.hscutari.weatherapp.common.constants.ID_LD
import com.eida.hscutari.weatherapp.common.constants.ID_SP
import com.eida.hscutari.weatherapp.common.requestLocationOrShowMessage
import com.eida.hscutari.weatherapp.common.toBitmap
import com.eida.hscutari.weatherapp.data.weather.repository.WeatherRepository
import com.eida.hscutari.weatherapp.model.PlaceWeather
import com.eida.hscutari.weatherapp.ui.utilities.LocationRequestWrapper
import com.eida.hscutari.weatherapp.ui.utilities.isNetworkAvailable
import com.eida.hscutari.weatherapp.ui.utilities.snackBarErrorMessage
import com.eida.hscutari.weatherapp.ui.utilities.snackBarNoConnection
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_weather.*


/**
 * Created by henriquescutari on 13/05/18.
 */
class WeatherFragment : Fragment(), LocationRequestWrapper.OnLocationListener {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var model: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        loadEvent()
        setUpLocation()
        setupLoadData()

        if (!context.isNetworkAvailable()) {
            context.snackBarNoConnection(container).show()
            constraintButtons.visibility = VISIBLE
            return
        }

        loadData()
    }

    private fun loadEvent() {
        main_search_view.setOnKeyListener { _, keyCode, keyevent ->
            if (keyevent.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                reloadData(main_search_view.text.toString())
                true
            } else false
        }

        btnCity1.setOnClickListener {
            getLocalData(ID_DB)
        }

        btnCity2.setOnClickListener {
            getLocalData(ID_SP)
        }

        btnCity3.setOnClickListener {
            getLocalData(ID_LD)
        }
    }

    companion object {
        fun newInstance(): Fragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun setupLoadData() {
        val factory = WeatherViewModelFactory(WeatherRepository(context))

        model = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)

        model.getWeather().observe(this, Observer<PlaceWeather> {
            it?.let {
                showData(it)
            } ?: onError()
        })

    }

    private fun loadData() {
        loadOfflineData()
    }

    private fun reloadData(city: String) {
        model.getRemoteWeather(city)
    }

    private fun loadOfflineData() {
        model.getRemoteWeather(listOf(ID_DB, ID_LD, ID_SP))
    }

    private fun getLocalData(id: String) {
        model.getLocalWeather(id)
    }

    private fun showData(placeWeather: PlaceWeather) {
        txtCity.text = placeWeather.main?.name + ", " + placeWeather.sys?.country
        txtTemp.text = placeWeather.main?.tempC.toString()
        txtTempMax.text = placeWeather.main?.convertTempMaxCelsius().toString()
        txtTempMin.text = placeWeather.main?.convertTempMinCelsius().toString()
        txtDescription.text = placeWeather.weather!![0].description

        txtHumidity.text = "${placeWeather.main?.humidity} %"
        txtPressure.text = "${placeWeather.main?.pressure} hPa"
        txtVisibility.text = placeWeather.main?.visibility
        txtWindSpeed.text = "${placeWeather.wind?.speed} mps"

        imageView.setImageBitmap(placeWeather.weather!![0].iconBitmap.toBitmap())
        txtSunrise.text = placeWeather.sys?.formatSunrise
        txtSunset.text = placeWeather.sys?.formatSunset
    }

    private fun onError() {
        if (context.isNetworkAvailable()) {
            context.snackBarErrorMessage(container).show()
        } else {
            context.snackBarNoConnection(container).show()
        }
    }

    override fun onLocation(location: Location) {
        model.getRemoteWeather(location.latitude.toString(), location.longitude.toString())
    }

    private fun setUpLocation() {
        if (requestLocationOrShowMessage(activity, 0)) {
            connectLocation()
        } else {
            constraintButtons.visibility = VISIBLE
        }
    }

    private fun connectLocation() {
        val locationRequestWrapper = LocationRequestWrapper(activity, this)
        locationRequestWrapper.connect()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        constraintButtons.visibility = GONE
        connectLocation()
    }
}