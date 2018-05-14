package com.eida.hscutari.weatherapp.data.weather.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.eida.hscutari.weatherapp.model.Sys

/**
 * Created by henriquescutari on 13/05/18.
 */
@Dao
interface SysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(sys: Sys)
}