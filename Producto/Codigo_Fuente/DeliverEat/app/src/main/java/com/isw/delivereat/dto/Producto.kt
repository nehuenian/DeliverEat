package com.isw.delivereat.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Parcelize
data class Producto constructor(
    var nombre: String,
    var precio: Float,
    var cantidad: Int,
    var imagen: String
) : Parcelable {
    override fun toString(): String {
        return "Producto(nombre='$nombre', precio=$precio, cantidad=$cantidad, imagen='$imagen')"
    }
}
