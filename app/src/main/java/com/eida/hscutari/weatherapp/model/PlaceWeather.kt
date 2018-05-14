package com.eida.hscutari.weatherapp.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation

/**
 * Created by henriquescutari on 13/05/18.
 */

class PlaceWeather {
    @Relation(parentColumn = "cityMainID", entityColumn = "cityID", entity = Weather::class)
    var weather: List<Weather>? = null
    @Embedded
    var main: Main? = null
    @Embedded
    var sys: Sys? = null
    @Embedded
    var wind: Wind? = null
    @Ignore
    var visibility: String = ""
    @Ignore
    var id: String = ""
    @Ignore
    var name : String = ""
    @Ignore
    var list: List<PlaceWeather>? = null

}