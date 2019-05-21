package com.isw.delivereat.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.isw.delivereat.api.ApiSuccessResponse
import com.isw.delivereat.api.DatosGeofraficosApi
import com.isw.delivereat.db.DeliverEatDatabase
import com.isw.delivereat.db.dao.MunicipioDao
import com.isw.delivereat.dto.Municipio
import com.isw.delivereat.dto.MunicipioSearchResponse
import com.isw.delivereat.dto.MunicipioSearchResult
import com.isw.delivereat.dto.Resource
import com.isw.delivereat.util.AppExecutors

/**
 * Repository class based on Android Architecture Components. Since not many data will be used,
 * this should be a farly simple class but it is crucial for following AAC recommendations. This is
 * the centralized entry point for every data needed in our applications, regardless of where we are
 * getting it from (Remote or Local).
 *
 * Created by Jeremias Gersicich on 2019-05-20
 */
class DeliverEatRepository private constructor(
    private val datosGeoApi: DatosGeofraficosApi,
    private val database: DeliverEatDatabase
) {

    private val municipioDao: MunicipioDao by lazy {
        database.municipioDao()
    }

    fun searchMoreMunicipios(query: String): LiveData<Resource<Boolean>> {
        val searchMoreMunicipiosTask = SearchMoreMunicipiosTask(
            query = query,
            datosGeofraficosApi = datosGeoApi,
            db = database
        )
        AppExecutors.networkIO().execute(searchMoreMunicipiosTask)
        return searchMoreMunicipiosTask.liveData
    }

    fun searchMunicipio(nombre: String): LiveData<Resource<List<Municipio>>> {
        return object : NetworkResource<List<Municipio>, MunicipioSearchResponse>() {

            override fun saveCallResult(item: MunicipioSearchResponse) {
                val municipiosIds = item.municipios.map { it.id }
                val municipioSearchResponse =
                    MunicipioSearchResult(
                        nombre = nombre,
                        municipiosIds = municipiosIds,
                        total = item.total,
                        nextInicio = item.inicio + item.max
                    )
                database.runInTransaction {
                    municipioDao.insertAll(item.municipios)
                    municipioDao.insert(municipioSearchResponse)
                }
            }

            override fun shouldFetch(data: List<Municipio>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Municipio>> {
                return Transformations.switchMap(municipioDao.search(nombre)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        municipioDao.loadOrdered(searchData.municipiosIds)
                    }
                }
            }

            override fun createCall() = datosGeoApi.getMunicipios(nombre, 0)

            override fun processResponse(response: ApiSuccessResponse<MunicipioSearchResponse>)
                    : MunicipioSearchResponse {
                return response.body
            }
        }.asLiveData()
    }

    companion object {
        private var INSTANCE: DeliverEatRepository? = null

        fun getInstance(context: Context): DeliverEatRepository? {
            if (INSTANCE == null) {
                synchronized(DeliverEatRepository::class) {
                    INSTANCE =
                        DeliverEatRepository(
                            DatosGeofraficosApi.getInstance()!!,
                            DeliverEatDatabase.getInstance(context)!!
                        )
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {
        init {
            // use post instead of set since this can be created on any thread
            postValue(null)
        }

        companion object {
            fun <T> create(): LiveData<T> {
                return AbsentLiveData()
            }
        }
    }
}