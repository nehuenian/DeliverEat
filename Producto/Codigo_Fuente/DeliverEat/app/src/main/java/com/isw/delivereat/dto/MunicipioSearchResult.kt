package com.isw.delivereat.dto

import androidx.room.Entity
import androidx.room.TypeConverters
import com.isw.delivereat.db.DeliverEatTypeConverters

@Entity(primaryKeys = ["nombre"])
@TypeConverters(DeliverEatTypeConverters::class)
data class MunicipioSearchResult(
    val nombre: String,
    val municipiosIds: List<Int>,
    val total: Int,
    val nextInicio: Int?
)
