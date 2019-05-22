package com.isw.delivereat.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Parcelize
data class FormaPago constructor(
    var efectivo: Boolean,
    var monto: Float,
    var tarjeta: Tarjeta?
) : Parcelable {
    override fun toString(): String {
        if (efectivo) {
            return "Efectivo - $ " + monto
        } else {
            return "Tarjeta"
        }
    }
}
