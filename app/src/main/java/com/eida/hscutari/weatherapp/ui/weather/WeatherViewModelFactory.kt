package com.eida.hscutari.weatherapp.ui.weather

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.eida.hscutari.weatherapp.data.weather.repository.WeatherRepository

/**
 * Created by henriquescutari on 13/05/18.
 */
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}