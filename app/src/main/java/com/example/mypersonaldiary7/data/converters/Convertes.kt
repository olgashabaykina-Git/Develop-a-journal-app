package com.example.mypersonaldiary7.data.converters
import androidx.room.TypeConverter
import java.util.Date
class Convertes {
    @TypeConverter
    fun fromDate(date : Date) : Long{
        return date.time
        }

        @TypeConverter
        fun toDate (time : Long) : Date{
            return Date(time)
}
        }

