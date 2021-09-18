package com.example.newsinpocket.room

import androidx.room.TypeConverter
import com.example.newsinpocket.model.news.Source


class Converters {

    @TypeConverter
    fun sourceToStringConverter(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun stringToSourceConverter(name: String): Source {
        return Source(name, name)
    }
}