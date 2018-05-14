package com.eida.hscutari.weatherapp.common

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory

import java.util.*


/**
 * Created by henriquescutari on 13/05/18.
 */

fun String.getHour() : String{
    val unixSeconds: Long = this.toLong()
    val date = java.util.Date(unixSeconds * 1000L)
    val sdf = java.text.SimpleDateFormat("HH:mm")
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(date)
}

fun String.toBitmap() :Bitmap? {
    return try {
        val encodeByte = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        e.message
        null
    }
}

fun Bitmap.ToString() : String{
    val arrString = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, arrString)
    val b = arrString.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun String.ToCelsius() : Int{
    return (this.toDouble() - (273.15)).toInt()
}

fun String.ToFahrenheit() : Int{
    return ((this.toDouble() * 1.8) - (459.67)).toInt()
}


