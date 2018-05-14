package com.eida.hscutari.weatherapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.eida.hscutari.weatherapp.common.ToCelsius
import com.eida.hscutari.weatherapp.common.ToFahrenheit
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

/**
 * Created by henriquescutari on 13/05/18.
 */

@Entity(tableName = "Main")
data class Main (
        @PrimaryKey
        @NotNull
        var cityMainID: String = "",
        var temp: String = "",
        var pressure: String = "",
        var humidity: String = "",

        @SerializedName("temp_min")
        var tempMin: String = "",

        @SerializedName("temp_max")
        var tempMax: String = "",

        var name : String = "",
        var visibility: String = "",

        @Ignore
        var tempF: Int = 0,

        @Ignore
        var tempC: Int = 0) {

    fun convertToC() {
        this.tempC = temp.ToCelsius()
    }

    fun convertToF(){
        this.tempF = temp.ToFahrenheit()
    }

    fun setId(id: String){
        this.cityMainID = id
    }

    fun convertTempMaxCelsius() : Int{
        return tempMax.ToCelsius()
    }

    fun convertTempMaxFahrenheit() : Int{
        return tempMax.ToFahrenheit()
    }

    fun convertTempMinCelsius() : Int{
        return tempMin.ToCelsius()
    }

    fun convertTempMinFahrenheit() : Int{
        return tempMin.ToFahrenheit()
    }
}