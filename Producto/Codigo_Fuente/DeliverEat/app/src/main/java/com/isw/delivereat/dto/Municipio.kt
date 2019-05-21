package com.isw.delivereat.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Entity(tableName = "Municipios")
@Parcelize
data class Municipio constructor(
    @SerializedName("centroide_lat")
    @ColumnInfo(name = "latitud")
    var latitud: Float,
    @SerializedName("centroide_lon")
    @ColumnInfo(name = "longitud")
    var longitud: Float,
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @SerializedName("nombre")
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @SerializedName("provincia_id")
    @ColumnInfo(name = "provincia_id")
    var provinciaId: Int
) : Parcelable {
    override fun toString(): String {
        return "Municipio(latitud=$latitud, longitud=$longitud, id=$id, nombre='$nombre', provinciaId=$provinciaId)"
    }
}
