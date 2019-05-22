package com.isw.delivereat.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isw.delivereat.api.ApiResponse
import com.isw.delivereat.api.DatosGeofraficosApi
import com.isw.delivereat.dto.MunicipioSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class based on Android Architecture Components. Since not many data will be used,
 * this should be a farly simple class but it is crucial for following AAC recommendations. This is
 * the centralized entry point for every data needed in our applications, regardless of where we are
 * getting it from (Remote or Local).
 *
 * Created by Jeremias Gersicich on 2019-05-20
 */
class DeliverEatRepository private constructor(
    private val datosGeoApi: DatosGeofraficosApi
) {

    fun searchMunicipio(nombre: String): LiveData<ApiResponse<MunicipioSearchResponse>> {
        val livedata = MutableLiveData<ApiResponse<MunicipioSearchResponse>>()

        livedata.postValue(ApiResponse.loading())
        datosGeoApi.getMunicipios(nombre, 0).enqueue(object : Callback<MunicipioSearchResponse> {
            override fun onResponse(
                call: Call<MunicipioSearchResponse>,
                response: Response<MunicipioSearchResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        val exc = Throwable("Response body is empty")
                        livedata.postValue(ApiResponse.error(exc))
                    } else {
                        livedata.postValue(ApiResponse.success(response.body()!!))

                    }
                } else {
                    val exc = Throwable(response.errorBody()?.string()!!)
                    livedata.postValue(ApiResponse.error(exc))
                }
            }

            override fun onFailure(call: Call<MunicipioSearchResponse>, t: Throwable) {
                livedata.postValue(ApiResponse.error(t))
            }
        })

        return livedata
    }

    companion object {
        private var INSTANCE: DeliverEatRepository? = null

        fun getInstance(context: Context): DeliverEatRepository? {
            if (INSTANCE == null) {
                synchronized(DeliverEatRepository::class) {
                    INSTANCE =
                        DeliverEatRepository(
                            DatosGeofraficosApi.getInstance()!!
                        )
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}