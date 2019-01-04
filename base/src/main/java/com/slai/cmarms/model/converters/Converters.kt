package com.slai.cmarms.model.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun toTimeStamp(value : Long) : Date? {
        return if(value == null) null
                else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}