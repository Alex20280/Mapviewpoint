package com.example.mapviewpoint.utils

import com.example.mapviewpoint.model.GpsCoordinates
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        return format.format(date)
    }

    fun convertDateToTimestamp(date: String): Long {

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val parsedDate = format.parse(date)

        return parsedDate.time
    }
}