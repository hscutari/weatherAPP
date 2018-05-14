package com.eida.hscutari.weatherapp.data.weather.repository

import android.content.Context
import com.eida.hscutari.weatherapp.data.weather.local.LocalWeatherDataSource
import com.eida.hscutari.weatherapp.data.weather.remote.RemoteWeatherDataSource
import com.eida.hscutari.weatherapp.model.PlaceWeather
import com.eida.hscutari.weatherapp.model.Weather
import io.reactivex.Observable

/**
 * Created by henriquescutari on 13/05/18.
 */
class WeatherRepository(context: Context) {
    private var _localSource: LocalWeatherDataSource = LocalWeatherDataSource(context)
    private var _remoteSource: RemoteWeatherDataSource = RemoteWeatherDataSource()

    fun getRemoteWeather(location: String): Observable<PlaceWeather> {
        return _remoteSource
                .getData(location)
                .map {
                    it.generate()
                }
    }

    fun getRemoteWeather(lat: String, lon: String): Observable<PlaceWeather> {
        return _remoteSource
                .getData(lat, lon)
                .map {
                    it.generate()
                }
    }

    fun getRemoteWeather(location: List<String>): Observable<PlaceWeather> {
        return _remoteSource
                .getSeveral(location.joinToString(","))
                .map {
                    it.list?.let {
                        it.map {
                            it.generate()
                        }
                    }
                    it
                }
    }

    fun getLocalWeather(id: String): Observable<PlaceWeather> {
        return _localSource.get(id).map { it.generate() }
    }

    fun saveLocal(placeWeather: PlaceWeather): Observable<Boolean> {
        return _localSource.saveAll(placeWeather)
    }

    private fun PlaceWeather.generate() : PlaceWeather {
        this.main?.convertToC()
        this.main?.convertToF()
        this.main?.setId(this.id)

        if(!this.name.isNullOrEmpty()){
            this.main?.name = this.name
        }

        if(!this.visibility.isNullOrEmpty()) {
            this.main?.visibility = this.visibility
        }

        this.sys?.setId(this.id)
        this.wind?.setId(this.id)
        this.weather!![0].setWeatherId(this.id)
        this.weather!![0].getNewIcon()
        this.sys?.format()
        return this
    }
}