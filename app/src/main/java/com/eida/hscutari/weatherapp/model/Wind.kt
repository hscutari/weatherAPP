package com.eida.hscutari.weatherapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Created by henriquescutari on 13/05/18.
 */

@Entity(tableName = "Wind")
data class Wind(
    @PrimaryKey
    @NotNull
    var cityWindId: String = "",
    var speed: String = ""){

    fun setId(id: String){
        this.cityWindId = id
    }
}