package com.isw.delivereat.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
data class MunicipioSearchResponse(
    @SerializedName("municipios")
    val municipios: List<Municipio>
)