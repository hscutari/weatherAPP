package com.eida.hscutari.weatherapp

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.eida.hscutari.weatherapp.data.weather.WeatherDatabase
import com.eida.hscutari.weatherapp.model.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WeatherTest {
    private lateinit var database: WeatherDatabase

    private val weather = PlaceWeather()

    @Before
    fun initDb() {
        weather.weather = listOf(Weather("001","01","main","description","01n",""))
        weather.wind = Wind("001","1234")
        weather.main = Main("001",
                "234.5",
                "2242",
                "100",
                "132.5",
                "334.5",
                "DB",
                "100",
                0,
                0)
        weather.sys = Sys("001","test","IE","1485753940","1485753940","","")

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                WeatherDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun celsiusMaxTemp() {

        val celsius = weather.main!!.convertTempMaxCelsius()
        val celsiusFun = (weather.main!!.tempMax.toDouble() - (273.15)).toInt()

        MatcherAssert.assertThat<Boolean>(celsiusFun == celsius, `is`<Boolean>(true))
    }

    @Test
    fun fahrenheitMaxTemp() {
        val celsius = weather.main!!.convertTempMaxFahrenheit()
        val celsiusFun = ((weather.main!!.tempMax.toDouble() * 1.8) - (459.67)).toInt()

        MatcherAssert.assertThat<Boolean>(celsiusFun == celsius, `is`<Boolean>(true))
    }

    @Test
    fun getHour(){

        weather.sys!!.format()

        val unixSeconds: Long = weather.sys!!.sunrise.toLong()
        val date = java.util.Date(unixSeconds * 1000L)
        val sdf = java.text.SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        MatcherAssert.assertThat<Boolean>(weather.sys!!.formatSunrise == sdf.format(date), `is`<Boolean>(true))
    }

    @Test
    fun insertWeather() {
        // When inserting a task
        database.getWeatherDao().save(weather.weather!![0])
        database.getWindDao().save(weather.wind!!)
        database.getMainDao().save(weather.main!!)
        database.getSysDao().save(weather.sys!!)

        // When getting the task by id from the database
        val loaded = database.getWeatherDao().getWeather("001")

        // The loaded data contains the expected values
        MatcherAssert.assertThat<Boolean>(loaded != null, CoreMatchers.`is`<Boolean>(true))
    }
}
