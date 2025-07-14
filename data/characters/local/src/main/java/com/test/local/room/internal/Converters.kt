package com.test.local.room.internal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.characters.model.Location
import com.test.characters.model.Origin

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun originToString(origin: Origin): String {
        return gson.toJson(origin)
    }

    @TypeConverter
    fun stringToOrigin(data: String): Origin {
        return gson.fromJson(data, Origin::class.java)
    }

    @TypeConverter
    fun locationToString(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToLocation(data: String): Location {
        return gson.fromJson(data, Location::class.java)
    }

    // Optional: if you need to convert List<String> (for your episode property)
    @TypeConverter
    fun listToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(data: String): List<String> {
        return gson.fromJson(data, Array<String>::class.java).toList()
    }
}