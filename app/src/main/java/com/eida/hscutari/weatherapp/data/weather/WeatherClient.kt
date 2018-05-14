package com.eida.hscutari.weatherapp.data.weather

import com.eida.hscutari.weatherapp.common.constants
import com.eida.hscutari.weatherapp.data.weather.api.IWeatherAPI
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by henriquescutari on 13/05/18.
 */
open class WeatherClient {
    private var weather: IWeatherAPI? = null

    private fun getInstance(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(constants.API_URL_BASE)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okClientBuilder = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okClientBuilder.addInterceptor(httpLoggingInterceptor)

        return okClientBuilder.build()
    }

    fun getWeather(): IWeatherAPI {
        if (weather == null) {
            weather = getInstance().create<IWeatherAPI>(IWeatherAPI::class.java!!)
        }

        return weather!!
    }
}