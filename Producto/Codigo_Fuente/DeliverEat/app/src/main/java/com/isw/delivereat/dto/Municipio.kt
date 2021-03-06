package com.isw.delivereat.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Parcelize
data class Municipio constructor(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int,
    @SerializedName("nombre")
    var nombre: String
) : Parcelable
