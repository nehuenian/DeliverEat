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
data class TotalPagar constructor(
    var monto: Float
) : Parcelable
