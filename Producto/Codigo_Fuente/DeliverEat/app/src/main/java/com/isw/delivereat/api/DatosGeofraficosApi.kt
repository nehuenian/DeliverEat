package com.isw.delivereat.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.isw.delivereat.dto.MunicipioSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface for API REST calls.
 */
interface DatosGeofraficosApi {
    /**
     * Fetch Municipios from Datos Abiertos API and return it as LiveData
     * @param nombre complete or part of the name of the Municipio to search
     * @param inicio delta used for paging
     */
    @GET("municipios?formato=json&aplanar&max=1000&inicio={inicio}&campos=nombre&nombre={nombre}&provincia=córdoba")
    fun getMunicipios(@Path("nombre") nombre: String, @Path("inicio") inicio: Int): Call<MunicipioSearchResponse>

    companion object {

        private var INSTANCE: DatosGeofraficosApi? = null

        /**
         * Returns and Instance of [DatosGeofraficosApi] as in Singleton pattern
         */
        fun getInstance(): DatosGeofraficosApi? {
            if (INSTANCE == null) {
                synchronized(DatosGeofraficosApi::class) {
                    INSTANCE = create()
                }
            }
            return INSTANCE
        }

        private fun create(): DatosGeofraficosApi {
            val gson =
                GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://apis.datos.gob.ar/georef/api/")
                .build()
                .create(DatosGeofraficosApi::class.java)
        }
    }
}
