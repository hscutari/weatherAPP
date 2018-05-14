package com.eida.hscutari.weatherapp.data.weather.local

import android.content.Context
import com.eida.hscutari.weatherapp.data.weather.WeatherDatabase
import com.eida.hscutari.weatherapp.data.weather.db.MainDao
import com.eida.hscutari.weatherapp.data.weather.db.SysDao
import com.eida.hscutari.weatherapp.data.weather.db.WeatherDao
import com.eida.hscutari.weatherapp.data.weather.db.WindDao
import com.eida.hscutari.weatherapp.model.PlaceWeather
import com.eida.hscutari.weatherapp.model.Weather
import io.reactivex.Observable
import java.io.IOException

/**
 * Created by henriquescutari on 13/05/18.
 */
class LocalWeatherDataSource(context: Context) {
    private var dao: WeatherDao
    private var daoSys: SysDao
    private var daoMain: MainDao
    private var daoWind: WindDao

    init {
        val db = WeatherDatabase.getDatabase(context)
        dao = db.getWeatherDao()
        daoSys = db.getSysDao()
        daoMain = db.getMainDao()
        daoWind = db.getWindDao()
    }

    fun get(id: String): Observable<PlaceWeather> {
        return Observable.fromCallable { dao.getWeather(id) }
    }

    fun saveAll(placeWeather: PlaceWeather): Observable<Boolean> {
        return Observable.create { subscriber ->
            try {
                dao.save(placeWeather.weather!![0])
                daoSys.save(placeWeather.sys!!)
                daoMain.save(placeWeather.main!!)
                daoWind.save(placeWeather.wind!!)

                subscriber.onNext(true)
                subscriber.onComplete()
            } catch (e: IOException) {
                e.localizedMessage
                if (!subscriber.isDisposed) {
                    subscriber.onError(e)
                }
            }
        }
    }
}