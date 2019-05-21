package com.isw.delivereat.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Entity(tableName = "Direcciones")
@Parcelize
data class Direccion constructor(
    @ColumnInfo(name = "calle")
    var calle: String,
    @ColumnInfo(name = "numero")
    var numero: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "municipio_id")
    var ciudad: Int,
    @ColumnInfo(name = "referencia")
    var referencia: String
) : Parcelable {
    override fun toString(): String {
        return "Direccion(calle='$calle', numero=$numero, id=$id, ciudad=$ciudad, referencia='$referencia')"
    }
}
