package com.dmm.recetario.data.local.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jakarta.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor (
    private val gson: Gson
) {
    // Para los pasos (List<String>)
    @TypeConverter
    fun fromStringList(value: List<String>?): String? = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Para los ingredientes (List<Map<String, String>>)
    @TypeConverter
    fun fromIngredients(value: List<Map<String, String>>?): String? = gson.toJson(value)

    @TypeConverter
    fun toIngredients(value: String?): List<Map<String, String>>? {
        val listType = object : TypeToken<List<Map<String, String>>>() {}.type
        return gson.fromJson(value, listType)
    }
}