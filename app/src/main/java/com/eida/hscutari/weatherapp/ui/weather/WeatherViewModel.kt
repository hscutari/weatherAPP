package com.eida.hscutari.weatherapp.ui.weather

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.eida.hscutari.weatherapp.data.weather.repository.WeatherRepository
import com.eida.hscutari.weatherapp.model.PlaceWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by henriquescutari on 13/05/18.
 */
class WeatherViewModel(private var weatherRepository: WeatherRepository) : ViewModel() {

    private var placeWeather: MutableLiveData<PlaceWeather> = MutableLiveData()

    fun getWeather(): LiveData<PlaceWeather> {
        return placeWeather
    }

    fun getRemoteWeather(location: String) {
        weatherRepository.getRemoteWeather(location)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                placeWeather.value = data
            }, {
                placeWeather.value = null
            })
    }

    fun getRemoteWeather(lat: String, lon: String) {
        weatherRepository.getRemoteWeather(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    placeWeather.value = data
                }, {
                    placeWeather.value = null
                })
    }

    fun getRemoteWeather(location: List<String>) {
        weatherRepository.getRemoteWeather(location)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    saveWeather(data)
                }, {
                    placeWeather.value = null
                })
    }

    fun getLocalWeather(id: String){
        weatherRepository.getLocalWeather(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data -> placeWeather.value = data
                }, {
                    placeWeather.value = null
                })
    }

    private fun saveWeather(placeWeather: PlaceWeather){
        placeWeather.list?.map {
            weatherRepository.saveLocal(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
    }
}