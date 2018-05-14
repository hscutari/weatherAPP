package com.eida.hscutari.weatherapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.eida.hscutari.weatherapp.common.getHour
import org.jetbrains.annotations.NotNull

/**
 * Created by henriquescutari on 13/05/18.
 */

@Entity(tableName = "Sys")
data class Sys(
        @PrimaryKey
        @NotNull
        var citySysID: String = "",
        var type: String = "",
        var country: String = "",
        var sunrise: String = "",
        var sunset: String = "",
        var formatSunrise: String = "",
        var formatSunset: String){

    fun format(){
        this.formatSunrise = sunrise.getHour()
        this.formatSunset = sunset.getHour()
    }

    fun setId(id: String){
        this.citySysID = id
    }
}