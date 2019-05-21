package com.isw.delivereat.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isw.delivereat.api.*
import com.isw.delivereat.db.DeliverEatDatabase
import com.isw.delivereat.dto.MunicipioSearchResult
import com.isw.delivereat.dto.Resource
import java.io.IOException

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 */
class SearchMoreMunicipiosTask constructor(
    private val query: String,
    private val datosGeofraficosApi: DatosGeofraficosApi,
    private val db: DeliverEatDatabase
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.municipioDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.nextInicio
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = datosGeofraficosApi.getMoreMunicipios(query, nextPage).execute()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    // we merge all repo ids into 1 list so that it is easier to fetch the
                    // result list.
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.municipiosIds)

                    ids.addAll(apiResponse.body.municipios.map { it.id })
                    val merged = MunicipioSearchResult(
                        query,
                        ids,
                        apiResponse.body.total,
                        (apiResponse.body.inicio + apiResponse.body.max)
                    )
                    db.runInTransaction {
                        db.municipioDao().insert(merged)
                        db.municipioDao().insertAll(apiResponse.body.municipios)
                    }
                    Resource.success(apiResponse.body.total > (apiResponse.body.inicio + apiResponse.body.max))
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}
