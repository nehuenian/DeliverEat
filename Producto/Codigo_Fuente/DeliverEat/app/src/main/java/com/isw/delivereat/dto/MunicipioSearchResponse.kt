package com.isw.delivereat.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
data class MunicipioSearchResponse(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("municipios")
    val municipios: List<Municipio>,
    @SerializedName("max")
    val max: Int = 0,
    @SerializedName("inicio")
    var inicio: Int = 0
)