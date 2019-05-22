package com.isw.delivereat.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Parcelize
data class Direccion constructor(
    var calle: String,
    var numero: Int,
    var ciudad: String,
    var referencia: String
) : Parcelable {
    override fun toString(): String {
        return "$calle $numero $ciudad"
    }
}
