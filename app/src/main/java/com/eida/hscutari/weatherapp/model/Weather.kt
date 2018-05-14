package com.eida.hscutari.weatherapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.BitmapFactory
import com.eida.hscutari.weatherapp.common.ToString
import com.eida.hscutari.weatherapp.common.constants
import org.jetbrains.annotations.NotNull
import java.net.URL

/**
 * Created by henriquescutari on 13/05/18.
 */

@Entity(tableName = "Weather")
data class Weather(
        @PrimaryKey
        @NotNull
        var cityID: String = "",
        var id: String = "",
        var main: String = "",
        var description: String = "",
        var icon: String = "",
        var iconBitmap: String = ""){

    fun getNewIcon(){
        try {
            val newuUrl = URL(constants.IMG_URL + this.icon + ".png")
            val iconBitmap = BitmapFactory.decodeStream(newuUrl.openConnection().getInputStream())
            this.iconBitmap = iconBitmap.ToString()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    fun setWeatherId(id: String){
        this.cityID = id
    }
}