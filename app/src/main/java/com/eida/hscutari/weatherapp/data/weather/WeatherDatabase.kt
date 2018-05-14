package com.eida.hscutari.weatherapp.data.weather

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.eida.hscutari.weatherapp.data.weather.db.MainDao
import com.eida.hscutari.weatherapp.data.weather.db.SysDao
import com.eida.hscutari.weatherapp.data.weather.db.WeatherDao
import com.eida.hscutari.weatherapp.data.weather.db.WindDao
import com.eida.hscutari.weatherapp.model.Main
import com.eida.hscutari.weatherapp.model.Sys
import com.eida.hscutari.weatherapp.model.Weather
import com.eida.hscutari.weatherapp.model.Wind

/**
 * Created by henriquescutari on 13/05/18.
 */

@Database(entities = [
    (Main::class),
    (Sys::class),
    (Weather::class),
    (Wind::class)], version = 1)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao
    abstract fun getMainDao(): MainDao
    abstract fun getSysDao(): SysDao
    abstract fun getWindDao(): WindDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<WeatherDatabase>(context.applicationContext,
                                WeatherDatabase::class.java, "weather_database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}