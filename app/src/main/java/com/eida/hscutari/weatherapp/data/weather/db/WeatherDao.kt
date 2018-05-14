package com.eida.hscutari.weatherapp.data.weather.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.eida.hscutari.weatherapp.model.PlaceWeather
import com.eida.hscutari.weatherapp.model.Weather

/**
 * Created by henriquescutari on 13/05/18.
 */

@Dao
interface WeatherDao{

    @Query("SELECT * FROM Main " +
            "INNER JOIN Sys " +
            "ON Sys.citySysID= Main.cityMainID  " +
            "INNER JOIN Wind " +
            "ON Wind.cityWindID = Sys.citySysID " +
            "INNER JOIN Weather " +
            "ON Weather.cityID = Wind.cityWindID  " +
            "WHERE Main.cityMainID = :id ")
    fun getWeather(id: String): PlaceWeather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weather: Weather)
}