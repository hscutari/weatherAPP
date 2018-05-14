package com.eida.hscutari.weatherapp.data.weather.api

import com.eida.hscutari.weatherapp.model.PlaceWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by henriquescutari on 13/05/18.
 */
interface IWeatherAPI {

    @GET("weather")
    fun getData(@Query("q") q: String, @Query("appid") appid: String): Observable<PlaceWeather>

    @GET("weather")
    fun getData(@Query("lat") lat: String,
                @Query("lon") lon: String,
                @Query("cnt") cnt: String,
                @Query("appid") appid: String): Observable<PlaceWeather>

    @GET("group")
    fun getSeveral(@Query("id") q: String, @Query("appid") appid: String): Observable<PlaceWeather>
}