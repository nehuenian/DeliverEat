@file:Suppress("KDocMissingDocumentation")

package com.isw.delivereat.db

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.TypeConverter

/**
 * Converter used for Database since Room cannot save Lists of objects
 */
object DeliverEatTypeConverters {
    @SuppressLint("LongLogTag")
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Log.e("DeliverEatTypeConverters", "Cannot convert $it to number", ex)
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}