package com.eida.hscutari.weatherapp.data.weather.remote

import com.eida.hscutari.weatherapp.common.constants
import com.eida.hscutari.weatherapp.data.weather.WeatherClient
import com.eida.hscutari.weatherapp.data.weather.api.IWeatherAPI
import com.eida.hscutari.weatherapp.model.PlaceWeather
import io.reactivex.Observable

/**
 * Created by henriquescutari on 13/05/18.
 */
class RemoteWeatherDataSource {

    private val _weatherAPI: IWeatherAPI? = WeatherClient().getWeather()

    fun getData(location: String): Observable<PlaceWeather> {
        return _weatherAPI!!.getData(location, constants.API_KEY)
    }

    fun getData(lat: String, lon: String): Observable<PlaceWeather> {
        return _weatherAPI!!.getData(lat, lon, "1", constants.API_KEY)
    }

    fun getSeveral(location: String): Observable<PlaceWeather> {
        return _weatherAPI!!.getSeveral(location, constants.API_KEY)
    }
}

