package com.isw.delivereat.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MunicipioSearchResult(
    val nombre: String,
    val municipiosIds: List<Int>,
    val total: Int,
    val nextInicio: Int?
) : Parcelable
